package spaceinvaders.entity;

/**
 * Misc Entities<br/>
 * Stores the entity data for non-enemy and non-weapon
 * @author Brian Yang
 */

public class Misc extends MovableEntity {

    /**
     * Constructs a new weapon entity using a data file
     */    
    public Misc() {
        // default values - should be ignored by the data file
        super("Asteroid", "misc1337", "enemy1", 50f, "Deadly flying rocks! Avoid at all costs!", 10.0);
    }
    
    /**
     * Constructs a new weapon entity
     * @param name name of entity
     * @param id index of entity
     * @param description description of entity
     * @param vx v-velocity of entity
     * @param vy y-velocity of entity
     */    
    public Misc(String name, String id, String image, float radius, String description, double v) {
        super(name, id, image, radius, description, v); 
    }
}

