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
    
    /** Lists all the Enemies */
    public final ArrayList<Enemy> enemies;
    /** Lists all the Weapons */
    public final ArrayList<Weapon> weapons;
    /** Lists all the Misc entities */
    public final ArrayList<Misc> misc;
    
    /** Maps Enemy names to their ID */
    public final HashMap<String, Integer> enemyData;
    /** Maps Weapon names to their ID */
    public final HashMap<String, Integer> weaponData;
    /** Maps Misc entity names to their ID */
    public final HashMap<String, Integer> miscData;
    
    /** The ArrayList is only assigned values after the constructor is run, therefore we can't create the map here.<br/>
     * The names are mapped to IDs the first time it is needed.
     */
    private boolean mapCreated;
    
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
        
        mapCreated = false;
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
        
        mapCreated = false;
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
        
        System.out.println("Hashmaps created");
        mapCreated = true;
    }

    /**
     * Get Enemy by Name
     * @param name name of Enemy
     * @return the requested Enemy
     */
    public Enemy getEnemy(String name) {
        if(!mapCreated) createMap();
        if(enemyData.containsKey(name)) {
            return enemies.get(enemyData.get(name));
        } else {
            System.out.println("WARNING - No Enemy by that name exists! Returning default enemy.");
            return new Enemy();
        }
    }
    
     /**
     * Get Weapon by Name
     * @param name index/id of Weapon
     * @return the requested Weapon
     */
    public Weapon getWeapon(String name) {
        if(!mapCreated) createMap();
        if(weaponData.containsKey(name)) {
            return weapons.get(weaponData.get(name));
        } else {
            System.out.println("WARNING - No Weapon by that name exists! Returning default weapon.");
            return new Weapon();
        }
    }
    
     /**
     * Get Misc entity by Name
     * @param name name of Misc entity
     * @return the requested Misc entity
     */
    public Misc getMisc(String name) {
        if(!mapCreated) createMap();
        if(miscData.containsKey(name)) {
            return misc.get(miscData.get(name));
        } else {
            System.out.println("WARNING - No Misc entity exists with that name! Returning default misc.");
            return new Misc();
        }
    }
      
    /**
     * Get Enemy by ID
     * @param id index/id of Enemy
     * @return the requested Enemy
     */
    public Enemy getEnemy(int id) {
        if(id < enemies.size()) {
            return enemies.get(id);
        } else {
            System.out.println("WARNING - Enemy id does not exist! Returning default enemy.");
            return new Enemy();
        }
    }
    
     /**
     * Get Weapon by ID
     * @param id index/id of Weapon
     * @return the requested Weapon
     */
    public Weapon getWeapon(int id) {
        if(id < weapons.size()) {
            return weapons.get(id);
        } else {
            System.out.println("WARNING - Weapon id does not exist! Returning default weapon.");
            return new Weapon();
        }
    }
    
     /**
     * Get Misc entity by ID
     * @param id index/id of Misc entity
     * @return the requested Misc entity
     */
    public Misc getMisc(int id) {
        if(id < misc.size()) {
            return misc.get(id);
        } else {
            System.out.println("WARNING - Misc entity id does not exist! Returning default misc.");
            return new Misc();
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