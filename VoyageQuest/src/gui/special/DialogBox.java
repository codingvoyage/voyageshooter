package gui.special;

import gui.BoundedGui;
import gui.VoyageGuiException;
import gui.types.Dialog;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

/**
 * A wrapper class to make creating and dealing with dialog boxes easier. 
 * @author Brian Yang
 */
public class DialogBox {
    
    /** The GUI element */
    private BoundedGui<Dialog> window;
    
    public static final int DEFAULT_X = 150;
    public static final int DEFAULT_Y = 550;
    public static final int DEFAULT_WIDTH = 750;
    public static final int DEFAULT_HEIGHT = 160;
    
    /**
     * Default dialog box with default coordinates
     * @param text 
     */
    public DialogBox(String text) {
        Dialog dialog = new Dialog(DEFAULT_X, DEFAULT_Y, text, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.window = new BoundedGui<>(DEFAULT_X, DEFAULT_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT, dialog);
        dialog.setWindow(window);
    }
    
    /**
     * Dialog box with default coordinates and specified color
     * @param text
     * @param start
     * @param end 
     */
    public DialogBox(String text, Color start, Color end) {
        Dialog dialog = new Dialog(DEFAULT_X, DEFAULT_Y, text, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.window = new BoundedGui<>(DEFAULT_X, DEFAULT_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT, start, end, dialog);
        dialog.setWindow(window);
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text 
     */
    public DialogBox(float x, float y, int width, int height, String text) {
        this.window = new BoundedGui<>(x, y, width, height, new Dialog(x, y, text, width, height));
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text
     * @param start
     * @param end 
     */
    public DialogBox(float x, float y, int width, int height, String text, Color start, Color end) {
        this.window = new BoundedGui<>(x, y, width, height, start, end, new Dialog(x, y, text, width, height));
    }
    
    /**
     * Start the dialog box
     */
    public void start() {
        window.start();
    }
    
    /**
     * Draw the window
     */
    public void draw() {
        window.draw();
    }
    
    /**
     * 
     * @param gc
     * @param delta 
     */
    public void next(GameContainer gc, int delta) {
        window.next(gc, delta);
    }
    
    /**
     * 
     * @throws VoyageGuiException 
     */
    public void printNext() throws VoyageGuiException {
        window.display();
    }
    
    /**
     * Get Gui element
     * @return the gui window
     */
    public BoundedGui<Dialog> getGui() {
        return window;
    }
    
    /**
     * Get the dialog object
     * @return the dialog object
     */
    public Dialog getDialog() {
        return window.getObject();
    }
    
    /**
     * Get the text
     * @return the dialog text
     */
    public String getText() {
        return window.getObject().getText();
    }
}
