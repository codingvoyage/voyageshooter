package spaceinvaders.entity;

import org.newdawn.slick.*;
import java.util.ArrayList;
import java.util.HashMap;
import spaceinvaders.script.SpaceInvaders;

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
    
    /** Maps all entity names to their respective entity object */
    private static final HashMap<String, Entity> entityData = new HashMap<String, Entity>();
    
    /** Sprite sheet for Entities */
    public static final PackedSpriteSheet sprites; 
    static {
        PackedSpriteSheet tempSprites = null; // required or compiler will complain that sprites isn't initialized
        try {
            tempSprites = new PackedSpriteSheet("src/spaceinvaders/entity/sprites/sprites.space");
        } catch (SlickException e) {
            System.out.println("Sprite sheet failure.");
        }
        sprites = tempSprites;
    }
    
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
     * Constructs a new set of entities<br/>
     * Calling this constructor normally is not recommended. Use a data file to load the data instead.<br/>
     * Please note that the fields in EntityGroup are static and therefore this constructor will replace everything.
     * @param enemies ArrayList of enemy entities
     * @param weapons ArrayList of weapon entities
     * @param misc ArrayList of misc entities
     */
    public EntityGroup(ArrayList<Enemy> enemies, ArrayList<Weapon> weapons, ArrayList<Misc> misc, ArrayList<Immovable> immovable) {
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
            entityData.put(e.getName(), e);
        }
        
        for (Weapon w : weapons) {
            entityData.put(w.getName(), w);
        }
        
        for (Misc m : misc) {
            entityData.put(m.getName(), m);
        }
        
        for (Immovable i : immovable) {
            entityData.put(i.getName(), i);
        }

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
     * Get Entity by Name
     * @param name name of Entity
     * @return the requested Entity
     */
    public static Entity getEntity(String name) {
        if(!mapCreated) createMap();
        if(entityData.containsKey(name)) {
            return entityData.get(name);
        } else {
            System.out.println("WARNING - No Entity by that name exists! Returning default enemy.");
            return new Entity();
        }
    }
   
    /**
     * Get Image from Sprite
     * @return the Entity's image
     */
    public static Image getImage(String ref) {
        return sprites.getSprite(ref);
    }
    
    /**
     * Spawn the Entity at a random location with default velocity<br/>
     * EntitySpawnExceptions from <code>spawn(name, vx, vy)</code> will retry spawning with this method
     * @param name the entity to spawn
     * @return the Entity of declared type that just got spawned
     */
    public static <EntityT extends Entity> EntityT spawn(String name) {
        
        Entity e = getEntity(name);
        float[] coordinates; // random coordinates
        
        if(e instanceof Enemy) { // the entity is actaully an enemy

            Enemy en = cloneEnemy(e);
            coordinates = drawRandom(en.getSprite());
            en.place(coordinates[0], coordinates[1]);
            return (EntityT)en;

        } else if(e instanceof Misc) { // the entity is actually a misc entity

            Misc mi = cloneMisc(e);
            coordinates = drawRandom(mi.getSprite());
            mi.place(coordinates[0], coordinates[1]);
            return (EntityT)mi;

        } else if(e instanceof Weapon) { // the entity is actually a weapon

            Weapon we = cloneWeapon(e);
            coordinates = drawRandom(we.getSprite());
            we.place(coordinates[0], coordinates[1]);
            return (EntityT)we;

        } else if(e instanceof Immovable) { // the entity is actually an immovable

            Immovable im = cloneImmovable(e);
            coordinates = drawRandom(im.getSprite());
            im.place(coordinates[0], coordinates[1]);
            return (EntityT)im;

        } else if(e instanceof Player) { // the entity is actually the player

            Player pl = clonePlayer(e);
            coordinates = drawRandom(pl.getSprite());
            pl.place(coordinates[0], coordinates[1]);
            return (EntityT)pl;
            
        } else {
            
            System.out.println("All else failed, so spawning a basic entity at a random location instead.");
            Entity ent = cloneEntity(e);
            coordinates = drawRandom(ent.getSprite());
            ent.place(coordinates[0], coordinates[1]);
            return (EntityT)ent;
            
        }
    }
    
    /**
     * Spawn the Entity at a random location with a specific velocity
     * @param name the movable entity to spawn
     * @param vx x velocity in mph
     * @param vy y velocity in mph
     * @return the Entity of declared type that just got spawned
     */
    public static <EntityT extends MovableEntity> EntityT spawn(String name, double vx, double vy) {
        
        Entity e = getEntity(name);
        float[] coordinates; // random coordinates
        
        try {
            
            /*
            if(!(e instanceof MovableEntity))
                throw new EntitySpawnException("You can't give a non-movable entity velocity!");
                */
            
            if(e instanceof Enemy) { // the entity is actaully an enemy
                
                Enemy en = cloneEnemy(e);
                en.setVelocity(vx, vy);
                coordinates = drawRandom(en.getSprite());
                en.place(coordinates[0], coordinates[1]);
                return (EntityT)en;
                
            } else if(e instanceof Misc) { // the entity is actually a misc entity
                
                Misc mi = cloneMisc(e);
                mi.setVelocity(vx, vy);
                coordinates = drawRandom(mi.getSprite());
                mi.place(coordinates[0], coordinates[1]);
                return (EntityT)mi;
                
            } else if(e instanceof Weapon) { // the entity is actually a weapon
                
                Weapon we = cloneWeapon(e);
                we.setVelocity(vx, vy);
                coordinates = drawRandom(we.getSprite());
                we.place(coordinates[0], coordinates[1]);
                return (EntityT)we;
                
            } else if(e instanceof Player) { // the entity is actually the player
                
                Player pl = clonePlayer(e);
                pl.setVelocity(vx, vy);
                coordinates = drawRandom(pl.getSprite());
                pl.place(coordinates[0], coordinates[1]);
                return (EntityT)pl;
                        
            } else {
                
                throw new EntitySpawnException("caught EntitySpawnException: What kind of entity are you exactly? Attempting to spawn again without velocity.");
                        
            }
            
        } catch(EntitySpawnException ex) {
            
            System.out.println(ex.getMessage());
            System.out.println("Ignoring your provided velocities.");
            return spawn(name);
            
        }   
    }
    
    /**
     * Spawn the entity at a specific location with default velocity<br/>
     * EntitySpawnExceptions from <code>spawn(name, x, y, vx, vy)</code> will retry using this method
     * @param name the entity to spawn
     * @param x x coordinate
     * @param y y coordinate
     * @return the Entity of declared type that just got spawned
     */
    public static <EntityT extends Entity> EntityT spawn(String name, float x, float y) {
        
        Entity e = getEntity(name);
        
        if(e instanceof Enemy) { // the entity is actaully an enemy

            Enemy en = cloneEnemy(e);
            en.getSprite().draw(x, y);
            en.place(x, y);
            return (EntityT)en;

        } else if(e instanceof Misc) { // the entity is actually a misc entity

            Misc mi = cloneMisc(e);
            mi.getSprite().draw(x, y);
            mi.place(x, y);
            return (EntityT)mi;

        } else if(e instanceof Weapon) { // the entity is actually a weapon

            Weapon we = cloneWeapon(e);
            we.getSprite().draw(x, y);
            we.place(x, y);
            return (EntityT)we;

        } else if(e instanceof Immovable) { // the entity is actually an immovable

            Immovable im = cloneImmovable(e);
            im.getSprite().draw(x, y);
            im.place(x, y);
            return (EntityT)im;

        } else if(e instanceof Player) { // the entity is actually the player

            Player pl = clonePlayer(e);
            pl.getSprite().draw(x, y);
            pl.place(x, y);
            return (EntityT)pl;
            
        } else {
            
            System.out.println("All else failed, so spawning a basic entity at your specified location instead.");
            Entity ent = cloneEntity(e);
            ent.getSprite().draw(x, y);
            ent.place(x, y);
            return (EntityT)ent;
            
        }
    }
    
    /**
     * Spawn the entity at a specific location with specific velocity
     * @param name the movable entity to spawn
     * @param x x coordinate
     * @param y y coordinate
     * @param vx x velocity in mph
     * @param vy y velocity in mph
     * @return the Entity of declared type that just got spawned
     */
    public static <EntityT extends MovableEntity> EntityT spawn(String name, float x, float y, double vx, double vy) {
        
        Entity e = getEntity(name);
        try {
            
            /*
            if(!(e instanceof MovableEntity))
                throw new EntitySpawnException("You can't give a non-movable entity velocity!");
                */
            
            if(e instanceof Enemy) { // the entity is actaully an enemy
                
                Enemy en = cloneEnemy(e);
                en.setVelocity(vx, vy);
                en.getSprite().draw(x, y);
                en.place(x, y);
                return (EntityT)en;
                
            } else if(e instanceof Misc) { // the entity is actually a misc entity
                
                Misc mi = cloneMisc(e);
                mi.setVelocity(vx, vy);
                mi.getSprite().draw(x, y);
                mi.place(x, y);
                return (EntityT)mi;
                
            } else if(e instanceof Weapon) { // the entity is actually a weapon
                
                Weapon we = cloneWeapon(e);
                we.setVelocity(vx, vy);
                we.getSprite().draw(x, y);
                we.place(x, y);
                return (EntityT)we;
                
            } else if(e instanceof Player) { // the entity is actually the player
                
                Player pl = clonePlayer(e);
                pl.setVelocity(vx, vy);
                pl.getSprite().draw(x, y);
                pl.place(x, y);
                return (EntityT)pl;
                        
            } else {
                
                throw new EntitySpawnException("caught EntitySpawnException: What kind of entity are you exactly? Attempting to spawn again without velocity.");
                        
            }
            
        } catch(EntitySpawnException ex) {
            
            System.out.println(ex.getMessage());
            System.out.println("Ignoring your provided velocities.");
            return spawn(name, x, y);
            
        }   
    }
    
    /**
     * Clone Basic Entity
     * @param e the Entity reference
     */
    private static Entity cloneEntity(Entity e) {
        Entity ent = e;
        ent = new Entity(ent.getName(), ent.getId(), ent.getImage(), ent.getDescription());
        return ent;
    }
    
    /**
     * Clone Enemy
     * @param e the Entity reference
     */
    private static Enemy cloneEnemy(Entity e) {
        Enemy en = (Enemy)e;
        en = new Enemy(en.getName(), en.getId(), en.getImage(), en.getDescription(), en.getAttack(), en.getDefense(), en.getWeaponName(), en.getVx(), en.getVy());
        return en;
    }
    
    /**
     * Clone Misc Entity
     * @param e the Entity reference
     */
    private static Misc cloneMisc(Entity e) {
        Misc mi = (Misc)e;
        mi = new Misc(mi.getName(), mi.getId(), mi.getImage(), mi.getDescription(), mi.getVx(), mi.getVy());
        return mi;
    }
    
    /**
     * Clone Weapon
     * @param e the Entity reference
     */
    private static Weapon cloneWeapon(Entity e) {
        Weapon we = (Weapon)e;
        we = new Weapon(we.getName(), we.getId(), we.getImage(), we.getDescription(), we.getAttack(), we.getVx(), we.getVy());
        return we;
    }
    
    /**
     * Clone Immovable Entity
     * @param e the Entity reference
     */
    private static Immovable cloneImmovable(Entity e) {
        Immovable im = (Immovable)e;
        im = new Immovable(im.getName(), im.getId(), im.getImage(), im.getDescription(), im.getAttack(), im.getDefense(), im.getWeaponName());
        return im;
    }
    
    /**
     * Clone Player
     * @param e the Entity reference
     */
    private static Player clonePlayer(Entity e) {
        Player pl = (Player)e;
        pl = new Player(pl.getName(), pl.getId(), pl.getImage(), pl.getDescription(), pl.getAttack(), pl.getDefense(), pl.getWeaponName(), pl.getVx(), pl.getVy());
        return pl;
    }
    
    /**
     * Draw the Image at a random location on the map
     * @param image the image to draw
     * @return an array of two floats representing the random x and y coordinates chosen
     */
    public static float[] drawRandom(Image image) {
        float x = (float)(Math.random() * ((SpaceInvaders.X_RESOLUTION) + 1));
        float y = (float)(Math.random() * ((SpaceInvaders.Y_RESOLUTION) + 1));
        image.draw(x, y);
        float[] coordinates = {x, y};
        return coordinates;
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