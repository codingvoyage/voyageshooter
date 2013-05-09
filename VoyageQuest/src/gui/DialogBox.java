package gui;

/**
 * Dialog boxes
 * @author Brian Yang
 */
public class DialogBox extends BoundedGui {
    
    /** The String to be printed */
    private String text;
    /** The Thread handling the printing */
    private Thread thread;
    /** Is the dialog being printed? */
    private boolean isPrinting;
    
    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text 
     */
    public DialogBox(float x, float y, int width, int height, String text) {
        super(x, y, width, height);
        this.text = text;
    }
    
    /**
     * 
     */
    public void print() throws InterruptedException {
        DialogParser parser = new DialogParser(text, this);
        thread = new Thread(parser);
        thread.start();
    }
}
