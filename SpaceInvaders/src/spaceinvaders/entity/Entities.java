package spaceinvaders.entity;

import java.util.ArrayList;

/**
 * Entity Data Storage
 * Holds a global list of all entities
 * @author Brian Yang
 */

public class Entities {
    
    // instance fields
    private ArrayList<Enemy> enemies;
    private ArrayList<Weapon> weapons;
    private ArrayList<Misc> misc;
    
    /*
     * Enemy Count
     * @return the number of enemy entities
     */
    
    public int getEnemyCount() {
        return enemies.size();
    }
    
    /*
     * Weapon Count
     * @return the number of weapon entities
     */
    
    public int getWeaponCount() {
        return weapons.size();
    }
    
    /*
     * Misc Count
     * @return the number of misc entities
     */
    
    public int getMiscCount() {
        return misc.size();
    }
}
