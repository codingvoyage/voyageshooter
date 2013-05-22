package gui;

import org.newdawn.slick.GameContainer;

/**
 * Voyage GUI is a custom designed graphical user interface 
 * intended to handle display of items such as the player's 
 * inventory, quest manager, dialog boxes, and more.
 * 
 * @author Brian Yang
 * @version 1.0
 */
public interface Gui<E extends Displayable> {
    
    /** Spawns the Gui element */
    public void start();
    
    /**
     * Update with delta time
     * @param gc game container
     * @param delta delta time
     */
    public void next(GameContainer gc, int delta);
    
    /** Draw the GUI element */
    public void draw();
    
    /** Return the object contained in the GUI element */
    public E getObject();
    
    /**
     * Display the contained object
     */
    public void display() throws VoyageGuiException;
}
