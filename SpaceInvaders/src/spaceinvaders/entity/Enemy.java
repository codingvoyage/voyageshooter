package spaceinvaders.entity;
import org.newdawn.slick.*;

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
    /** Current HP */
    private Integer hp;
    /** Max HP */
    private int maxhp;
    /** Weapon name used by Enemy */
    private String weapons;
    /** Weapon used by Enemy */
    private Weapon weapon;
    
    /**
     * Constructs a new enemy entity using a data file
     */    
    public Enemy() {
        // default values - should be ignored by the data file
        super("Panther Ship", "enemy1337", "enemy1", 50f, 52, "The most powerful and evil creature you will ever meet in your life.", 10.0);
        attack = 9133.7; 
        defense = 9133.7;
        hp = 100;
        maxhp = 100;
        weapons = "Default";
        weapon = new Weapon();
    }
    
    /**
     * Constructs a new enemy entity
     * @param name name of entity
     * @param id index of entity
     * @param image name of image reference
     * @param description description of entity
     * @param attack base attack of entity
     * @param defense base defense of entity
     * @param weapons name of weapon used by entity
     * @param vx x velocity
     * @param vy y velocity
     */    
    public Enemy(String name, String id, String image, float radius, int scriptID, String description, double attack, double defense, int hp, String weapons, double v) {
        super(name, id, image, radius, scriptID, description, v);
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
        this.maxhp = hp;
        this.weapons = weapons;
        weapon = (Weapon)EntityGroup.getBaseEntity(weapons);
    }
    
    /**
     * Fire weapon with the same direction 
     * from the location of the enemy
     */
    @Override
    public void fire() {
        fire(0);
    }
    
    /**
     * Fire weapon with a different direction 
     * from the location of the enemy
     * @param angle angle <em>relative</em> to the enemy
     */
    public void fire(float angle) {
        if(weapon == null) 
            weapon = (Weapon)EntityGroup.getEntity(weapons);
        weapon.fire(getX() + BULLET_OFFSET, getY() + BULLET_OFFSET, angle + getRotation(), this);
    }
    
    
    /**
     * Fire weapon with a different direction and at a distance away
     * @param angle angle <em>relative</em> to the enemy
     * @param distanceAway distance away from the enemy
     */
    public void fire(float angle, double distanceAway) {
        if(weapon == null) 
            weapon = (Weapon)EntityGroup.getEntity(weapons);
        
        float distanceOffsetX = (float)(Math.cos(Math.toRadians(angle))*distanceAway);
        float distanceOffsetY = (float)(Math.sin(Math.toRadians(angle))*distanceAway);
        weapon.fire(getX() + distanceOffsetX + BULLET_OFFSET,
                getY() + distanceOffsetY + BULLET_OFFSET,
                getRotation() + angle - ROTATION_FACTOR,
                this);
    }
    
    /**
     * Set the Attack
     * @param attack the new attack
     */
    @Override
    public void setAttack(double attack) {
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
     * Accessors for Defense
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
        if(hp <= 0) 
            EntityGroup.remove(this.getName(), true);
    }
    
    /**
     * Die - set HP to 0
     */
    @Override
    public void die() {
        hp = 0;
    }
    
    /**
     * Accessors of HP
     * @return the current HP
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
        if(weapon != null) {
            return weapon;
        } else {
            weapon = (Weapon)EntityGroup.getEntity(weapons);
            return weapon;
        }
    }
}
