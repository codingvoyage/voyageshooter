package spaceinvaders.entity;

import spaceinvaders.script.SpaceInvaders;

/**
 * Weapon Entities
 * Stores the entity data for all weapons
 * @author Brian Yang
 */
public class Weapon extends MovableEntity implements Attacker {

    /** bullet's own attack */
    private Double attack;
    
    /** is the bullet fired from the player */
    private boolean fromPlayer;

    /**
     * Constructs a new weapon entity using a data file
     */    
    public Weapon() {
        // default values - should be ignored by the data file
        super("Panther Rockets", "weapon1337", "enemy1", 12f, "The most powerful weapon ever invented.", 10.0);
        attack = 9133.7;
        fromPlayer = false;
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
    public Weapon(String name, String id, String image, float radius, String description, double attack, double v) {
        super(name, id, image, radius, description,v);
        this.attack = attack;
        fromPlayer = false;
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
     * @return the Weapon entity which was just spawned
     */
    public Weapon fire(float x, float y, float angle) {
        Weapon newWeapon = EntityGroup.spawn(getName(), "bullet" + Math.random(), x, y);
        EntityGroup.bullets.add(newWeapon);
        newWeapon.setRotation(angle);
        newWeapon.draw();
        return newWeapon;
    }
    
    /**
     * Fire from another entity with attack boost
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @param angle angle <em>relative</em> to the entity to fire from
     * @param entity the entity being fired from
     * @return the Weapon entity which was just spawned
     */
    public Weapon fire(float x, float y, float angle, Attacker entity) {
        Weapon newWeapon = fire(x, y, angle);
        newWeapon.setAttack(newWeapon.getAttack() + entity.getAttack());
        return newWeapon;
    }
    
    /**
     * Fire from another entity with attack boost and at a custom velocity
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @param angle angle <em>relative</em> to the entity to fire from
     * @param entity the entity being fired from
     * @return the Weapon entity which was just spawned
     */
    public Weapon fireAtVelocity(float x, float y, float angle, double velocity, Attacker entity) {
        Weapon newWeapon = fire(x, y, angle, entity);
        newWeapon.setVelocity(velocity);
        return newWeapon;
    }
    
    /**
     * Fire at a custom velocity
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @param angle angle <em>relative</em> to the entity to fire from
     * @param entity the entity being fired from
     * @return the Weapon entity which was just spawned
     */
    public Weapon fireAtVelocity(float x, float y, float angle, double velocity) {
        Weapon newWeapon = fire(x, y, angle);
        newWeapon.setVelocity(velocity);
        return newWeapon;
    }
    
    
    /**
     * Fire from another entity with attack boost, and it might be from player
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @param angle angle <em>relative</em> to the entity to fire from
     * @param entity the entity being fired from
     * @param fromPlayer whether or not the bullet is fired from the player
     */
    public void fire(float x, float y, float angle, Attacker entity, boolean fromPlayer) {
        Weapon newWeapon = fire(x, y, angle, entity);
        newWeapon.setSource(fromPlayer);
        newWeapon.draw();
    }
    
    /**
     * Move Weapon
     */
    public void move(double delta) {
        move(getVelocity() * VELOCITY_FACTOR * (float)delta * Math.sin(Math.toRadians(getRotation())), -1 * getVelocity() * VELOCITY_FACTOR * (float)delta * Math.cos(Math.toRadians(getRotation())));
    
        //That's great and all. See if we're off the map though
        
        if ((this.getX() < -300 || this.getX() > SpaceInvaders.X_RESOLUTION + 300) ||
           (this.getY() < -300 || this.getY() > SpaceInvaders.Y_RESOLUTION + 300))
        {
            this.markForDeletion();
        }
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
     * Accessors for Weapon
     * @return itself
     */
    @Override
    public Weapon getWeapon() {
        return this;
    }
    
    /**
     * Set source status
     * @param fromPlayer is it from the player?
     */
    public void setSource(boolean fromPlayer) {
        this.fromPlayer = fromPlayer;
    }
    
    /**
     * Get Source Status
     * @return whether or not the player fired it
     */
    public boolean fromPlayer() {
        return fromPlayer;
    }
}

