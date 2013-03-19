package spaceinvaders.entity;

/**
 * Immovable Entities<br/>
 * Stores the entity data for all entities that can't move
 * @author Brian Yang
 */
public class Immovable extends Entity {
    private String name;
    private Integer id;
    private String description;
    private Double attack;
    
    /**
     * Constructs a new immovable entity using a data file
     */    
    public Immovable() {
        super();
        // default values - should be ignored by the data file
        name = "Floating Mine";
        id = 1337;
        description = "A small but powerful floating mine that will obliterate anything that comes near it.";
        attack = 99999.0; 
    }
    
    /**
     * Constructs a new immovable entity
     * @param name name of entity
     * @param id index of entity
     * @param description description of entity
     * @param attack base attack of entity
     */    
    public Immovable(String name, int id, String description, double attack) {
        super();
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
