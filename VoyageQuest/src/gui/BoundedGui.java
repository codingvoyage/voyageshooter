package gui;

import org.newdawn.slick.geom.Vector2f;

/**
 * Fixed dimension GUI element
 * @author Brian Yang
 */
public class BoundedGui implements Gui {
    
    /** position */
    private Vector2f position;
    /** width */
    public final int width;
    /** height */
    public final int height;
    
    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    public BoundedGui(float x, float y, int width, int height) {
        position = new Vector2f(x, y);
        this.width = width;
        this.height = width;
    }
}
