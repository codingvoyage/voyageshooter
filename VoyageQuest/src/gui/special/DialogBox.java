package gui.special;

import gui.BoundedGui;
import gui.VoyageGuiException;
import gui.types.Dialog;
import org.newdawn.slick.Color;

/**
 * A wrapper class to make creating and dealing with dialog boxes easier. 
 * @author Brian Yang
 */
public class DialogBox {
    
    /** The GUI element */
    private BoundedGui<Dialog> window;
    /** The dialog box */
    private Dialog dialog;
    
    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text 
     */
    public DialogBox(float x, float y, int width, int height, String text, Color start, Color end) {
        this.window = new BoundedGui<>(x, y, width, height, start, end, new Dialog(x, y, text, width, height));
        dialog = window.getObject();
    }
    
    /**
     * 
     */
    public void start() {
        window.draw();
    }
    
    public void next(int delta) {
        dialog.next(delta);
    }
    
    public void printNext() throws VoyageGuiException {
        dialog.print();
    }
    
    /**
     * 
     * @return 
     */
    public Dialog getDialog() {
        return dialog;
    }
    
    /**
     * 
     * @return 
     */
    public String getText() {
        return dialog.getText();
    }
}
