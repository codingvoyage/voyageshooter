package gui;

/**
 * Represents anything that can be displayed in a Voyage GUI element
 * @author Brian Yang
 */
public interface Displayable {
    
    /**
     * Print or display the object
     * @param x top left x-coordinate
     * @param y top right y-coordinate
     * @throws InterruptedException 
     */
    public void print(float x, float y);
}
