package voyagequest;

import gui.VoyageGuiException;
import map.*;
import org.newdawn.slick.*;
import java.util.ArrayList;
import voyagequest.DoubleRect;
import gui.special.*;

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
    
    public static Entity player;
    
    int index = 0;
    int whichDraw = -1;
    int deltaCounter = 0;
    int removeCounter = 0;
    
    double time;
    
    public DialogBox dialog;
    
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
        
        //Create the current Map
        Global.currentMap = new Map("res/MAPTEST.tmx");
        
        //Create and add the player to the Map
        player = new Entity(new DoubleRect(0, 0, 64, 128));
        player.isPlayer = true;
        Global.currentMap.entities.add(player);
        Global.currentMap.collisions.addEntity(player);
        
        //As a terrible temporary solution...
        Global.character = new Image("res/CHARACTER.png");
                
        //Now create the Camera.
        Global.camera = new Camera();
        
        Color start = new Color(166, 250, 252, 100); // Color: #A6FAFC with alpha 75%
        Color end = new Color(205, 255, 145, 100); // Color #CDFF91 with alpha 75%
        
        String lorem = "The dialog box system is currently under construction. This is merely a test of automatic line breaks. Do not modify the gui.* packages.";
        dialog = new DialogBox(250, 550, 600, 150, lorem, start, end);
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
        
        Input input = gc.getInput();
        double step = 0.5*delta;
            
        /* tilt and move to the left */
        if (input.isKeyDown(Input.KEY_LEFT)) {
            player.attemptMove(-step, 0);
        }

        if(input.isKeyDown(Input.KEY_RIGHT)) {
            player.attemptMove(step, 0);
        }

        if(input.isKeyDown(Input.KEY_UP)) {
            player.attemptMove(0, -step);
        }
        
        if(input.isKeyDown(Input.KEY_DOWN)) {
            player.attemptMove(0, step);
        }

        if(input.isKeyDown(Input.KEY_ENTER))
        {
            
        }
           
        dialog.next(delta);
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
            dialog.start();
            dialog.printNext();
        } catch (VoyageGuiException ex) {}
        Util.FONT.drawString(10, 10, "FPS: " + gc.getFPS());
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
