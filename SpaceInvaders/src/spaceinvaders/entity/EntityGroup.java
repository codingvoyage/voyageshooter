package spaceinvaders.entity;

import org.newdawn.slick.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Entity Data Storage<br/>
 * Class holds a global list and HashMap of entities<br/><br/>
 * 
 * It is recommended, but not at all required, that entity data be loaded from a separate data file such as JSON.<br/>
 * Each game can only have one group of entities. This data is accessible by all classes.<br/>
 * The data sets are private and there is intentionally no way to add to them. Data is loaded once in the beginning only and cannot be changed later.
 * @author Brian Yang
 */

public final class EntityGroup {
    
    /** Lists all the Enemies */
    private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    /** Lists all the Weapons */
    private static ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    /** Lists all the Misc entities */
    private static ArrayList<Misc> misc = new ArrayList<Misc>();
    /** Lists all the Immovable entities */
    private static ArrayList<Immovable> immovable = new ArrayList<Immovable>();
    
    /** Maps Enemy names to their ID */
    private static final HashMap<String, Integer> enemyData = new HashMap<String, Integer>();
    /** Maps Weapon names to their ID */
    private static final HashMap<String, Integer> weaponData = new HashMap<String, Integer>();
    /** Maps Misc entity names to their ID */
    private static final HashMap<String, Integer> miscData = new HashMap<String, Integer>();
    /** Maps Immovable entity names to their ID */
    private static final HashMap<String, Integer> immovableData = new HashMap<String, Integer>();
    
    /** The graphics engine provided by Slick2D */
    private static Graphics g;
    
    /** 
     * The ArrayLists are only assigned values after the constructor is run, therefore we can't create the maps here.<br/>
     * The names are mapped to IDs the first time it is needed.
     */
    private static boolean mapCreated;
    
    /**
     * Constructs a new set of entities using a data file<br/>
     * Should be called via a separate data file such as JSON
     */
    public EntityGroup() {
        mapCreated = false;
    }
    
     /**
     * Constructs a new set of entities
     * @param enemies ArrayList of enemy entities
     * @param weapons ArrayList of weapon entities
     * @param misc ArrayList of misc entities
     */
    public EntityGroup(ArrayList<Enemy> enemies, ArrayList<Weapon> weapons, ArrayList<Misc> misc, ArrayList<Immovable> immovable) {

        /* This is horribly unelegant, but is the only way to have the ArrayLists be static final. */
        EntityGroup.enemies = enemies;
        EntityGroup.weapons = weapons;
        EntityGroup.misc = misc;
        EntityGroup.immovable = immovable;
        mapCreated = false;
    }
    
    /**
     * Construct HashMaps for each type of entity<br/>
     * We don't want to have to refer to each entity by its ID/index number
     */
    private static void createMap() {
        for(Enemy e : enemies) {
            enemyData.put(e.getName(), e.getId());
        }
        
        for (Weapon w : weapons) {
            weaponData.put(w.getName(), w.getId());
        }
        
        for (Misc m : misc) {
            miscData.put(m.getName(), m.getId());
        }
        
        for (Immovable i : immovable) {
            immovableData.put(i.getName(), i.getId());
        }
        
        System.out.println("Hashmaps created");
        mapCreated = true;
    }

    /**
     * The graphics engine<br/>
     * Passed by the render method<br/>
     * To use the graphics engine, simply call <code>EntityGroup.getGraphics()</code>
     */
    public static void receiveGraphics(Graphics g) {
        
        EntityGroup.g = g;
    }
    
    /**
     * Get the Graphics Engine
     * @return graphics engine
     */
    public static Graphics getGraphics() {
        return g;
    }
    
    /**
     * Render the graphics for all entities
     */
    public static void renderGraphics() {
        for (Enemy e : enemies) {
            //e.(g);
        }
    }
    
    /**
     * Get Enemy by Name
     * @param name name of Enemy
     * @return the requested Enemy
     */
    public static Enemy getEnemy(String name) {
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
    public static Weapon getWeapon(String name) {
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
    public static Misc getMisc(String name) {
        if(!mapCreated) createMap();
        if(miscData.containsKey(name)) {
            return misc.get(miscData.get(name));
        } else {
            System.out.println("WARNING - No Misc entity exists with that name! Returning default misc.");
            return new Misc();
        }
    }
        
     /**
     * Get Misc entity by Name
     * @param name name of Immovable entity
     * @return the requested Immovable entity
     */
    public static Immovable getImmovable(String name) {
        if(!mapCreated) createMap();
        if(immovableData.containsKey(name)) {
            return immovable.get(immovableData.get(name));
        } else {
            System.out.println("WARNING - No immovable entity exists with that name! Returning default immovable.");
            return new Immovable();
        }
    }
      
    /**
     * Get Enemy by ID
     * @param id index/id of Enemy
     * @return the requested Enemy
     */
    public static Enemy getEnemy(int id) {
        if(id < enemies.size() && id > -1) {
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
    public static Weapon getWeapon(int id) {
        if(id < weapons.size() && id > -1) {
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
        if(id < misc.size() && id > -1) {
            return misc.get(id);
        } else {
            System.out.println("WARNING - Misc entity id does not exist! Returning default misc.");
            return new Misc();
        }
    }
    
    /**
     * Get Immovable entity by ID
     * @param id index/id of Immovable entity
     * @return the requested Immovable entity
     */
    public static Immovable getImmovable(int id) {
        if(id < immovable.size() && id > -1) {
            return immovable.get(id);
        } else {
            System.out.println("WARNING - Immovable entity id does not exist! Returning default misc.");
            return new Immovable();
        }
    }
    
    /**
     * Enemy Count
     * @return the number of enemy entities
     */
    
    public static int getEnemyCount() {
        return enemies.size();
    }
    
    /**
     * Weapon Count
     * @return the number of weapon entities
     */
    
    public static int getWeaponCount() {
        return weapons.size();
    }
    
    /**
     * Misc Count
     * @return the number of misc entities
     */
    
    public static int getMiscCount() {
        return misc.size();
    }
    
    /**
     * Misc Count
     * @return the number of misc entities
     */
    
    public static int getImmovableCount() {
        return immovable.size();
    }
}