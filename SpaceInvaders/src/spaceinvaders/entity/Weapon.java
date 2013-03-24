package spaceinvaders.entity;

/**
 * Weapon Entities
 * Stores the entity data for all weapons
 * @author Brian Yang
 */
public class Weapon extends MovableEntity implements Attacker {

    private Double attack;

    /**
     * Constructs a new weapon entity using a data file
     */    
    public Weapon() {
        // default values - should be ignored by the data file
        super("Panther Rockets", 1337, "The most powerful weapon ever invented.", 10.0, 10.0);
        attack = 9133.7;
    }
    
    /**
     * Constructs a new weapon entity
     * @param name name of entity
     * @param id index of entity
     * @param description description of entity
     * @param attack base attack of entity
     * @param vx x-velocity of entity
     * @param vy y-velocity of entity
     */    
    public Weapon(String name, int id, String description, double attack, double vx, double vy) {
        super(name, id, description,vx,vy);
        this.attack = attack;
        
    }
    
    /**
     * Fire
     */
    @Override
    public void fire() {
        // the actual bullet doesn't fire anything silly
    }
    
    /**
     * Fire from another entity
     * @param x starting x coordinate
     * @param y starting y coordinate
     */
    public void fire(float x, float y) {
        
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
     * Accessors for Weapon
     * @return itself
     */
    @Override
    public Weapon getWeapon() {
        return this;
    }
}

