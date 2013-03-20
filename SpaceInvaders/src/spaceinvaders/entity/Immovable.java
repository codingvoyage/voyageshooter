package spaceinvaders.entity;

/**
 * Immovable Entities<br/>
 * Stores the entity data for all entities that can't move
 * @author Brian Yang
 */
public class Immovable extends Entity implements Attacker, Defender {

    private Double attack;
    private Double defense;
    
    /**
     * Constructs a new immovable entity using a data file
     */    
    public Immovable() {
        // default values - should be ignored by the data file
        super("Floating Mine", 1337, "A small but powerful floating mine that will obliterate anything that comes near it.");
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
        super(name, id, description);
        this.attack = attack;
    }
    
    /**
     * Accessors for Attack
     * @return attack of entity
     */
    @Override
    public double getAttack() {
        return attack;
    }  
    
    /**
     * Accessors for Attack
     * @return attack of entity
     */
    @Override
    public double getDefense() {
        return defense;
    }  
}
