package gui.types;

import gui.Displayable;
import gui.VoyageGuiException;
import org.newdawn.slick.GameContainer;

/**
 * Dialog box GUI element
 * @author Brian Yang
 */
public class Dialog implements Displayable {
    
    /** The String to be printed */
    private String text;
    /** x coordinate */
    private float x;
    /** y coordinate */
    private float y;
    /** Is the dialog being printed? */
    private boolean isPrinting;
    /** width */
    private int width;
    /** height */
    private int height;
    /** the dialog parser */
    private DialogParser parser;
    
    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text 
     */
    public Dialog(float x, float y, String text, int width, int height) {
        this.text = text;
        this.width = width;
        this.height = height;
        parser = new DialogParser(text, this, x, y);
    }
    
    /**
     * Print the dialog
     */
    @Override
    public void print() throws VoyageGuiException {
        parser.drawNext();
    }
    
    /**
     * Update with delta time
     * @param delta delta time
     */
    @Override
    public void next(GameContainer gc, int delta) {
        parser.update(gc, delta);
    }
    
    /**
     * 
     * @return 
     */
    public String getText() {
        return text;
    }
    
    /**
     * 
     * @return 
     */
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
}
