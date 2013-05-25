package voyagequest;

import gui.VoyageGuiException;
import gui.GuiManager;
import gui.special.*;
import voyagequest.DoubleRect;
import scripting.*;
import scripting.Thread;
import map.*;

import org.newdawn.slick.*;
import java.util.ArrayList;

/**
 * Voyage Quest RPG
 * Copyright (c) 2013 Team Coding Voyage.
 * 
 * @author Edmund Qiu
 * @author Brian Yang
 */

public class VoyageQuest extends BasicGame {
    /** x resolution */
    public static int X_RESOLUTION = 1024;
    /** y resolution */
    public static int Y_RESOLUTION = 768;
    
    /** full screen mode */
    public static boolean FULLSCREEN = false;
    /** a rectangle of the screen*/
    public static final DoubleRect SCREEN_RECT = new DoubleRect(0, 0, X_RESOLUTION, Y_RESOLUTION);
    
    /** All the scripts to be read */
    public static ScriptManager scriptCollection;
    /** The actual script reader */
    public static ScriptReader scriptReader;
    /** Manages all the scripting threads */
    public static ThreadManager threadManager;
    
    public static Entity player;
    
    double time;
    
    public DialogBox dialog;
    
    int guiTestCounter;
    
    /**
     * Construct a new game
     */
    public VoyageQuest() {
       super("Voyage Quest Pre-Alpha");
    }

    /**
     * Initialize the game container and start the scripting engine
     * @param gc the new game container
     * @throws SlickException something went wrong with the Slick engine
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
        // Get rid of the default FPS count so we can use our own font
        gc.setShowFPS(false);
        
        // Set the minimum and maximum update intervals please
        gc.setMinimumLogicUpdateInterval(20);
        gc.setMaximumLogicUpdateInterval(20);
        
        //Load all the scripts
        loadScripts();
        
        //Create the current Map
        Global.currentMap = new Map("res/MAPTEST.tmx");
        
        //Create and add the player to the Map
        player = new Entity(new DoubleRect(1400, 4300, 64, 128));
        player.isPlayer = true;
        Global.currentMap.entities.add(player);
        Global.currentMap.collisions.addEntity(player);
        
        //As a terrible temporary solution...
        Global.character = new Image("res/CHARACTER.png");
                
        //Now create the Camera.
        Global.camera = new Camera();
        
        Color start = new Color(166, 250, 252, 100); // Color: #A6FAFC with alpha 75%
        Color end = new Color(205, 255, 145, 100); // Color #CDFF91 with alpha 75%
        
        String lorem = "This dialog box will be removed in 60 seconds. Tue May 21 00:46:27 EDT 2013 INFO:Slick Build #264 \n" +
"Tue May 21 00:46:27 EDT 2013 INFO:LWJGL Version: 2.9.0 \n" +
"Tue May 21 00:46:27 EDT 2013 INFO:OriginalDisplayMode: 1920 x 1080 x 32 @60Hz \n" +
"Tue May 21 00:46:27 EDT 2013 INFO:TargetDisplayMode: 1024 x 768 x 0 @0Hz \n" +
"Tue May 21 00:46:28 EDT 2013 INFO:Starting display 1024x768 \n" +
"Tue May 21 00:46:28 EDT 2013 INFO:Use Java PNG Loader = true \n" +
"WARNING: Found unknown Windows version: Windows 7 :) :) :) If you are seeing this, then the test is successful. This dialog box is done and will be removed when the timer is up.";
        dialog = new DialogBox(250, 550, 600, 150, lorem, start, end);
        dialog.start();
    }


     /**
     * Loads all scripting relating things
     */
    private void loadScripts() throws SlickException {
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
        
        //scriptReader.setEntityHandle(entities);
        
        //Create a thread which governs this entity with Script 
        Thread testThread = new Thread(1);
        
        //Set the main thread of the entity to this thread.
        //testEntity.setMainThread(entityThread);
        
        //Set the details of the thread
        testThread.setLineNumber(0);
        testThread.setName("main");
        testThread.setRunningState(false);
        //entityThread.setScriptable(testEntity);
        
        //Add this thread to the collection of threads
        threadManager.addThread(testThread);
    }
    
    /**
     * Update the screen
     * @param gc the game container
     * @param delta time interval
     * @throws SlickException something went horribly wrong with Slick
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        for (int i = 0; i < Global.currentMap.entities.size(); i++)
        {
            Entity e = Global.currentMap.entities.get(i);
            if (e != null)
            {
                e.act(delta);
            }
        }
        
        threadManager.act(delta);
        
        
        Input input = gc.getInput();
        double step = 0.25*delta;
            
        /* tilt and move to the left */
        if (input.isKeyDown(Input.KEY_LEFT)) {
            player.attemptMove(-step, 0);
        }

        if (input.isKeyDown(Input.KEY_RIGHT)) {
            player.attemptMove(step, 0);
        }

        if (input.isKeyDown(Input.KEY_UP)) {
            player.attemptMove(0, -step);
        }
        
        if (input.isKeyDown(Input.KEY_DOWN)) {
            player.attemptMove(0, step);
        }

        if(input.isKeyDown(Input.KEY_ENTER))
        {
            
        }
           
        GuiManager.update(gc, delta);
        
        if (guiTestCounter > 60000)
            GuiManager.close(dialog.getGui());
        else
            guiTestCounter += delta;
    }

    /**
     * Render the game window
     * @param gc the game container
     * @param g the graphics engine
     * @throws SlickException something went horribly wrong with Slick
     */
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        //If there isn't a full screen GUI... draw what the Camera sees
        Global.camera.display(g);
        
        try {
            GuiManager.draw();
            GuiManager.display();
        } catch (VoyageGuiException ex) {}
        Util.FONT.drawString(10, 10, "FPS: " + gc.getFPS());
        
        double entityX = player.r.x;
        double entityY = player.r.y;
        
        
        Util.FONT.drawString(10, 40, "Coordinates of player: (" + entityX + ", " + entityY + ")");
    }

    /**
     * The main method<br/>
     * Create the game window and start the game!
     * @param args command line arguments
     * @throws SlickException something went horribly wrong with Slick
     */
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new VoyageQuest());
        app.setDisplayMode(X_RESOLUTION, Y_RESOLUTION, FULLSCREEN);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(60);
        app.start();
        
    }
}