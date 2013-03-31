package spaceinvaders.entity;

import spaceinvaders.script.SpaceInvaders;

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
        super("Panther Rockets", "weapon1337", "enemy1", "The most powerful weapon ever invented.", 10.0);
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
    public Weapon(String name, String id, String image, String description, double attack, double v) {
        super(name, id, image, description,v);
        this.attack = attack;
        
    }
    
    /**
     * Fire<br/>
     * Because this is a weapon itself, this default method does absolutely nothing.
     */
    @Override
    public void fire() {
        // the actual bullet doesn't fire anything silly
    }
    
    /**
     * Fire from another entity
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @param angle angle <em>relative</em> to the entity to fire from
     */
    public void fire(float x, float y, float angle) {
        Weapon newWeapon = EntityGroup.spawn(getName(), "bullet" + Math.random(), x, y);
        EntityGroup.bullets.add(newWeapon);
        newWeapon.setRotation(angle);
        newWeapon.draw();
        
        System.out.println("Weapon should now be drawn");
        System.out.println("Weapon's coordiantes are: " + getX() + getY());
        System.out.println("Weapon's rotation is: " + getRotation());
        
        move(300, 250);
        newWeapon.draw();
        System.out.println("Weapon's coordiantes are: " + getX() + getY());
        System.out.println("Bullet is done moving");
    }

    /**
     * Move Weapon
     */
    public void move(double delta) {
        move(STEP_SIZE * (float)delta * Math.sin(Math.toRadians(getRotation())), -STEP_SIZE * (float)delta * Math.cos(Math.toRadians(getRotation())));
    
        //That's great and all. See if we're off the map though
        
        if ((this.getX() < -500 || this.getX() > SpaceInvaders.X_RESOLUTION + 500) ||
           (this.getY() < -500 || this.getY() > SpaceInvaders.Y_RESOLUTION + 500))
        {
            this.markForDeletion();
        }
            
    
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

