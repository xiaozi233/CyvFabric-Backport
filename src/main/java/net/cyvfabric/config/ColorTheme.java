package net.cyvfabric.config;

public enum ColorTheme {
    //red-crimson
    VIRULIA(0xAF000000, 0xAF1F0000, 0xAFAF002F, 0xAF88001F,
            0xAFBB004F, 0xAFAA002F, 0xAFCC0033, 0xAFAF0022,
            0xDFDD0033, 0xDFCC0022, 0xAF444444, 0xAF333333,
            1.0F, 0, 0.2F, 0.6F, 0, 0.05F, 0.6F, 0.6F, 0.6F),

    //golden
    FLAXEN(0xAF000000, 0xAF000022, 0xAFB9Af30, 0xAF997f10,
            0xAFCCBF00, 0xAFBBA500, 0xAEEEDD00, 0xAFDDBB00,
            0xDFFFFB00, 0xDFF0E000, 0xAF2F3F44, 0xAF293940,
            0.8F, 0.6F, 0, 0.65F, 0.45F, 0, 0.55F, 0.6F, 0.65F),

    //green-teal
    VERDANT(0xAF000000, 0xAF001919, 0xAF00EF7F, 0xAF00BB55,
            0xAF00BB77, 0xAF00AA66, 0xAF00EF66, 0xAF00DF55,
            0xDF00EE77, 0xDF00DD66, 0xAF346954, 0xAF245944,
            0, 0.85F, 0.65F, 0, 0.6F, 0.3F, 0.3F, 0.65F, 0.5F),

    //aqua-electric
    CYVISPIRIA(0xAF000000, 0xAF001122, 0xAF00AFEF, 0xAF0066AA,
            0xAF00AACC, 0xAF0099BB, 0xAF00AAFF, 0xAF0099EE,
            0xDF00CCFF, 0xDF00BBEE, 0xAF444444, 0xAF333333,
            0, 0.8F, 1.0F, 0, 0.4F, 0.6F, 0.6F, 0.6F, 0.6F);


    ColorTheme(int background, int highlight, int shade1, int shade2,
               int border1, int border2, int main1, int main2,
               int accent1, int accent2, int secondary1, int secondary2,
               float mr, float mg, float mb, float br, float bg, float bb, float sr, float sg, float sb) {
        this.background1 = background;
        this.highlight = highlight;

        this.shade1 = shade1;
        this.shade2 = shade2;
        this.border1 = border1;
        this.border2 = border2;
        this.main1 = main1;
        this.main2 = main2;
        this.accent1 = accent1;
        this.accent2 = accent2;
        this.secondary1 = secondary1;
        this.secondary2 = secondary2;

        this.mainBaseR = mr;
        this.mainBaseG = mg;
        this.mainBaseB = mb;
        this.borderBaseR = br;
        this.borderBaseG = bg;
        this.borderBaseB = bb;
        this.secondaryBaseR = sr;
        this.secondaryBaseG = sg;
        this.secondaryBaseB = sb;
    }

    public final int background1;
    public final int highlight;

    public final int shade1, shade2;
    public final int border1, border2;
    public final int main1, main2;
    public final int accent1, accent2;
    public final int secondary1, secondary2;

    public final float mainBaseR, mainBaseG, mainBaseB;
    public final float borderBaseR, borderBaseG, borderBaseB;
    public final float secondaryBaseR, secondaryBaseG, secondaryBaseB;

    public int mainBase() {
        return (int) (0xFF000000 + mainBaseR*256*256*255 + mainBaseG*256*255 + mainBaseB*255);
    }

    public int borderBase() {
        return (int) (0xFF000000 + borderBaseR*256*256*255 + borderBaseG*256*255 + borderBaseB*255);
    }

    public int secondaryBase() {
        return (int) (0xFF000000 + secondaryBaseR*256*256*255 + secondaryBaseG*256*255 + secondaryBaseB*255);
    }

    public static String[] getThemes() {
        String[] strs = new String[ColorTheme.values().length];

        for (int i = 0; i<ColorTheme.values().length; i++) {
            strs[i] = ColorTheme.values()[i].toString();
        }

        return strs;
    }

}