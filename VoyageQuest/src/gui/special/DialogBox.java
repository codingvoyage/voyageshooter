package gui.special;

import gui.BoundedGui;
import gui.types.Dialog;

/**
 * A wrapper class to make creating and dealing with dialog boxes easier. 
 * @author Brian Yang
 */
public class DialogBox {
    
    /** The GUI element */
    private BoundedGui<Dialog> window;
    /** The dialog box */
    private Dialog dialog;
    
    public DialogBox(float x, float y, int width, int height, String text) {
        this.window = new BoundedGui<>(x, y, width, height, new Dialog(text));
        dialog = window.getObject();
    }
    
    public void start() throws InterruptedException {
        window.display();
    }
    
    public Dialog getDialog() {
        return dialog;
    }
    
    public String getText() {
        return dialog.getText();
    }
    
    public Thread getThread() {
        return dialog.getThread();
    }
}
