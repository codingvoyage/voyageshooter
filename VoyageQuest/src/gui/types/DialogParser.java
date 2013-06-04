package gui.types;

import gui.GuiManager;
import gui.VoyageGuiException;
import java.util.LinkedList;
import java.util.ListIterator;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
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
    /** top margin */
    private float yStart;
    /** width of the text area */
    private int totalWidth;
    /** height of the text area */
    private int totalHeight;
    
    /** speed of text printing in milliseconds */
    public static final int PRINT_SPEED = 20;
    
    /** waiting for user */
    private boolean waiting = false;
    
    /** blink continue cursor */
    private boolean blink = true;
    /** blink speed */
    public int blinkTimer = 0;
    
    /** status */
    private boolean continuePrinting = true;
    
    /** profile animation */
    private Animation profile;
    
    /**
     * Print a new dialog message
     * @param text the text to print
     * @param box the box to print it in
     * @param x x-coordinate of this
     * @param y y-coordinate of this
     */
    public DialogParser(String text, Dialog box, float x, float y) {
        this.box = box;
        
        this.x = x;
        this.y = y;
        
        // Split the text up into different words
        String[] words = text.split(" ");
        
        xStart = x + DIALOG_PADDING;
        yStart = y + DIALOG_PADDING;
        this.x += DIALOG_PADDING;
        this.y += DIALOG_PADDING;
        totalWidth = box.getWidth() + (int)xStart - (int)DIALOG_PADDING * 3;
        totalHeight = box.getHeight() + (int)yStart - (int)DIALOG_PADDING * 3;
        
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
        
        profile = box.getSpeaker().profile;
        
    }
    
    /**
     * Update the time
     * @param delta the time interval
     */
    public void update(GameContainer gc, int delta) {
        time += delta;
        
        if (waiting) {
            Input input = gc.getInput();
            if (input.isKeyDown(Input.KEY_Z)) {
                
                waiting = false;
                printedChars.clear();
                x = xStart;
                y = yStart;
                if (!hasNext()) {
                    // the box has no more to print. close it.
                    GuiManager.close(box.getWindow());
                    continuePrinting = false;
                    return;
                }
            }
        }
        
        blinkTimer += delta;
        
        if (blinkTimer >= 400) {
            blink = !blink;
            blinkTimer = 0;
        }
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
        } else {
            waiting = true;
            return ""; 
        }
    }
    
    /**
     * Processes and prints the dialog box
     */
    public void drawNext() throws VoyageGuiException {
        
        if (profile != null)
            drawProfile();
        printPrevious();
        if (!waiting) {
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
                        if (y > totalHeight) {
                            waiting = true;
                            charIterator.previous();
                            return;
                        }
                    }

                    newWord = false;

                }

                FONT.drawString(x, y, next);
                printedChars.add(new Coordinate<>(next, x, y));
                x += FONT.getWidth(next);  

            }
        } else {
            if (blink)
                FONT.drawString(xStart + box.getWidth(), yStart + box.getHeight(), "Press Z");
        }
        
    }
    
    /**
     * Render what is already printed
     */
    private void printPrevious() {
        
        ListIterator<Coordinate> print = printedChars.listIterator();
        while (print.hasNext()) {
            Coordinate<String> next = print.next();
            FONT.drawString(next.getPosition()[0], next.getPosition()[1], next.getObject());
        }
        
    }
    
    /**
     * Draw profile
     */
    private void drawProfile() {
        
        
    }
    
    /**
     * Get the status
     * @return whether or not it should continue printing
     */
    public boolean getStatus() {
        return continuePrinting;
    }
    
}