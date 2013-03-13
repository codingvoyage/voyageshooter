package spaceinvaders.entity;

/**
 * Enemy Entities<br/>
 * Stores the entity data for all enemies
 * @author Brian Yang
 */
public class Enemy extends Entity {
    private String name;
    private Integer id;
    private String description;
    private Double attack;
    
    /**
     * Constructs a new enemy entity using a data file
     */    
    public Enemy() {
        // default values - should be ignored by the data file
        name = "Panther Ship";
        id = 1337;
        description = "The most powerful and evil creature you will ever meet in your life.";
        attack = 9133.7; 
    }
    
    /**
     * Constructs a new enemy entity
     * @param name name of entity
     * @param id index of entity
     * @param description description of entity
     * @param attack base attack of entity
     */    
    public Enemy(String name, int id, String description, double attack) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.attack = attack;
        
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

}
