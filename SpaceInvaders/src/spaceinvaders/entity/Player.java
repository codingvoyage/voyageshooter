package spaceinvaders.entity;
import org.newdawn.slick.*;

/**
 * Player Entity<br/>
 * Stores data and methods for the actual player
 * @author Brian Yang
 */
public class Player extends MovableEntity implements Attacker, Defender {
    
    /** DATA - Player attack */
    private Double attack;
    /** DATA - Player defense */
    private Double defense;
    /** DATA - Current HP */
    private Integer hp;
    /** DATA - Max HP */
    private Integer maxhp;
    
    /** DATA - Player lives */
    private int lives;
    
    /** DATA - Player weapon name */
    private String weapons;
    /** Player weapon */
    private Weapon weapon;
    /** DATA - Player weapon pack */
    private SelectionList<String> weaponpack;
    
    /**
     * New Game
     */
    public Player() {
        super("Cool Guy", "player1337", "spaceship", 50f, "A cool guy assigned on an even cooler mission.", 10.0);
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
    public Player(String name, String id, String image, float radius, String description, double attack, double defense, int hp, int maxhp, int lives, String weapons, double v) {
        super(name, id, image, radius, description, v);
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
        this.maxhp = maxhp;
        this.lives = lives;
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
     * @param angle angle <em>relative</em> to the entity
     */
    public void fire(float angle) {
        if(weapon == null) 
            weapon = (Weapon)EntityGroup.getEntity(weapons);
        
        weapon.fire(getX() + BULLET_OFFSET, getY() + BULLET_OFFSET, angle + getSprite().getRotation(), this, true);
    }
    
    /**
     * Fire weapon with a different direction and at a distance away
     * @param angle angle <em>relative</em> to the entity
     * @param distanceAway distance away from the entity
     */
    public void fire(float angle, double distanceAway) {
        if(weapon == null)
            weapon = (Weapon)EntityGroup.getEntity(weapons);
        
        float distanceOffsetX = (float)(Math.cos(Math.toRadians(angle))*distanceAway);
        float distanceOffsetY = (float)(Math.sin(Math.toRadians(angle))*distanceAway);
        weapon.fire(getX() + distanceOffsetX + BULLET_OFFSET,
                getY() + distanceOffsetY + BULLET_OFFSET,
                getRotation() + angle - ROTATION_FACTOR, this, true);
    }
    
    /**
     * Switch weapons to the next one in the list
     */
    public Weapon switchWeapon() {
        System.out.println(weaponpack.getList().size());
        weapon = (Weapon)EntityGroup.getEntity(weaponpack.getNext());
        weapons = weapon.getName();
        return weapon;
    }
    
    /**
     * Add weapon if it doesn't already exist
     * @param w the weapon to add
     */
    public boolean addWeapon(Weapon w) {
        return weaponpack.add(w.getName());
    }
    
    /**
     * Add weapon if it doesn't already exist
     * @param name the weapon name to add
     */
    public boolean addWeapon(String name) {
        return weaponpack.add(name);
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
     * Set HP
     * @param hp the new hp
     */
    public void setHp(int hp) {
        this.hp = hp;
    }
    
    /**
     * Deduct HP
     * @param hp the hp to deduct
     */
    @Override
    public void deductHp(int hp) throws SlickException {
        this.hp -= hp;
        if(hp <= 0) 
            EntityGroup.remove(this.getName(), false);
    }
    
    /**
     * Die - set HP to 0
     */
    @Override
    public void die() {
        hp = 0;
    }
    
    /**
     * Add life
     * @return the number of lives left
     */
    public int addLife() {
        lives++;
        return lives;
    }
    
    /**
     * Add lives
     * @param lives the number of lives to add
     * @return the number of lives left
     */
    public int addLife(int lives) {
        this.lives+=lives;
        return this.lives;
    }
    
    /**
     * Remove a life
     * @return the number of lives left
     */
    public int removeLife() {
        lives--;
        return lives;
    }
    
    /**
     * Remove lives
     * @param lives the number of lives to remove
     * @return the number of lives left
     */
    public int removeLife(int lives) {
        this.lives-=lives;
        return this.lives;
    }
    
    /**
     * Number of lives left
     * @return number of lives left
     */
    public int getLives() {
        return lives;
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
