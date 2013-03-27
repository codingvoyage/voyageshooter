package spaceinvaders.entity;

/**
 * Entity with Attack<br/>
 * <strong>Known Implementations:</strong> Enemy, Weapon, Immovable
 * @author Brian Yang
 */
public interface Attacker {
    /** Base attack of entity */
    public double getAttack();
    /** Entity's weapon */
    public Weapon getWeapon();
    /** Fire weapon */
    public void fire();

}