package gui.types;

import gui.Displayable;

/**
 * Dialog box GUI element
 * @author Brian Yang
 */
public class Dialog implements Displayable {
    
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
    public Dialog(String text) {
        this.text = text;
    }
    
    /**
     * Print the dialog
     */
    @Override
    public void print(float x, float y) throws InterruptedException {
        DialogParser parser = new DialogParser(text, this);
        thread = new Thread(parser);
        thread.start();
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
    public Thread getThread() {
        return thread;
    }
}
