package spaceinvaders.entity;

/**
 * Enemy Entities<br/>
 * Stores the entity data for all enemies
 * @author Brian Yang
 */
public class Enemy extends MovableEntity implements Attacker, Defender {
   
    /** Base attack of Enemy */
    private Double attack;
    /** Base defense of Enemy */
    private Double defense;
    
    /**
     * Constructs a new enemy entity using a data file
     */    
    public Enemy() {
        // default values - should be ignored by the data file
        super("Panther Ship", 1337, "The most powerful and evil creature you will ever meet in your life.", 10.0, 10.0);
        attack = 9133.7; 
        defense = 9133.7;
    }
    
    /**
     * Constructs a new enemy entity
     * @param name name of entity
     * @param id index of entity
     * @param description description of entity
     * @param attack base attack of entity
     * @param defense base defense of entity
     */    
    public Enemy(String name, int id, String description, double attack, double defense, double vx, double vy) {
        super(name, id, description,vx,vy);
        this.attack = attack;
        this.defense = defense;
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
