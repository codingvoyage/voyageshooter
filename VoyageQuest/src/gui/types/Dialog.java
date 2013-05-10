package gui.types;

import gui.Displayable;

/**
 * Dialog box GUI element
 * @author Brian Yang
 */
public class Dialog implements Displayable {
    
    /** The String to be printed */
    private String text;
    /** Is the dialog being printed? */
    private boolean isPrinting;
    /** width */
    private int width;
    /** height */
    private int height;
    
    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text 
     */
    public Dialog(String text, int width, int height) {
        this.text = text;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Print the dialog
     */
    @Override
    public void print(float x, float y) {
        DialogParser parser = new DialogParser(text, this, x, y);
        parser.draw();
    }
    
    /**
     * 
     * @return 
     */
    public String getText() {
        return text;
    }
    
    public int getWidth() {
        return width;
    }
}
