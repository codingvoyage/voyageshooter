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
    
    /** The dialog box */
    private Dialog box;
    
    /** Elapsed time */
    private int time;
    /** Font */
    public static final UnicodeFont FONT = Util.FONT;
    
    /** Linked list of characters to be printed */
    private LinkedList<LinkedList<String>> chars;
    /** Iterator for outer list to be printed */
    private ListIterator<LinkedList<String>> wordIterator;
    /** Iterator for inner list to be printed */
    private ListIterator<String> charIterator;
    
    /** Linked list of printed characters mapped to their coordinates to avoid repetitive calculations */
    private LinkedList<Coordinate> printedChars;
    /** Iterator for list of printed characters */
    private ListIterator<Coordinate> printedIterator;
    
    /** number of words printed */
    private int wordIndex;
    /** number of characters of the last word printed */
    private int charIndex;
    
    /** Begin a new word */
    private boolean newWord;
    
    /** x coordinate */
    private float x;
    /** y coordinate */
    private float y;
    
    /** dialog box offset */
    public static final float DIALOG_PADDING = 25.0f;
    
    /** left margin */
    private float xStart;
    /** width of the text area */
    private int totalWidth;
    
    /** speed of text printing in milliseconds */
    public static final int PRINT_SPEED = 25;
    
    /**
     * Print a new dialog message
     * @param text the text to print
     * @param box the box to print it in
     * @param x x-coordinate of this
     * @param y 
     */
    public DialogParser(String text, Dialog box, float x, float y) {
        this.box = box;
        
        this.x = x;
        this.y = y;
        
        // Split the text up into different words
        String[] words = text.split(" ");

        wordIndex = 0;
        charIndex = 0;
        
        xStart = x + DIALOG_PADDING;
        this.x += DIALOG_PADDING;
        this.y += DIALOG_PADDING;
        totalWidth = box.getWidth() + (int)xStart - (int)DIALOG_PADDING * 2;
        
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
        if (wordIterator.hasNext())
            wordIterator.next();
        printedChars = new LinkedList<>();
        
    }
    
    /**
     * Update the time
     * @param delta the time interval
     */
    public void update(int delta) {
        time += delta;
    }
    
    /**
     * Check if there is more to print
     * @return boolean indicating whether or not there is more to print
     */
    public boolean hasNext() {
        return charIterator.hasNext() || wordIterator.hasNext();
    }
    
    /**
     * Get the next character
     * @return the next character
     */
    public String next() {
        if (charIterator.hasNext()) {
            return charIterator.next();
        } else if (wordIterator.hasNext()) {
            charIterator = wordIterator.next().listIterator();
            newWord = true;
            return next();
        } else
            // if this method was called correctly, it should never reach this point, but just in case
            return ""; 
    }
    
    /**
     * Processes and prints the dialog box
     */
    public void drawNext() throws VoyageGuiException {
        
        printPrevious();
        if (time >= PRINT_SPEED) {
            String next = next();
            time = 0;

            if (newWord) {
                String currentWord = "";

                wordIterator.previous();
                ListIterator<String> tempWord = wordIterator.next().listIterator();
                while (tempWord.hasNext()) {
                    currentWord += tempWord.next();
                }

                int width = FONT.getWidth(currentWord);
                
                if (x + width > totalWidth) {
                    x = xStart;
                    y += FONT.getLineHeight();
                }

                newWord = false;
            
            }
            
            FONT.drawString(x, y, next);
            printedChars.add(new Coordinate<>(next, x, y));
            x += FONT.getWidth(next);  
            
        }
        
    }
    
    /**
     * Render what is already printed
     */
    public void printPrevious() {
        
        ListIterator<Coordinate> print = printedChars.listIterator();
        while (print.hasNext()) {
            Coordinate<String> next = print.next();
            FONT.drawString(next.getPosition()[0], next.getPosition()[1], next.getObject());
        }
        
    }
    
}
