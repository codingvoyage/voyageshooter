package gui.types;

import gui.VoyageGuiException;
import java.util.LinkedList;
import java.util.ListIterator;
import org.newdawn.slick.UnicodeFont;
import voyagequest.Util;

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
    
    /** Elapsed time */
    private int time;
    /** Font */
    public static final UnicodeFont FONT = Util.FONT;
    
    /** Linked list of characters */
    private LinkedList<LinkedList<String>> chars;
    /** Iterator for outer list */
    private ListIterator<LinkedList<String>> wordIterator;
    /** Iterator for inner list */
    private ListIterator<String> charIterator;
    
    /** Begin a new word */
    private boolean newWord;
    
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
     * @param x
     * @param y 
     */
    public DialogParser(String text, Dialog box, float x, float y) {
        this.box = box;
        
        this.x = x;
        this.y = y;
        
        // Split the text up into different words
        words = text.split(" ");

        chars = new LinkedList<>();
        
        newWord = true;
        
        boolean first = true;
        // For each word, split into characters
        for (String s : words) {
            char[] tempChars = s.toCharArray();
            
            // For each character array, make a new linked list
            LinkedList<String> charList = new LinkedList<>();
            for (char c : tempChars) {
                charList.add(Character.toString(c));
            }
            // Add a space after each word
            charList.add(" ");
            // Add this character list to the outer list of words
            chars.add(charList);
            // Start the character iterator with the first word
            if (first) {
                charIterator = charList.listIterator();
                first = false;
            }
        }
        
        wordIterator = chars.listIterator();
    }
    
    /**
     * 
     * @param delta 
     */
    public void update(int delta) {
        time += delta;
    }
    
    /**
     * 
     * @return 
     */
    public boolean hasNext() {
        return charIterator.hasNext() || wordIterator.hasNext();
    }
    
    /**
     * 
     * @return 
     */
    public String next() {
        if (charIterator.hasNext())
            return charIterator.next();
        else if (wordIterator.hasNext()) {
            charIterator = wordIterator.next().listIterator();
            return next();
        } else
            // if this method was called correctly, it should never reach this point, but just in case
            return "";      
    }
    
    /**
     * Processes and prints the dialog box
     */
    public void drawNext() throws VoyageGuiException {
        
        String next = "";
        
        if (time >= 300) {
            if (hasNext()) {
                next = next();
            } else {
                throw new VoyageGuiException("There is no next character!");
            }
        }
        
        if (newWord) {
            String currentWord = "";
            
            wordIterator.previous();
            ListIterator<String> tempWord = wordIterator.next().listIterator();
            while (tempWord.hasNext()) {
                currentWord += tempWord.next();
            }
            
            float xStart = x + DIALOG_PADDING;
            x += DIALOG_PADDING;
            y += DIALOG_PADDING;
            int totalWidth = box.getWidth() + (int)xStart - (int)DIALOG_PADDING * 2;
            
            int width = FONT.getWidth(currentWord);
            
            if (x + width > totalWidth) {
                x = xStart;
                y += FONT.getLineHeight();
            }
            
        }
        
        /*
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
        */
        
   }

    
}
