package spaceinvaders.entity;

/**
 * Misc Entities<br/>
 * Stores the entity data for non-enemy and non-weapon
 * @author Brian Yang
 */

public class Misc extends Entity implements Movable {
    private String name;
    private Integer id;
    private String description;
    private Double vx;
    private Double vy;

    /**
     * Constructs a new weapon entity using a data file
     */    
    public Misc() {
        super();
        // default values - should be ignored by the data file
        name = "Asteroid";
        id = 1337;
        description = "Deadly flying rocks! Avoid at all costs!";
        vx = 10.0;
        vy = 10.0;
    }
    
    /**
     * Constructs a new weapon entity
     * @param name name of entity
     * @param id index of entity
     * @param description description of entity
     */    
    public Misc(String name, int id, String description, double vx, double vy) {
        super();
        this.name = name;
        this.id = id;
        this.description = description;  
        this.vx = vx;
        this.vy = vy;
    }
    
    /** 
     * Checks if the entity should continue moving by calling the parent Entity class method 
     * and passing the appropriate velocities
     * @param delta elapsed time between checks
     * @return whether or not the entity should continue moving
     */
    @Override
    public boolean continueMove(double delta) {
        return super.continueMove(delta, vx, vy);
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
     * Get X velocity
     * @return the entity's x velocity
     */   
    @Override
    public double getVx() {
        return vx;
    }   
    
    /**
     * Get Y velocity
     * @return the entity's y velocity
     */    
    @Override
    public double getVy() {
        return vy;
    } 
}

