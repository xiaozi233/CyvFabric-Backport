package net.cyvfabric.config;

import java.util.ArrayList;

public class CyvClientColorHelper {
    public static CyvClientColor color1;
    public static CyvClientColor color2;
    public static ArrayList<CyvClientColor> colors;
    public static String[] colorStrings;

    static {
        colors = new ArrayList<CyvClientColor>();
        colors.add(new CyvClientColor("dark_red", ChatFormattingString.DARK_RED, 11141120));
        colors.add(new CyvClientColor("red", ChatFormattingString.RED, 16733525));
        colors.add(new CyvClientColor("gold", ChatFormattingString.GOLD, 16755200));
        colors.add(new CyvClientColor("yellow", ChatFormattingString.YELLOW, 16777045));
        colors.add(new CyvClientColor("dark_green", ChatFormattingString.DARK_GREEN, 43520));
        colors.add(new CyvClientColor("green", ChatFormattingString.GREEN, 5635925));
        colors.add(new CyvClientColor("aqua", ChatFormattingString.AQUA, 5636095));
        colors.add(new CyvClientColor("dark_aqua", ChatFormattingString.DARK_AQUA, 43690));
        colors.add(new CyvClientColor("dark_blue", ChatFormattingString.DARK_BLUE, 170));
        colors.add(new CyvClientColor("blue", ChatFormattingString.BLUE, 5592575));
        colors.add(new CyvClientColor("light_purple", ChatFormattingString.LIGHT_PURPLE, 16733695));
        colors.add(new CyvClientColor("dark_purple", ChatFormattingString.DARK_PURPLE, 11141290));
        colors.add(new CyvClientColor("white", ChatFormattingString.WHITE, 16777215));
        colors.add(new CyvClientColor("gray", ChatFormattingString.GRAY, 11184810));
        colors.add(new CyvClientColor("dark_gray", ChatFormattingString.DARK_GRAY, 5592405));
        colors.add(new CyvClientColor("black", ChatFormattingString.BLACK, 0));

        colorStrings = colors.stream().map(c -> c.name).toArray(String[]::new);

    }

    public static void checkColor(String c1, String c2) {
        color1 = colors.get(6);
        color2 = colors.get(12);

        for (CyvClientColor c : colors) {
            if (c.name.equals(c1)) color1 = c;
            if (c.name.equals(c2)) color2 = c;
        }

        CyvClientConfig.set("color1", color1.name);
        CyvClientConfig.set("color2", color2.name);
    }

    public static boolean setColor1(String s) {
        for (CyvClientColor c : colors) {
            if (c.name.equals(s)) {
                color1 = c;
                CyvClientConfig.set("color1", color1.name);
                return true;
            }
        }
        return false;
    }

    public static boolean setColor2(String s) {
        for (CyvClientColor c : colors) {
            if (c.name.equals(s)) {
                color2 = c;
                CyvClientConfig.set("color2", color2.name);
                return true;
            }
        }
        return false;
    }

    public static class CyvClientColor {
        public final String name;
        public final String chatColor;
        public final long drawColor;

        CyvClientColor(String name, String chatColor, long drawColor) {
            this.name = name;
            this.chatColor = chatColor;
            this.drawColor = drawColor;
        }
    }

}