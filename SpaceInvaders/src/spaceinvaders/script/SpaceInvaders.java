package spaceinvaders.script;

import spaceinvaders.entity.*;
import spaceinvaders.entity.upgrades.Shop;
import spaceinvaders.GUI.DisplayBox;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import java.awt.Font;


/**
 * Space Invaders Game
 * 
 * An exciting space invaders game created 
 * by two AP Computer Science students at 
 * Hunter College High School
 * 
 * Copyright (c) 2013 Team Coding Voyage
 *
 * Licensed under the terms of the GNU General Public License v3
 * as published by the Free Software Foundation.
 * 
 * @author Brian Yang
 * @author Edmund Qiu
 * @version 0.5
 */

/**
 * Creates, renders, and updates the actual game window
 */
public class SpaceInvaders extends BasicGame {
    /** All the scripts to be read */
    public static ScriptManager scriptCollection;
    /** The actual script reader */
    public static ScriptReader scriptReader;
    /** Manages all the scripting threads */
    public static ThreadManager threadManager;
    
    public static DisplayBox textManager;
        
    /** A test enemy entity that moves */
    private static Enemy testEntity;
    
    /** horizontal resolution */
    public static final int X_RESOLUTION = 1024;
    /** vertical resolution */
    public static final int Y_RESOLUTION = 768;
    /** are we doing fullscreen */
    public static final boolean IS_FULLSCREEN = false;
    
    /** The space background */
    private static Image space;
    
    /** The actual player */
    public static Player player;
    public static Image playerSprite;
    
    /** Upgrades shop */
    public static Shop shop;
    
    /** Data for all entities */
    private static EntityGroup entities;
    
    /** Keyboard control */
    static boolean enableKeyboard;
    
    /** Font to be used */
    TrueTypeFont trueTypeFont;
    
    /**
     * Construct a new game
     */
    public SpaceInvaders() {
       super("We are Team Coding Voyage!");
    }

    /**
     * Initialize the game container and start the scripting engine
     * @param gc the new game container
     * @throws SlickException something went wrong with the Slick engine
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.setShowFPS(false);
        Font font = new java.awt.Font("Verdana", Font.BOLD, 20);
        trueTypeFont = new TrueTypeFont(font, true);
        
        enableKeyboard = true;
      
        /* load the data for all entities */     
        loadEntities();
        
        //Set the minimum and maximum update intervals please
        gc.setMinimumLogicUpdateInterval(20);
        gc.setMaximumLogicUpdateInterval(20);
        
        loadScripts();
        
    }


    /**
     * Update the screen
     * @param gc the game container
     * @param delta time interval
     * @throws SlickException something went horribly wrong with Slick
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        //Should work UNTIL we start having the ability to create
        //new threads, upon which you should examine this carefully to make
        //sure that there aren't massive off-by-one-errors.
        
        threadManager.act(delta);
        
        /** user keyboard control */
        if (enableKeyboard) 
            EntityGroup.control(gc, delta);
        
        //Check collision detection
        EntityGroup.checkCollision();
        
        //Kill any entities which are dead.
        
        
        // Continue moving
        continueStepping(delta);
        
        //Move on to next dialogue box
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_RETURN) && enableKeyboard) {
            textManager.next();
        }
        
    }

    /**
     * Render the game window
     * @param gc the game container
     * @param g the graphics engine
     * @throws SlickException something went horribly wrong with Slick
     */
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        
        EntityGroup.receiveGraphics(g);
        
        // draw the space background
        space.draw(0,0);
        
        // draw all the active entities
        EntityGroup.draw();
        
        if (textManager.isDisplaying == true)
            textManager.draw(g);
        
        drawControls(gc, g);
        
        
    }

    /**
     * The main method<br/>
     * Create the game window and start the game!
     * @param args command line arguments
     * @throws SlickException something went horribly wrong with Slick
     */
    public static void main(String[] args) throws SlickException {
        /*
         * Start Game Window Construction
         */
        
        AppGameContainer app = new AppGameContainer(new SpaceInvaders());

        app.setDisplayMode(X_RESOLUTION, Y_RESOLUTION, IS_FULLSCREEN);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(60);

        app.start();

    }
    
    /** 
     * ____________________________________
     * 
     * 
     * START ALL NON-DEFAULT SLICK METHODS 
     * 
     * 
     * ____________________________________
     */
    
    
    /**
     * Loads all scripting relating things
     */
    private void loadScripts() throws SlickException {
        textManager = new DisplayBox();
        
        //Initialize the ScriptManager
        scriptCollection = new ScriptManager();
        
        //Load the loader script...
        scriptCollection.loadScript("loader.cfg", 0);
        
        //Initialize ScriptReader, passing it the ScriptManager handle
        scriptReader = new ScriptReader(scriptCollection);
        
        //Initialize the collection of threads
        threadManager = new ThreadManager(scriptReader);
        scriptReader.setThreadHandle(threadManager);
        
        
        //Now create a thread that uses the loading script, 
        //adding it to threadManager and running it
        Thread loadingThread = new Thread(0);
        threadManager.addThread(loadingThread);
        threadManager.act(0.0);
        
        space = new Image("src/spaceinvaders/images/bluestar.jpg");
        scriptReader.setEntityHandle(entities);
        
        player = EntityGroup.spawn("Player", "p1", X_RESOLUTION/2 - 50, Y_RESOLUTION/2 + 200);
        //Create our test entity
        testEntity = (Enemy)EntityGroup.getBaseEntity("Minion");
        
        
        //Create a thread which governs this entity with Script 
        Thread entityThread = new Thread(31);
        
        //Set the main thread of the entity to this thread.
        testEntity.setMainThread(entityThread);
        
        //Set the details of the thread
        entityThread.setLineNumber(0);
        entityThread.setName("main");
        entityThread.setRunningState(false);
        entityThread.setScriptable(testEntity);
        
        //Add this thread to the collection of threads
        threadManager.addThread(entityThread);
    }
    
    /**
     * Continue stepping
     */
    private void continueStepping(int delta) throws SlickException {
        boolean continueStepping = !EntityGroup.bullets.isEmpty();
        int index = 0;
        Thread currentThread;
        while (continueStepping)
        {
            //Get current thread...
            Weapon w = EntityGroup.bullets.get(index);
            
            //Should any bullets be deleted right now?
            if (w.isMarkedForDeletion())
            {
                EntityGroup.bullets.remove(index);
                //index is unchanged, since everything shifts back by one
                //we'll be on track for the next one by NOT MOVING
                EntityGroup.remove(w, false);
            }
            else 
            //Otherwise, just act on it.
            {
                w.move(delta);
                index++;
            }
            
            //Stop when we've reached the last thread.
            if (index == EntityGroup.bullets.size()) {
                continueStepping = false;
            }
        }
    }
    
    /**
     * Loads all entity data from a separate data file
     * @return a boolean indicating whether or not the entity data have been successfully loaded
     */
    private boolean loadEntities() {
        JsonReader<EntityGroup> reader = new JsonReader<EntityGroup>(EntityGroup.class, "src/spaceinvaders/entity/EntityData.json");
        if(reader.readJson()) {
            entities = reader.getObject();
            return true;
        }
        entities = new EntityGroup();
        return false;
    }
    
    /**
     * Loads data needed for the shop
     * @return a boolean indicating whether or not the shop data has been successfully loaded
     */
    private boolean loadShop() {
        JsonReader<Shop> reader = new JsonReader<Shop>(Shop.class, "src/spaceinvaders/entity/upgrades/Upgrades.json");
        if(reader.readJson()) {
            shop = reader.getObject();
            return true;
        }
        shop = new Shop();
        return false;
    }
    
    /**
     * Draws all on-screen controls and information
     * @param gc the game container
     * @param g the graphics engine
     * @throws SlickException something went horribly wrong with Slick
     */
    private void drawControls(GameContainer gc, Graphics g) throws SlickException {
        
        g.setFont(trueTypeFont);
        
        
        g.setColor(Color.white);
        
        
        g.drawString("FPS: " + gc.getFPS(), 0, 0);
                
                
        
        g.drawString("Coordinates: " + player.getX() + ", " + player.getY(), 600, 40);
        g.drawString("HP: " + player.getHp() + "/" + player.getMaxHp(), 600, 60);
        g.drawString("Lives: " + player.getLives(), 600, 80);
            
        if(player.getHp() <= 0) {
            
            if(player.getLives() > 0) {
                g.drawString("You died. Press R to use a life and revive yourself.", X_RESOLUTION/2 - 200, Y_RESOLUTION/2 + 200);
                Input input = gc.getInput();
                if (input.isKeyDown(Input.KEY_R)) {
                    player.removeLife(); // deduct a life
                    player.place(X_RESOLUTION/2 - 50, Y_RESOLUTION/2 + 200);
                    player.setHp(player.getMaxHp()); // max out hp
                } else {
                    player.setHp(0); // prevent the offscreen player from taking damage
                    player.place(-200, -300);
                }
            } else {
                g.drawString("GAME OVER!", X_RESOLUTION/2 - 50, Y_RESOLUTION/2 + 200);
                player.setHp(0); // prevent the offscreen player from taking damage
                player.place(-200, -300);
            }
        }
        
        g.drawString((new Integer(EntityGroup.bullets.size())).toString() + " bullets in play", 600, 100);
        
    }
    
    
    

}
