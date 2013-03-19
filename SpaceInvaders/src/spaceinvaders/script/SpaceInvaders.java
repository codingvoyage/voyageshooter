package spaceinvaders.script;

import spaceinvaders.entity.*;
import org.newdawn.slick.*;

/**
 * Space Invaders Game
 * A (maybe not so) simple space invaders game
 * 
 * @author Brian Yang
 * @author Edmund Qiu
 * @version 0.1
 */

public class SpaceInvaders extends BasicGame {
    ScriptManager scriptCollection;
    ScriptReader scriptReader;
    
    //Our testing
    Entity testEntity;
    
    ThreadManager threadManager;
    
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
        
        //Initialize the ScriptManager
        scriptCollection = new ScriptManager();
        scriptCollection.loadScript("script.txt", 0);
        scriptCollection.loadScript("shortdemo.txt", 1);
        scriptCollection.loadScript("toread.txt", 2);
        scriptCollection.loadScript("ROCKET MOTTO.txt", 3); 
        scriptCollection.loadScript("ROCKET MOTTO ONCE.txt", 4); 
        scriptCollection.loadScript("Loader.txt", 5);   
        
  
        //Initialize ScriptReader, passing it the ScriptManager handle
        scriptReader = new ScriptReader(scriptCollection);
        
        //Initialize the collection of threads
        threadManager = new ThreadManager(scriptReader);
        
        scriptReader.setThreadHandle(threadManager);
        
        //Create our test entity
        testEntity = new Entity();
        
        //Create a thread which governs this entity with Script #4
        Thread entityThread = new Thread(5);
        
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
        
        //Of course, our pride in our team!!
        g.drawString("We are Team Coding Voyage!", 100, 50);
        
        //Pull up the script of the main thread of the entity
        Script sampleScript = scriptCollection.getScriptAtID(
                testEntity.getMainThread().getScriptID());
        int linesToPrint = sampleScript.getLineCount();
        
        //Print all the lines of sampleScript
        for (int i = 0; i < linesToPrint; i++)
        {
            g.drawString(sampleScript.getLine(i).toString(), 100, 100 + 16*i);     
        }
        
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

        app.setDisplayMode(1024, 768, false);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(60);
        //This is important. I found out that with this command, it will limit
        //the frames displayed per second to around 60. We DON'T want frames
        //being drawn 2000 times per second.

        // Temporarily commented out to test entities
        app.start();

        /*
         * Start JSON Entities
         */

        JsonReader<EntityGroup> reader = new JsonReader<EntityGroup>(EntityGroup.class, "src/spaceinvaders/entity/EntityData.json");
        if(reader.readJson()) {
            
            EntityGroup data = reader.getObject();
            // Show it.
            System.out.println(data);

            System.out.println();
            
            System.out.println("Entity Counts:");
            System.out.println(data.getEnemyCount());
            System.out.println(data.getWeaponCount());
            System.out.println(data.getMiscCount());
            
            System.out.println();
            
            System.out.println("Getting Entity Description by calling their Name");
            System.out.println(data.getEnemy("Minion").getDescription());
            System.out.println(data.getEnemy("I don't exist").getDescription());
            System.out.println(data.getWeapon("Water Gun").getDescription());
            System.out.println(data.getMisc("Asteroid").getDescription());
            
            System.out.println();
            
            System.out.println("Getting Entity Description by calling their ID");
            System.out.println(data.getEnemy(0).getDescription());
            System.out.println(data.getEnemy(9000).getDescription());
            System.out.println(data.getWeapon(0).getDescription());
            System.out.println(data.getMisc(0).getDescription());
        
        }
    }
}
