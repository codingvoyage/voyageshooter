package voyagequest;

import org.newdawn.slick.UnicodeFont;

/**
 * A utility class of random useful functions and variables
 * 
 * @author Edmund Qiu
 * @author Brian Yang
 */
public final class Util {
    
    /** Font to be used throughout */
    public static final UnicodeFont FONT;
    static {
        UnicodeFont newFont;
        try {
            newFont = new UnicodeFont("/src/voyagequest/DroidSans.ttf", 24, false, false);
            newFont.addAsciiGlyphs();
            newFont.getEffects().add(new org.newdawn.slick.font.effects.ColorEffect(new java.awt.Color(218, 249, 248))); 
            newFont.loadGlyphs(); 
        } catch (org.newdawn.slick.SlickException e) {
            newFont = new UnicodeFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 24));
        }
        FONT = newFont;
    }
    
    /**
     * Given an integer bound, returns a random integer from
     * [min, max], so it's inclusive of min and max.
     * 
     * @param min minimum bound for random number
     * @param max maximum bound for random number
     * @return the random number
     */
    public static int rand(int min, int max)
    {
        int randomNumber = min + (int)(Math.random() * ((max - min + 1)));
        return randomNumber;
    }
    
    /**
     * Nobody likes typing out System.out.println();
     * @param s the String to print 
     */
    public static void p(String s)
    {
        System.out.println(s);
    }
    
    /**
     * Nobody likes typing out System.out.println();
     * @param i the integer to print
     */
    public static void p(int i)
    {
        System.out.println(i);
    }
    
    
    /**
     * Nobody likes typing out System.out.println();
     * @param b the boolean value to print 
     */
    public static void p(boolean b)
    {
        System.out.println(b);
    }
}
