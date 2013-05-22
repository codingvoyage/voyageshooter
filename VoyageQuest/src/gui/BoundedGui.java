package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.Vector2f;
        
/**
 * Fixed dimension GUI element
 * @author Brian Yang
 */
public class BoundedGui<E extends Displayable> implements Gui {
    
    /** position */
    private Vector2f position;
    
    /** width */
    public final int width;
    /** height */
    public final int height;
    
    /** background image */
    private RoundedRectangle rect;
    /** corner radius */
    public final float CORNER_RADIUS;
    
    /** gradient color start */
    private Color start;
    /** gradient color end */
    private Color end;
    
    /** the object contained in the GUI element */
    private E object;
    
    /**
     * Construct a new default bounded GUI
     * @param x top left x-coordinate
     * @param y top left y-coordinate
     * @param width width of window
     * @param height height of window
     * @param object the object contained in the GUI element
     */
    public BoundedGui(float x, float y, int width, int height, E object) {
        position = new Vector2f(x, y);
        this.width = width;
        this.height = width;
        
        CORNER_RADIUS = 40.0f;
        rect = new RoundedRectangle(x, y, width, height, CORNER_RADIUS);
        
        start = new Color(166, 250, 252, 75); // Color: #A6FAFC with alpha 75%
        end = new Color(205, 255, 145, 75); // Color #CDFF91 with alpha 75%
        
        this.object = object;
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
    public BoundedGui(float x, float y, int width, int height, float cornerRadius, E object) {
        position = new Vector2f(x, y);
        this.width = width;
        this.height = width;
        
        CORNER_RADIUS = cornerRadius;
        rect = new RoundedRectangle(x, y, width, height, CORNER_RADIUS);
        
        start = new Color(166, 250, 252, 75); // Color: #A6FAFC with alpha 75%
        end = new Color(205, 255, 145, 75); // Color #CDFF91 with alpha 75%
        
        this.object = object;
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
    public BoundedGui(float x, float y, int width, int height, float cornerRadius, Color color, E object) {
        position = new Vector2f(x, y);
        this.width = width;
        this.height = width;
        
        CORNER_RADIUS = cornerRadius;
        rect = new RoundedRectangle(x, y, width, height, CORNER_RADIUS);
        
        start = color;
        end = color;
        
        this.object = object;
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
    public BoundedGui(float x, float y, int width, int height, float cornerRadius, Color start, Color end, E object) {
        position = new Vector2f(x, y);
        this.width = width;
        this.height = width;
        
        CORNER_RADIUS = cornerRadius;
        rect = new RoundedRectangle(x, y, width, height, CORNER_RADIUS);
        
        this.start = start;
        this.end = end;
        
        this.object = object;
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
    public BoundedGui(float x, float y, int width, int height, Color color, E object) {
        position = new Vector2f(x, y);
        this.width = width;
        this.height = width;
        CORNER_RADIUS = 40.0f;
        rect = new RoundedRectangle(x, y, width, height, CORNER_RADIUS);
        
        start = color;
        end = color;
        
        this.object = object;
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
    public BoundedGui(float x, float y, int width, int height, Color start, Color end, E object) {
        position = new Vector2f(x, y);
        this.width = width;
        this.height = width;
        
        CORNER_RADIUS = 40.0f;
        rect = new RoundedRectangle(x, y, width, height, CORNER_RADIUS);
        
        this.start = start;
        this.end = end;
        
        this.object = object;
    }
    
    /**
     * Starts the Gui element
     */
    @Override
    public void start() {
        GuiManager.add(this);
    }
    
    /**
     * Draw the GUI element
     */
    @Override
    public void draw() {
        ShapeRenderer.fill(rect, (new GradientFill(0, 0, start, width/3, height/3, end, true)) );
    }
    
    /**
     * Update with delta time
     */
    @Override
    public void next(GameContainer gc, int delta) {
        object.next(gc, delta);
    }
    
    /**
     * Display the contained object
     */
    @Override
    public void display() throws VoyageGuiException {
        object.print();
    }
    
    /**
     * Get the contained object
     * @return the object contained the GUI element
     */
    @Override
    public E getObject() {
        return object;
    }
}
