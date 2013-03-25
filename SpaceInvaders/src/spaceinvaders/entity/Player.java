package spaceinvaders.entity;

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
        super("Cool Guy", 1337, "A cool guy assigned on an even cooler mission.", 10.0, 10.0);
        attack = 1.0;
        defense = 1.0;
        lives = 7;
        weapons = "Rainbow Laser";
        weapon = EntityGroup.getWeapon("Rainbow Laser");
    }
    
    /**
     * New Special Game
     * @param name name of entity
     * @param id index of entity
     * @param description description of entity
     * @param attack base attack of entity
     * @param defense base defense of entity
     */
    public Player(String name, int id, String description, double attack, double defense, String weapons, double vx, double vy) {
        super(name, id, description, vx, vy);
        this.attack = attack;
        this.defense = defense;
        lives = 7;
        this.weapons = weapons;
        weapon = EntityGroup.getWeapon(weapons);
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
     * Accessors for Weapon
     * @return weapon used by entity
     */
    //@Override
    public Weapon getWeapon() {
        return weapon;
    }
}
