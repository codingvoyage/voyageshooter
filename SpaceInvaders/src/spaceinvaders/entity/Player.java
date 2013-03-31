package spaceinvaders.entity;
import org.newdawn.slick.*;

/**
 * Player Entity<br/>
 * Stores data and methods for the actual player
 * @author Brian Yang
 */
public class Player extends MovableEntity implements Attacker, Defender {
    
    /** Player attack */
    private Double attack;
    /** Player defense */
    private Double defense;
    /** Current HP */
    private Integer hp;
    /** Max HP */
    private Integer maxhp;
    /** Player weapon name */
    private String weapons;
    /** Player weapon */
    private Weapon weapon;
    /** Player lives */
    private int lives;
    
    /**
     * New Game
     */
    public Player() {
        super("Cool Guy", "player1337", "spaceship", "A cool guy assigned on an even cooler mission.", 10.0);
        attack = 1.0;
        defense = 1.0;
        hp = 100;
        maxhp = 100;
        lives = 7;
        weapons = "Rainbow Laser";
        weapon = new Weapon();
    }
    
    /**
     * New Special Game
     * @param name name of entity
     * @param id index of entity
     * @param description description of entity
     * @param attack base attack of entity
     * @param defense base defense of entity
     */
    public Player(String name, String id, String image, String description, double attack, double defense, int hp, String weapons, double v) {
        super(name, id, image, description, v);
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
        this.maxhp = hp;
        lives = 7;
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
        weapon.fire(getX() + BULLET_OFFSET, getY() + BULLET_OFFSET, angle + getSprite().getRotation());
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
     * Accessors for HP
     * @return the player's HP
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
     * Deduct HP
     * @param hp the hp to deduct
     */
    @Override
    public void deductHp(int hp) throws SlickException {
        this.hp -= hp;
        if(hp <= 0) 
            EntityGroup.remove(this.getName());
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
