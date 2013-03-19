package spaceinvaders.entity;

/**
 * Misc Entities<br/>
 * Stores the entity data for non-enemy and non-weapon
 * @author Brian Yang
 */

public class Misc extends Entity {
    private String name;
    private Integer id;
    private String description;

    /**
     * Constructs a new weapon entity using a data file
     */    
    public Misc() {
        // default values - should be ignored by the data file
        name = "Asteroid";
        id = 1337;
        description = "Deadly flying rocks! Avoid at all costs!";
    }
    
    /**
     * Constructs a new weapon entity
     * @param name name of entity
     * @param id index of entity
     * @param description description of entity
     */    
    public Misc(String name, int id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;   
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

}

