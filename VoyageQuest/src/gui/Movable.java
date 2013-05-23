package gui;

/**
 * Gui elements that can be dragged around
 * @author Brian Yang
 */
public interface Movable<E extends Displayable> {
    
    /**
     * Change the x coordinates
     * @param x the new x coordinate
     */
    public void setX(float x);
    
    /**
     * Change the y coordinates
     * @param y the new y coordinate
     */
    public void setY(float y);
    
}
