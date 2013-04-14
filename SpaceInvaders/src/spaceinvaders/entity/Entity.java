package spaceinvaders.entity;

import spaceinvaders.script.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Circle;

/**
 * An Entity is any object that appears on the map 
 * that is not part of the menu, controls, or map itself.
 * <br/><br/>
 * 
 * All instance fields whose documentation is appended with "DATA" is 
 * expected to be loaded via an external data file.
 * 
 * @author Brian Yang
 * @author Edmund Qiu
 */

public class Entity extends ScriptableClass {
    
    /** DATA - Name of Entity */
    private String name;
    /** DATA - ID of Entity */
    private String id;
    /** DATA - Description of Entity */
    private String description;
    
    /** DATA - Image name of Entity */
    private String image;
    /** Actual image of Entity */
    private Image sprite;
    
    /** DATA - The radius of the Entity's collision circle */
    private Float radius;
    /** Collision shape of Entity */
    private Circle collision;
    
    /** position vector */
    public final Vector2f position = new Vector2f(300, 400);
    
    /** rotation in degrees (facing directly south is 180 degrees) */
    private float angle;
    
    /** Default scaling factor */
    private float scale = 0.5f;
    
    /** Image base */
    private static final String IMAGE_PATH = "src/spaceinvaders/images/";
    
    /** Angle offset for rotations */
    public static float ROTATION_FACTOR = 90.0f;
    
    /** Offset for spawning bullets */
    public static float BULLET_OFFSET = 25;
    
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
        radius = 50f;
        angle = 180;
    }

    /**
     * Constructs a new Entity and calls ScriptableClass
     * @param name name of Entity
     * @param id id (index) of Entity
     * @param image the image reference name of entity
     * @param description description of Entity
     */
    public Entity(String name, String id, String image, float radius, String description) {
        super();
        this.name = name;
        this.id = id;
        this.image = image;
        this.radius = radius;
        this.sprite = EntityGroup.getImage(image);
        angle = 180;
        //sprite.setRotation(angle);
        this.description = description;
    }
    
    /**
     * Constructs a new scripted Entity and calls ScriptableClass
     * @param name name of Entity
     * @param id id (index) of Entity
     * @param image the image reference name of entity
     * @param scriptID the script ID of the scripted entity
     * @param description description of Entity
     */
    public Entity(String name, String id, String image, float radius, int scriptID, String description) {
        super();
        this.name = name;
        this.id = id;
        this.image = image;
        this.radius = radius;
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
     * Set the entity rotation
     * <strong>Note:</strong> Use the rotate (unscripted) or beginRotate (scripted) method to 
     * create a smooth rotation
     * @param angle the new angle
     */
    public void setRotation(float angle) {
        this.angle = angle;
        
        if (angle >= 360)
            angle = 360 - angle;
        sprite.setRotation(angle);
        if(sprite.getRotation() >= 360)
            sprite.setRotation(360 - angle);
    }
    
    /**
     * Set the rotation to face another entity
     * <strong>Note:</strong> Use the rotate (unscripted) or beginRotate (scripted) methods to 
     * create a smooth rotation
     * @param entity the entity to face
     */
    public void setRotation(Entity entity) {
        angle = (float)(new Vector2f(position)).sub(entity.position).getTheta() - ROTATION_FACTOR;
        sprite.setRotation(angle);
        if (angle >= 360)
            angle = 360 - angle;
        if(sprite.getRotation() >= 360)
            sprite.setRotation(360 - angle);
    }
    
    /**
     * Rotate the entity
     * <strong>Note:</strong> For a smooth rotation, include a
     * delta time parameter
     * @param angle the angle to rotate by
     */
    public void rotate(float angle) {
        this.angle += angle;
        if(sprite == null)
            getSprite();
        sprite.rotate(angle);
        if (angle >= 360)
            angle = 360 - angle;
        if(sprite.getRotation() >= 360)
            sprite.setRotation(360 - angle);
    }
    
    /**
     * Rotate the entity
     * <strong>Note:</strong> For a smooth rotation, this method can 
     * only be called from a movable entity's beginMove method.
     * @param angle the angle to rotate by
     * @delta update interval time
     */
    public void rotate(float angle, double delta) {
        float deltaAngle = angle*(float)delta;
        this.angle += deltaAngle;
        sprite.rotate(deltaAngle);
    }
    
    /**
     * Calculate the angle to rotate to face another entity<br/>
     * <strong>Note:</strong> Because of the delta time requirement, this method can 
     * only be called from a movable entity's beginMove method 
     * and only calculates the angle and does not actually rotate that angle. 
     * Pass the returned angle to the rotate(float angle, double delta) method.
     * @param entity the other entity
     * @return the angle needed to rotate
     */
    public float rotate(Entity entity) {
        return (float)(Math.acos(this.position.getNormal().dot(entity.position.getNormal())));
    }
    
    /**
     * Scripted rotation by a certain angle<br/>
     * <strong>Note:</strong> If rotating and then immediately moving, 
     * just call the beginMove(angle, pixelsToMove) method instead.
     * @param angle to angle to rotate to
     */
    public void beginRotate(float angle) {
        setTemporaryParameter(new Parameter(getRotation() - angle));
        mainThread.setRunningState(true);
    }
    
    /**
     * Get the rotation
     * @return the current rotation in degrees
     */
    public float getRotation() {
        return angle;
    }
    
    /**
     * Get the rotation in radians
     * @return the current rotation in radians
     */
    public double getRotationRad() {
        return Math.toRadians(angle);
    }
    
    /**
     * Get Name
     * @return name of entity
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get ID
     * @return id (index) of entity
     */
    public String getId() {
        return id;
    }
    
    /**
     * Get Description
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
    
    /**
     * Get the collision radius
     * @return collision radius
     */
    public float getRadius() {
        return radius;
    }
    
    /**
     * Set the collision area shape
     * @param s the collision area shape
     */
    public void setCollisionShape(Circle s) {
        collision = s;
    }
    
    /**
     * Get the collision shape
     * @return the collision shape
     */
    public Circle getCollisionShape() {
        if (collision == null)
            collision = new Circle(position.x, position.y, radius);
        collision.setX(position.x);
        collision.setY(position.y);
        return collision;
    }
}
