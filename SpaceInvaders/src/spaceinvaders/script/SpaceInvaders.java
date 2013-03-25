package spaceinvaders.script;

import spaceinvaders.entity.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.fills.GradientFill;

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
    ScriptManager scriptCollection;
    /** The actual script reader */
    ScriptReader scriptReader;
    
    /** Manages all the scripting threads */
    ThreadManager threadManager;
        
    /** A test enemy entity that moves */
    Enemy testEntity;
    
    /** horizontal resolution */
    public static final int X_RESOLUTION = 1024;
    /** vertical resolution */
    public static final int Y_RESOLUTION = 768;
    
    /** The space background */
    private Image space;
    
    /** The actual player */
    
    /** TEMPORARY TEST VARIABLES */

    
    /** Data for all entities */
    private EntityGroup entities;
    
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
        //This initializes stuff
        gc.setMinimumLogicUpdateInterval(20);
        
        /* Load images */
        space = new Image("src/spaceinvaders/images/bluestar.jpg");
        
        /* load the data for all entities */     
        if(loadEntities()) {
            System.out.println("Entity data has successfully been loaded.");
        } else {
            /* The entities failed to load, therefore we will use the default entities */
            System.out.println("WARNING - Entity data has failed to load! Loading blank entity group.");
        }
        
       //Initialize the ScriptManager
        scriptCollection = new ScriptManager();
        scriptCollection.loadScript("script.txt", 0);
        scriptCollection.loadScript("shortdemo.txt", 1);
        scriptCollection.loadScript("toread.txt", 2);
        scriptCollection.loadScript("ROCKET MOTTO.txt", 3); 
        scriptCollection.loadScript("ROCKET MOTTO ONCE.txt", 4); 
        scriptCollection.loadScript("Loader.txt", 5);   
        
        scriptCollection.loadScript("mainscript.txt", 9);  
        scriptCollection.loadScript("thescript.txt", 10);  
        scriptCollection.loadScript("thirdscript.txt", 12);
        
        scriptCollection.loadScript("AUXTHREAD.txt", 17); 
        scriptCollection.loadScript("MASTERTEST.txt", 18); 
        scriptCollection.loadScript("AUXSCRIPT.txt", 19);
        scriptCollection.loadScript("SECONDTHREAD.txt", 15);
        scriptCollection.loadScript("recursiontest", 13);
        scriptCollection.loadScript("Enemy.txt", 30);
        
  
        //Initialize ScriptReader, passing it the ScriptManager handle
        scriptReader = new ScriptReader(scriptCollection);
        
        //Initialize the collection of threads
        threadManager = new ThreadManager(scriptReader);
        
        scriptReader.setThreadHandle(threadManager);
        
        //Create our test entity
        testEntity = EntityGroup.getEnemy("Minion");
        
        //Create a thread which governs this entity with Script #4
        //Thread entityThread = new Thread(30);
        
        Thread entityThread = new Thread(30);
        //18 5
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
        //control(gc, delta);
 
    }

    /**
     * Render the game window
     * @param gc the game container
     * @param g the graphics engine
     * @throws SlickException something went horribly wrong with Slick
     */
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        //Any and all graphics/rendering functions which should be called
        //with the drawing of each frame go HERE

        // give EntityGroup the Graphics engine so it can used later if needed
        // g is only needed for drawing shapes. Not needed for rendering images.
        // Use EntityGroup.getGraphics()
        EntityGroup.receiveGraphics(g);
        
        // draw the space background
        space.draw(0,0);
        
        // Test spawning an enemy
        
        //System.out.println(EntityGroup.getEnemyCount());
        //System.out.println(enemy.getImagePath());
        if (!testEntity.spawn(testEntity.getX(), testEntity.getY())) {
            System.out.println("Well then, it looks like the enemy failed to spawn.");
        }
        
        g.drawString("Bullet: " + testEntity.getX() + ", " + testEntity.getY(), 600, 40);
        g.drawString("This game is currently in testing and nothing should work properly.", 300, 50);
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

        app.setDisplayMode(X_RESOLUTION, Y_RESOLUTION, false);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(60);
        //This is important. I found out that with this command, it will limit
        //the frames displayed per second to around 60. We DON'T want frames
        //being drawn 2000 times per second.

        app.start();

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
}
