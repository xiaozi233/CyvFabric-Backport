package net.cyvfabric.command.calculations;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.server.command.ServerCommandSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CommandCalculate extends CyvCommand {
    public CommandCalculate() {
        super("calculate");
        this.hasArgs = true;
        this.usage = "<math expression>";
        this.helpString = "Parse a string for a math expression to evaluate.";

        this.aliases.add("calc");
        this.aliases.add("==");
        this.aliases.add("=");

    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        if (args.length == 0) {
            CyvFabric.sendMessage("Please input something to calculate.");

        } else {
            String text = String.join(" ", args);
            new Calculator(text).start();
        }
    }

    static class Calculator extends Thread {
        String input;

        Calculator(String args) {
            this.input = args;
        }

        public void run() {
            try {
                double result = this.parse(this.separate(input), true);

                DecimalFormat df = CyvFabric.df;

                CyvFabric.sendMessage("Calculating " + input + "\n = " + df.format(result));

            } catch (Exception e) {
                CyvFabric.sendMessage("Calculation failed.");
            }

        }

        public ArrayList<String> separate(String input) throws Exception {
            input = input.toLowerCase();
            ArrayList<String> elements = new ArrayList<String>();

            int state = 0;
            String current_number = ""; //current number for current element
            String current_function = ""; //current function if one exists

            ArrayList<Character> operands = new ArrayList<Character>(); //operators
            operands.add('+'); operands.add('-'); operands.add('*'); operands.add('/'); operands.add('^');

            ArrayList<String> advFunctions = new ArrayList<String>(); //advanced functions
            advFunctions.add("sin("); advFunctions.add("cos("); advFunctions.add("tan(");
            advFunctions.add("sec("); advFunctions.add("csc("); advFunctions.add("cot(");
            advFunctions.add("asin("); advFunctions.add("acos("); advFunctions.add("atan(");
            advFunctions.add("asec("); advFunctions.add("acsc("); advFunctions.add("acot(");
            advFunctions.add("arcsin("); advFunctions.add("arccos("); advFunctions.add("arctan(");
            advFunctions.add("arcsec("); advFunctions.add("arccsc("); advFunctions.add("arccot(");
            advFunctions.add("log("); advFunctions.add("ln("); advFunctions.add("abs(");
            advFunctions.add("sqrt("); advFunctions.add("cbrt(");

            //no more bs, parsing begins here.
            for (int i = 0; i < input.length(); i++) { //main loop
                char current = input.charAt(i);

                if (state == 0) { //looking for ANYTHING

                    if (Character.isDigit(current) || current == '.') { //NUMBER START

                        if (elements.size() != 0) { //check if last element was also a number
                            String last_value = elements.get(elements.size()-1);
                            try {
                                Double.parseDouble(last_value);
                                elements.add("*"); //last value is a double!

                            } catch (Exception e) {
                                if (last_value.equals(")")) { //last value is end of parenthesis
                                    elements.add("*");
                                }
                            }
                        }

                        current_number = current_number + current; //start the number parsing
                        state = 1;

                    } else if (current == ' ') { //space, nothing happens
                        continue;

                    } else if (current == '!') { //FACTORIAL
                        char last_value = elements.get(elements.size()-1).toCharArray()[0];
                        if (operands.contains(last_value)) {
                            throw new Exception("Factorial must be of a number");
                        }
                        elements.add("!");

                    } else if (operands.contains(current)) { //OPERAND
                        if (elements.size() != 0) { //check if last element was also a number
                            char last_value = elements.get(elements.size()-1).toCharArray()[0];

                            if (last_value == '(') {
                                if (current != '-') {
                                    throw new Exception("Started with operand"); //starting the string with an operand. nice
                                } else {
                                    current_number = current_number + current; //first number negative
                                    state = 1;
                                    continue;
                                }
                            }

                            if (operands.contains(last_value)) { //two operands in a row. nice
                                if (current != '-') {
                                    throw new Exception("Two operands in a row"); //two operands in a row. nice
                                } else {
                                    current_number = current_number + current; //negative number
                                    state = 1;
                                    continue;
                                }
                            }
                        } else {
                            if (current != '-') {
                                throw new Exception("Started with operand"); //starting the string with an operand. nice
                            } else {
                                current_number = current_number + current; //first number negative
                                state = 1;
                                continue;
                            }
                        }

                        //ok everything's fine, add the operand
                        elements.add(current + "");
                        state = 0;

                    } else if (current == '(') { //PARENTHESIS OPENING
                        if (elements.size() != 0) { //check if last element was also a number
                            String last_value = elements.get(elements.size()-1);
                            try {
                                Double.parseDouble(last_value);
                                elements.add("*"); //last value is a double!

                            } catch (Exception e) {
                                if (last_value.equals(")")) { //last value is end of parenthesis
                                    elements.add("*");
                                }
                            }
                        }

                        elements.add("(");
                        state = 0;

                    } else if (current == ')') { //PARENTHESIS CLOSING
                        if (elements.size() != 0) { //check if last element was also a number
                            char last_value = elements.get(elements.size()-1).toCharArray()[0];
                            if (operands.contains(last_value)) { //two operands in a row. nice
                                throw new Exception("Last element in parenthesis was an operand");
                            }
                        } else { throw new Exception("Started with closing parenthesis"); } //starting the string with a ). nice

                        elements.add(")");
                        state = 0;

                    } else { //start function
                        if (elements.size() != 0) { //check if last element was also a number
                            String last_value = elements.get(elements.size()-1);
                            try {
                                Double.parseDouble(last_value);
                                elements.add("*"); //last value is a double!

                            } catch (Exception e) {
                                if (last_value.equals(")")) { //last value is end of parenthesis
                                    elements.add("*");
                                }
                            }
                        }

                        current_function = current_function + current;
                        state = 2;
                    }

                } else if (state == 1) { //looking for NUMBER END
                    if (Character.isDigit(current) || current == '.') { //continue number
                        if (current == '.' && current_number.indexOf('.') != -1) {
                            throw new Exception("You can't use two decimal places in one number."); //two decimals. nice
                        }

                        current_number = current_number + current;

                    } else if (current == ' ') { //ending number, looking for number start
                        if (current_number.equals("-")) {
                            throw new Exception("Invalid minus sign");
                        }
                        elements.add(current_number); //save the number
                        current_number = "";
                        state = 0;

                    } else if (operands.contains(current)) { //operator found
                        if (current_number.equals("-")) {
                            throw new Exception("Two operands in a row");
                        }
                        elements.add(current_number); //save the number
                        current_number = "";

                        elements.add(current + ""); //and then save the operand as well
                        state = 0;

                    } else if (current == '(') { //PARENTEHSIS OPENING
                        if (current_number.equals("-")) {
                            current_number = "";

                            elements.add("-(");
                            state = 0;
                        } else {
                            elements.add(current_number); //save the number
                            current_number = "";

                            elements.add("*");
                            elements.add("(");
                            state = 0;
                        }

                    } else if (current == ')') { //PARENTEHSIS CLOSING
                        if (current_number.equals("-")) {
                            throw new Exception("Invalid negative symbol");
                        }
                        elements.add(current_number); //save the number
                        current_number = "";

                        elements.add(")");
                        state = 0;

                    } else if (current == '!') {
                        if (current_number.equals("-")) {
                            throw new Exception("Invalid negative symbol");
                        }
                        elements.add(current_number);
                        current_number = "";
                        elements.add("!");
                        state = 0;

                    } else { //FUNCTION
                        if (current_number.equals("-")) {
                            current_function = current_function + current_number + current;
                            current_number = "";
                            state = 2;
                        } else {
                            elements.add(current_number); //save the number
                            current_number = "";

                            elements.add("*");
                            current_function = current_function + current;
                            state = 2;
                        }

                    }

                } else if (state == 2) { //curently parsing function

                    if (current == ' ') {
                        if (current_function.equals("pi")) {
                            elements.add(Math.PI + "");
                        } else if (current_function.equals("e")) {
                            elements.add(Math.E + "");
                        } else if (current_function.equals("-pi")) {
                            elements.add(-Math.PI + "");
                        } else if (current_function.equals("-e")) {
                            elements.add(-Math.E + "");
                        } else throw new Exception("Invalid special number");
                        current_function = "";
                        state = 0;

                    } else if (operands.contains(current)) {
                        if (current_function.equals("pi")) {
                            elements.add(Math.PI + "");
                        } else if (current_function.equals("e")) {
                            elements.add(Math.E + "");
                        } else if (current_function.equals("-pi")) {
                            elements.add(-Math.PI + "");
                        } else if (current_function.equals("-e")) {
                            elements.add(-Math.E + "");
                        } else throw new Exception("Invalid special number");
                        elements.add(current + "");
                        current_function = "";
                        state = 0;

                    } else if (current == '!') {
                        if (current_function.equals("pi")) {
                            elements.add(Math.PI + "");
                        } else if (current_function.equals("e")) {
                            elements.add(Math.E + "");
                        } else if (current_function.equals("-pi")) {
                            elements.add(-Math.PI + "");
                        } else if (current_function.equals("-e")) {
                            elements.add(-Math.E + "");
                        } else throw new Exception("Invalid special number");
                        elements.add("!");
                        current_function = "";
                        state = 0;

                    } else if (current == ')') {
                        if (current_function.equals("pi")) {
                            elements.add(Math.PI + "");
                        } else if (current_function.equals("e")) {
                            elements.add(Math.E + "");
                        } else if (current_function.equals("-pi")) {
                            elements.add(-Math.PI + "");
                        } else if (current_function.equals("-e")) {
                            elements.add(-Math.E + "");
                        } else throw new Exception("Invalid special number");

                        elements.add(")");
                        current_function = "";
                        state = 0;

                    } else if (current == '(') {
                        current_function = current_function + current;
                        if (advFunctions.contains(current_function) || advFunctions.contains(current_function.substring(1))) {
                            elements.add(current_function);

                        } else throw new Exception("Invalid function");
                        current_function = "";
                        state = 0;

                    } else {
                        current_function = current_function + current;

                    }

                }

            } //end parsing

            if (state == 1) { //if NUMBER was in progress, add it
                elements.add(current_number);
                current_number = "";
                state = 0;
            }
            if (state == 2) { //if NUMBER was in progress, add it
                if (current_function.equals("pi")) {
                    elements.add(Math.PI + "");
                } else if (current_function.equals("e")) {
                    elements.add(Math.E + "");
                } else if (current_function.equals("-pi")) {
                    elements.add(-Math.PI + "");
                } else if (current_function.equals("-e")) {
                    elements.add(-Math.E + "");
                } else throw new Exception("Invalid special number");
                current_function = "";
                state = 0;
            }

            return elements;
        }

        public double parse(ArrayList<String> elements, boolean isOriginal) throws Exception {

            ArrayList<String> advFunctions = new ArrayList<String>(); //advanced functions
            advFunctions.add("(");
            advFunctions.add("sin("); advFunctions.add("cos("); advFunctions.add("tan(");
            advFunctions.add("sec("); advFunctions.add("csc("); advFunctions.add("cot(");
            advFunctions.add("asin("); advFunctions.add("acos("); advFunctions.add("atan(");
            advFunctions.add("asec("); advFunctions.add("acsc("); advFunctions.add("acot(");
            advFunctions.add("arcsin("); advFunctions.add("arccos("); advFunctions.add("arctan(");
            advFunctions.add("arcsec("); advFunctions.add("arccsc("); advFunctions.add("arccot(");
            advFunctions.add("log("); advFunctions.add("ln("); advFunctions.add("abs(");
            advFunctions.add("sqrt("); advFunctions.add("cbrt(");

            //Begin parsing


            for (int i = 0; i < elements.size(); i++) {
                String current = elements.get(i);
                //Let's go back to year 2 and relearn PEMDAS!

                if (advFunctions.contains(current) || advFunctions.contains(current.substring(1))) { //P = PARENTHESIS'

                    int term_count = 1;
                    int starting_index = i;
                    ArrayList<String> subElements = new ArrayList<String>(); //create new mini-array
                    String temp = "";
                    boolean isSuccessful = true;

                    do { //add elements to mini-array
                        i++;

                        try {
                            temp = elements.get(i);
                        } catch (Exception e) {
                            throw new Exception("Unbalanced Parenthesis");
                        }

                        if (advFunctions.contains(temp) || advFunctions.contains(temp.substring(1))) { //new parenthesis
                            term_count++;
                            isSuccessful = false;
                            break;

                        } else { //add to list
                            if (!temp.equals(")")) {
                                subElements.add(temp); //add term
                            }

                            term_count++;
                        }

                    } while (!temp.equals(")") && i < elements.size());

                    if (i == elements.size()) { //UNBALANCED PARENTHESIS
                        throw new Exception("Unbalanced parenthesis");
                    }

                    if (!isSuccessful) { //another parenthesis found
                        i--; //prepare to restart method

                    } else { //prepare to parse inside parenthesis

                        //now for the giant function shit, parse the substring

                        String a = elements.get(starting_index);

                        if (elements.get(starting_index).charAt(0) == '-') {
                            a = a.substring(1);
                        }

                        double value = this.parse(subElements, false);

                        if (a.equals("sin(")) {
                            value = Math.sin(value);
                        } else if (a.equals("cos(")) {
                            value = Math.cos(value);
                        } else if (a.equals("tan(")) {
                            value = Math.tan(value);
                        } else if (a.equals("sec(")) {
                            value = 1/Math.cos(value);
                        } else if (a.equals("csc(")) {
                            value = 1/Math.sin(value);
                        } else if (a.equals("cot(")) {
                            value = 1/Math.tan(value);
                        } else if (a.equals("asin(") || a.equals("arcsin(")) {
                            value = Math.asin(value);
                        } else if (a.equals("acos(") || a.equals("arccos(")) {
                            value = Math.acos(value);
                        } else if (a.equals("atan(") || a.equals("arctan(")) {
                            value = Math.atan(value);
                        } else if (a.equals("acsc(") || a.equals("arccsc(")) {
                            value = Math.asin(1/value);
                        } else if (a.equals("asec(") || a.equals("arcsec(")) {
                            value = Math.acos(1/value);
                        } else if (a.equals("acot(") || a.equals("arccot(")) {
                            value = Math.atan(1/value);
                        } else if (a.equals("log(")) {
                            value = Math.log(value) / Math.log(10); //log base 10
                        } else if (a.equals("ln(")) {
                            value = Math.log(value);
                        } else if (a.equals("abs(")) {
                            value = Math.abs(value);
                        } else if (a.equals("sqrt(")) {
                            value = Math.sqrt(value);
                        } else if (a.equals("cbrt(")) {
                            value = Math.cbrt(value);
                        }

                        if (elements.get(starting_index).charAt(0) == '-') {
                            value = -value;
                        }

                        if (a.equals("sin(") || a.equals("cos(") || a.equals("tan(") ||
                                a.equals("sec(") || a.equals("csc(") || a.equals("cot(") ||
                                a.equals("asin(") || a.equals("acos(") || a.equals("atan(") ||
                                a.equals("asec") || a.equals("acsc(") || a.equals("acot(")) {

                            if (value > Math.pow(10, 14)) { //POSITIVE INFINITY THRESHOLD
                                value = Double.POSITIVE_INFINITY;

                            } else if (value < -Math.pow(10, 14)) { //NEGATIVE INFINITY THRESHOLD
                                value = Double.NEGATIVE_INFINITY;
                            }


                        }

                        elements.set(starting_index, value + "");

                        for (int j=0; j<term_count-1; j++) { //remove the stuffs
                            elements.remove(starting_index+1);
                        }

                        i=-1;

                    }

                } //end parenthesis checking

            }

            for (int i = 0; i < elements.size(); i++) {
                String current = elements.get(i);

                if (current.equals("!")) { //Factorials
                    double term1 = Double.parseDouble(elements.get(i-1));
                    elements.set(i-1, "" + factorial(term1));
                    elements.remove(i);

                    i=-1;

                }

            }

            for (int i = 0; i < elements.size(); i++) {
                String current = elements.get(i);

                if (current.equals("^")) { //E = EXPONENT

                    double term1 = Double.parseDouble(elements.get(i-1));
                    double term2 = Double.parseDouble(elements.get(i+1));
                    double value = Math.pow(term1, term2);
                    elements.set(i-1, value + "");
                    elements.remove(i+1);
                    elements.remove(i);

                    i=-1;

                }
            }

            for (int i = 0; i < elements.size(); i++) {
                String current = elements.get(i);

                if (current.equals("*")) { //M = MULTIPLICATION

                    double term1 = Double.parseDouble(elements.get(i-1));
                    double term2 = Double.parseDouble(elements.get(i+1));
                    double value = term1 * term2;
                    elements.set(i-1, value + "");
                    elements.remove(i+1);
                    elements.remove(i);

                    i=-1;

                } else if (current.equals("/")) { //D = Division

                    double term1 = Double.parseDouble(elements.get(i-1));
                    double term2 = Double.parseDouble(elements.get(i+1));
                    double value = term1 / term2;
                    elements.set(i-1, value + "");
                    elements.remove(i+1);
                    elements.remove(i);

                    i=-1;

                }

            }

            for (int i = 0; i < elements.size(); i++) {
                String current = elements.get(i);

                if (current.equals("+")) { //A = ADDITION

                    double term1 = Double.parseDouble(elements.get(i-1));
                    double term2 = Double.parseDouble(elements.get(i+1));
                    double value = term1 + term2;
                    elements.set(i-1, value + "");
                    elements.remove(i+1);
                    elements.remove(i);

                    i=-1;

                } else if (current.equals("-")) { //S = SUBTRACTION

                    double term1 = Double.parseDouble(elements.get(i-1));
                    double term2 = Double.parseDouble(elements.get(i+1));
                    double value = term1 - term2;
                    elements.set(i-1, value + "");
                    elements.remove(i+1);
                    elements.remove(i);

                    i=-1;

                }

            }

            //NUMBER

            if (elements.get(0).equals("pi")) {
                elements.set(0, Math.PI + "");
            } else if (elements.get(0).equals("e")) {
                elements.set(0, Math.E + "");
            }

            if (Double.isInfinite(Double.parseDouble(elements.get(0)))) {
                return Double.parseDouble(elements.get(0));
            }

            if (isOriginal) {
                double answer = Double.parseDouble(elements.get(0));
                BigDecimal bd = new BigDecimal(Double.toString(answer));
                bd = bd.setScale(10, RoundingMode.HALF_DOWN);
                return bd.doubleValue();

            } else {

                return Double.parseDouble(elements.get(0));
            }

        }

        public ArrayList<String> smallerList(ArrayList<String> original, int index) {
            ArrayList<String> newList = new ArrayList<String>();

            for (int i = index; i < original.size(); i++) {
                newList.add(original.get(i));
            }

            return newList;
        }

        static double factorial(double n) throws Exception {
            if (n == 0) return 1;
            if (n < 0) throw new Exception("Factorials only work for positive numbers.");
            if (n != (long) n) throw new Exception("Factorials only work for whole numbers.");

            double factorial = 1;

            for (int i=1; i<=n; i++) {
                factorial *= i;
            }

            return factorial;
        }
    }

}
