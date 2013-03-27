package spaceinvaders.entity;
import org.newdawn.slick.*;

/**
 * Immovable Entities<br/>
 * Stores the entity data for all entities that can't move
 * @author Brian Yang
 */
public class Immovable extends Entity implements Attacker, Defender {

    /** Base attack of Entity */
    private Double attack;
    /** Base defense of Entity */
    private Double defense;
    /** Entity's health points */
    private Integer hp;
    /** Max HP */
    private int maxhp;
    /** Weapon name used by Entity */
    private String weapons;
    /** Weapon used by Entity */
    private Weapon weapon;
    
    /**
     * Constructs a new immovable entity using a data file
     */    
    public Immovable() {
        // default values - should be ignored by the data file
        super("Floating Mine", "immovable1337", "enemy1", "A small but powerful floating mine that will obliterate anything that comes near it.");
        attack = 99999.0; 
        defense = 99999.0;
        hp = 100;
        maxhp = 100;
        weapons = "Bomb";
        weapon = new Weapon();
    }
    
    /**
     * Constructs a new enemy entity
     * @param name name of entity
     * @param id index of entity
     * @param description description of entity
     * @param attack base attack of entity
     * @param defense base defense of entity
     * @param weapons name of weapon used by entity
     * @param vx x velocity
     * @param vy y velocity
     */     
    public Immovable(String name, String id, String image, String description, double attack, double defense, int hp, String weapons) {
        super(name, id, image, description);
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
        this.maxhp = hp;
        this.weapons = weapons;
        weapon = (Weapon)EntityGroup.getBaseEntity(weapons);
    }
    
    /**
     * Fire<br/>
     * A weapon is the thing thats fired, so do nothing here.
     */
    @Override
    public void fire() {
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
      
    /**
     * Deduct HP
     * @param hp the hp to deduct
     */
    @Override
    public void deductHp(int hp) throws SlickException {
        this.hp -= hp;
    }
    
    /**
     * Accessors for HP
     * @return the current hp of entity
     */
    @Override
    public int getHp() {
        return hp;
    }
    
    /**
     * Accessors for HP
     * @return the player's HP
     */
    @Override
    public int getMaxHp() {
        return maxhp;
    }
    
    /**
     * Accessors for Weapon Name
     * @return name of weapon used by entity
     */
    public String getWeaponName() {
        return weapons;
    }
    
    /**
     * Accessors for Weapon
     * @return weapon used by entity
     */
    @Override
    public Weapon getWeapon() {
        return weapon;
    }
}
