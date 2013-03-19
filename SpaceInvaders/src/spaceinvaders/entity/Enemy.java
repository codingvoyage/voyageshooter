package spaceinvaders.entity;

/**
 * Enemy Entities<br/>
 * Stores the entity data for all enemies
 * @author Brian Yang
 */
public class Enemy extends Entity implements Movable {
    private String name;
    private Integer id;
    private String description;
    private Double attack;
    private Double vx;
    private Double vy;
    
    /**
     * Constructs a new enemy entity using a data file
     */    
    public Enemy() {
        super();
        // default values - should be ignored by the data file
        name = "Panther Ship";
        id = 1337;
        description = "The most powerful and evil creature you will ever meet in your life.";
        attack = 9133.7; 
        vx = 10.0;
        vy = 10.0;
    }
    
    /**
     * Constructs a new enemy entity
     * @param name name of entity
     * @param id index of entity
     * @param description description of entity
     * @param attack base attack of entity
     */    
    public Enemy(String name, int id, String description, double attack, double vx, double vy) {
        super();
        this.name = name;
        this.id = id;
        this.description = description;
        this.attack = attack;
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
     * Accessors for Attack
     * @return attack of entity
     */
    public double getAttack() {
        return attack;
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
