package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Vector2f;

/**
 * Any GUI element that can be resized by the user
 * @author Brian Yang
 */
public class ResizableGui<E extends Displayable> extends Gui<E> {
    
    /**
     * Construct a new default bounded GUI
     * @param x top left x-coordinate
     * @param y top left y-coordinate
     * @param width width of window
     * @param height height of window
     * @param object the object contained in the GUI element
     */
    public ResizableGui(float x, float y, int width, int height, E object) {
        super(x, y, width, height, object);
    }
    
    /**
     * Construct a new bounded GUI with specified corner radius
     * @param x top left x-coordinate
     * @param y top left y-coordinate
     * @param width width of window
     * @param height height of window
     * @param cornerRadius rounded corner radius
     * @param object the object contained in the GUI element
     */
    public ResizableGui(float x, float y, int width, int height, float cornerRadius, E object) {
        super(x, y, width, height, cornerRadius, object);
    }
    
    /**
     * Construct a new bounded GUI with a specified corner radius and a constant color
     * @param x top left x-coordinate
     * @param y top left y-coordinate
     * @param width width of window
     * @param height height of window
     * @param cornerRadius rounded corner radius
     * @param color the color throughout
     * @param object the object contained in the GUI element
     */
    public ResizableGui(float x, float y, int width, int height, float cornerRadius, Color color, E object) {
        super(x, y, width, height, cornerRadius, color, object);
    }
    
    /**
     * Construct a new bounded GUI with a specified corner radius and gradient coloring
     * @param x top left x-coordinate
     * @param y top left y-coordinate
     * @param width width of window
     * @param height height of window
     * @param cornerRadius rounded corner radius
     * @param start the start gradient color
     * @param end the end gradient color
     * @param object the object contained in the GUI element
     */
    public ResizableGui(float x, float y, int width, int height, float cornerRadius, Color start, Color end, E object) {
        super(x, y, width, height, cornerRadius, start, end, object);
    }
    
    /**
     * Construct a new bounded GUI with default specified corner radius and a constant color
     * @param x top left x-coordinate
     * @param y top left y-coordinate
     * @param width width of window
     * @param height height of window
     * @param color the color throughout
     * @param object the object contained in the GUI element
     */
    public ResizableGui(float x, float y, int width, int height, Color color, E object) {
        super(x, y, width, height, color, object);
    }
    
    /**
     * Construct a new bounded GUI with default specified corner radius and gradient coloring
     * @param x top left x-coordinate
     * @param y top left y-coordinate
     * @param width width of window
     * @param height height of window
     * @param start the start gradient color
     * @param end the end gradient color
     * @param object the object contained in the GUI element
     */
    public ResizableGui(float x, float y, int width, int height, Color start, Color end, E object) {
        super(x, y, width, height, start, end, object);
    }
    
    public void next(GameContainer gc, int delta) {
        
    }
    
    public void resize() {
        
    }
    
}
