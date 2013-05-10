package gui.special;

import gui.BoundedGui;
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
        this.window = new BoundedGui<>(x, y, width, height, start, end, new Dialog(text, width, height));
        dialog = window.getObject();
    }
    
    /**
     * 
     */
    public void start() {
        window.display();
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
