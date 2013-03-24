package spaceinvaders.entity;

import spaceinvaders.script.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

/**
 * An Entity is any object that appears on the map 
 * that is not part of the menu, controls, or map itself
 * 
 * Will be declared as abstract later
 * 
 * @author Brian Yang
 * @author Edmund Qiu
 */

public class Entity extends ScriptableClass {
    
    /** Name of Entity */
    private String name;
    /** ID (index) of Entity */
    private Integer id;
    /** Description of Entity */
    private String description;
    
    /** Default x coordinates */
    private float x = 400;
    /** Default y coordinates */
    private float y = 300;
    /** Default scaling factor */
    private float scale = 1;
    
    /** origin */
    public static final double ORIGIN = 0.0;
    
    /** map boundary factor */
    public static final float EDGE_FACTOR = 75.0f;
    /** velocity conversion from miles to pixels */
    public static final double VELOCITY_FACTOR = 0.01;
    
    /** rotation size */
    public static final float ROTATION_SIZE = 0.2f;
    /** step size */
    public static final float STEP_SIZE = 0.4f;
    /** back up size */
    public static final float BACK_SIZE = 0.075f;
    
    private Rectangle image;
    
    /**
     * Calls ScriptableClass<br/>
     * Instance fields should be set by a data file like JSON
     */
    public Entity() {
        super();
        name = "You";
        id = 0;
        description = "I am you.";
    }

    /**
     * Constructs a new Entity and calls ScriptableClass
     * @param name name of Entity
     * @param id id (index) of Entity
     * @param description description of Entity
     */
    public Entity(String name, int id, String description) {
        super();
        this.name = name;
        this.id = id;
        this.description = description;
    }
        
    /**
     * Place an entity at a certain location on the map
     * @param x x coordinate
     * @param y y coordinate
     */
    public void place(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Change coordinates
     * @param x x-distance to move
     * @param y y-distance to move
     */
    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }
    
    /**
     * Accessors for Name
     * @return name of entity
     */
    public String getName() {
        return name;
    }
    
    /**
     * Accessors for ID
     * @return id (index) of entity
     */
    public int getId() {
        return id;
    }
    
    /**
     * Accessors for Description
     * @return description of entity
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Get X
     * @return the entity's x-coordinate
     */    
    public float getX() {
        return x;
    }

    /**
     * Get Y
     * @return the entity's y-coordinate
     */    
    public float getY() {
        return y;
    }
    
    /** 
     * Render graphics
     */
    public void renderGraphics(Graphics g) {
        image = new Rectangle(-500, -500, 10, 10);
        g.draw(image);
    }
}
