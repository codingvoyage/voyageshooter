package spaceinvaders.entity;

import spaceinvaders.script.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;

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
    /** ID of Entity */
    private String id;
    /** Description of Entity */
    private String description;
    /** Image name of Entity */
    private String image;
    /** Actual image of Entity */
    private Image sprite;
       
    /** position vector */
    public final Vector2f position = new Vector2f(300, 400);;
    
    /*
    
    private float x = 300;
    
    private float y = 400;
    */
    
    /** Default scaling factor */
    private float scale = 0.5f;
    
    /** Image base */
    private static final String IMAGE_PATH = "src/spaceinvaders/images/";
    
    /**
     * Calls ScriptableClass<br/>
     * Instance fields should be set by a data file like JSON
     */
    public Entity() {
        super();
        name = "You";
        id = "e1";
        description = "I am you.";
        image = "spaceship";
        //position = new Vector2f();
    }

    /**
     * Constructs a new Entity and calls ScriptableClass
     * @param name name of Entity
     * @param id id (index) of Entity
     * @param image the image reference name of entity
     * @param description description of Entity
     */
    public Entity(String name, String id, String image, String description) {
        super();
        this.name = name;
        this.id = id;
        this.image = image;
        this.sprite = EntityGroup.getImage(image);
        this.description = description;
        //position = new Vector2f();
    }
    
    /**
     * Constructs a new scripted Entity and calls ScriptableClass
     * @param name name of Entity
     * @param id id (index) of Entity
     * @param image the image reference name of entity
     * @param scriptID the script ID of the scripted entity
     * @param description description of Entity
     */
    public Entity(String name, String id, String image, int scriptID, String description) {
        super();
        this.name = name;
        this.id = id;
        this.image = image;
        this.sprite = EntityGroup.getImage(image);
        this.description = description;
        setMainScriptID(scriptID);
    }
        
    /**
     * Place an entity at a certain location on the map
     * @param x x coordinate
     * @param y y coordinate
     */
    public void place(float x, float y) {
        position.set(x, y);
    }
    
    /**
     * Change coordinates
     * @param x x-distance to move
     * @param y y-distance to move
     */
    public void move(double x, double y) {
        position.x += x;
        position.y += y;
    }
    
    /**
     * Set the Entity's ID tag
     * @param id the ID to set to
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Draw the Entity at its set coordinates
     */
    public void draw() {
        getSprite().draw(position.x, position.y);
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
    public String getId() {
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
        return position.x;
    }

    /**
     * Get Y
     * @return the entity's y-coordinate
     */    
    public float getY() {
        return position.y;
    }
    
    /**
     * Get the Image file name
     * @return the entity's image file name
     */
    public String getImage() {
        return image;
    }
    
    /**
     * Get the actual sprite image
     * @return entity's sprite
     */
    public Image getSprite() {
        if(sprite != null)
            return sprite;
        else {
            sprite = EntityGroup.getImage(image);
            return sprite;
        }
    }
}
