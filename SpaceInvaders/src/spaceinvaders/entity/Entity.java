package spaceinvaders.entity;

import spaceinvaders.script.*;

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
    /** x coordinate */
    private double x;
    /** y coordinate */
    private double y;
    
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
    public void place(double x, double y) {
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
    public double getX() {
        return x;
    }

    /**
     * Get Y
     * @return the entity's y-coordinate
     */    
    public double getY() {
        return y;
    }
    
}
