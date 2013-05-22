package gui;

import org.newdawn.slick.GameContainer;

/**
 * Represents anything that can be displayed in a Voyage GUI element
 * @author Brian Yang
 */
public interface Displayable {
    
    /**
     * Update with delta time
     * @param gc game container
     * @param delta delta time
     */
    public void next(GameContainer gc, int delta);
    
    /**
     * Print or display the object
     * @param x top left x-coordinate
     * @param y top right y-coordinate
     * @throws InterruptedException 
     */
    public void print() throws VoyageGuiException;
}
