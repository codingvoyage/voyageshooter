package gui;

/**
 * Something went wrong with Voyage GUI
 * @author Brian Yang
 */
public class VoyageGuiException extends Exception {
    
    /**
     * Throw a new exception
     */
    public VoyageGuiException() {}
    
    /**
     * Throw a new exception
     * @param msg the message to print
     */
    public VoyageGuiException(String msg) {
        super("VoyageGuiException: " + msg);
    }
    
}
