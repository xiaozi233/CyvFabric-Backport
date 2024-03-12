package mcpk.functions.nonmovement;

import java.util.ArrayList;
import java.util.HashMap;

import mcpk.Parser;
import mcpk.Player;
import mcpk.functions.Function;
import mcpk.functions.Function.DurationException;
import mcpk.functions.Function.InvalidKeypressException;
import mcpk.utils.ParserException;

public abstract class SpecialFunction extends Function {
	
	public abstract void specialRun(Player player, double args, Parser parser) throws ParserException;

	@Override //This method is not used
	public final void run(Player player, int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) 
			throws DurationException, InvalidKeypressException {
		return;
	}
	
}
