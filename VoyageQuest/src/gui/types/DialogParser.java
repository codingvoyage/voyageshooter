package gui.types;

import gui.types.Dialog;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.TrueTypeFont;
import voyagequest.Globals;

/**
 * Processes the dialog text to print out one character at a time 
 * and fit it to the dialog box.
 * @author Brian Yang
 */
public class DialogParser {
    
    /** Words of the String */
    private String[] words;
    /** The dialog box */
    private Dialog box;
    /** Update interval */
    private int delta;
    /** Font */
    public static final TrueTypeFont FONT = Globals.FONT;
    
    /** x coordinate */
    private float x;
    /** y coordinate */
    private float y;
    
    /** dialog box offset */
    public static final float DIALOG_OFFSET = 50.0f;
    
    /**
     * 
     * @param text
     * @param box 
     */
    public DialogParser(String text, Dialog box, float x, float y) {
        this.box = box;
        words = text.split(" ");
        this.x = x;
        this.y = y;
    }
    
    /**
     * Processes and prints the dialog box
     */
    public void draw() {
        
        float xStart = x + DIALOG_OFFSET/2;
        x += DIALOG_OFFSET/2;
        int totalWidth = box.getWidth() + (int)xStart - (int)DIALOG_OFFSET;
        
        for (String s : words) {
            int width = FONT.getWidth(s);
            
            if (x + width > totalWidth) {
                System.out.println(x);
                x = xStart;
                System.out.println();
                y += FONT.getLineHeight();
            }
            
            char[] chars = s.toCharArray();
            for (char c : chars) {           
                FONT.drawString(x, y, Character.toString(c));

                x += FONT.getWidth(Character.toString(c));
            }

            FONT.drawString(x, y, " ");
            x+= FONT.getWidth(" ");
                
        }
   }

    
}
