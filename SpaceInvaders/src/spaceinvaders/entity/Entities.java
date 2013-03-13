package spaceinvaders.entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Entity Data Storage<br/>
 * Class holds a global list and HashMap of entities<br/><br/>
 * 
 * It is recommended, but not at all required, that entity data be loaded from a separate data file such as JSON
 * @author Brian Yang
 */

public final class Entities {
    
    // lists of each type of entity - ID = index
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Weapon> weapons;
    private final ArrayList<Misc> misc;
    
    // we don't want to have to refer to all entities by ID numbers
    public final HashMap<String, Integer> enemyData;
    public final HashMap<String, Integer> weaponData;
    public final HashMap<String, Integer> miscData;
    
    /**
     * Constructs a new set of entities using a data file<br/>
     * Should be called via a separate data file such as JSON
     */
    public Entities() {
        // default ArrayList - should be overwritten by the data file
        enemies = new ArrayList<Enemy>();
        weapons = new ArrayList<Weapon>();
        misc = new ArrayList<Misc>();
        
        // initialize the HashMaps
        enemyData = new HashMap<String, Integer>();
        weaponData = new HashMap<String, Integer>();
        miscData = new HashMap<String, Integer>();
        
        // create HashMaps from the ArrayList
        createMap();
    }
    
     /**
     * Constructs a new set of entities
     * @param enemies ArrayList of enemy entities
     * @param weapons ArrayList of weapon entities
     * @param misc ArrayList of misc entities
     */
    public Entities(ArrayList<Enemy> enemies, ArrayList<Weapon> weapons, ArrayList<Misc> misc) {
        // default ArrayList - should be overwritten by the data file
        this.enemies = enemies;
        this.weapons = weapons;
        this.misc = misc;
        
        // initialize the HashMaps
        enemyData = new HashMap<String, Integer>();
        weaponData = new HashMap<String, Integer>();
        miscData = new HashMap<String, Integer>();
        
        // create HashMaps from the ArrayList
        createMap();
    }
    
    /**
     * Construct HashMaps for each type of entity<br/>
     * We don't want to have to refer to each entity by its ID/index number
     */
    private void createMap() {
        for(Enemy e : enemies) {
            enemyData.put(e.getName(), e.getId());
        }
        
        for (Weapon w : weapons) {
            weaponData.put(w.getName(), w.getId());
        }
        
        for (Misc m : misc) {
            miscData.put(m.getName(), m.getId());
        }
    }
    
    /**
     * Enemy Count
     * @return the number of enemy entities
     */
    
    public int getEnemyCount() {
        return enemies.size();
    }
    
    /**
     * Weapon Count
     * @return the number of weapon entities
     */
    
    public int getWeaponCount() {
        return weapons.size();
    }
    
    /**
     * Misc Count
     * @return the number of misc entities
     */
    
    public int getMiscCount() {
        return misc.size();
    }
}