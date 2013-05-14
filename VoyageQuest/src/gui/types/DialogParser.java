package gui.types;

import org.newdawn.slick.UnicodeFont;
import voyagequest.Util;
import java.util.LinkedList;

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
    public static final UnicodeFont FONT = Util.FONT;
    
    /** Linked list of characters */
    private LinkedList<LinkedList<String>> chars;
    
    /** x coordinate */
    private float x;
    /** y coordinate */
    private float y;
    
    /** dialog box offset */
    public static final float DIALOG_PADDING = 25.0f;
    
    /**
     * 
     * @param text
     * @param box 
     */
    public DialogParser(String text, Dialog box, float x, float y) {
        this.box = box;
        
        this.x = x;
        this.y = y;
        
        // Split the text up into different words
        words = text.split(" ");

        /*
        chars = new LinkedList<>();
        
        // For each word, split into characters
        for (String s : words) {
            char[] tempChars = s.toCharArray();
            
            // For each character array, make a new linked list
            LinkedList<String> charList = new LinkedList<>();
            for (char c : tempChars) {
                charList.add(Character.toString(c));
            }
            chars.add(charList);
        }
        */
    }
    
    /**
     * Processes and prints the dialog box
     */
    public void draw() {
        
        
        
        
        float xStart = x + DIALOG_PADDING;
        x += DIALOG_PADDING;
        y += DIALOG_PADDING;
        int totalWidth = box.getWidth() + (int)xStart - (int)DIALOG_PADDING * 2;
        
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
