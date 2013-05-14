package voyagequest;

import gui.types.Dialog;
import org.newdawn.slick.*;
import java.util.ArrayList;
import java.awt.Rectangle;

// import scripting.*;
import gui.*;
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
    
    public static Camera camera;
    public static ArrayList<Entity> entities;
    public static QuadTree partitionTree;
    
    int whichDraw = -1;
    int deltaCounter = 0;
    int removeCounter = 0;
    
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
        //Set the minimum and maximum update intervals please
        gc.setMinimumLogicUpdateInterval(20);
        gc.setMaximumLogicUpdateInterval(20);
        
        camera = new Camera();
        partitionTree = new QuadTree(5, 20,
                new Rectangle(0, 0, X_RESOLUTION, 
                Y_RESOLUTION));
        entities = new ArrayList<Entity>();
        
        for (int i = 0; i < 200; i++)
        {
            int randX = Util.rand(0, X_RESOLUTION - 10);
            int randY = Util.rand(0, Y_RESOLUTION - 10);
            int randWidth = Util.rand(2, 4);
            int randHeight = Util.rand(2, 4);
            
            Entity e = new Entity(new Rectangle(randX, randY, randWidth, randHeight));
            entities.add(e);
            partitionTree.addEntity(e);
        }
        
        
        Color start = new Color(166, 250, 252, 75); // Color: #A6FAFC with alpha 75%
        Color end = new Color(205, 255, 145, 75); // Color #CDFF91 with alpha 75%
        
        String lorem = "Hello Team Coding Voyage. This is a test of dialog box parsin and automatic splitting into nice lines. Let's start the test now shall we?";
        dialog = new DialogBox(250, 250, 600, 150, lorem, start, end);
    }


    /**
     * Update the screen
     * @param gc the game container
     * @param delta time interval
     * @throws SlickException something went horribly wrong with Slick
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        
        deltaCounter += delta;
        removeCounter += delta;
        
        if (deltaCounter > 1000)
        {
            deltaCounter = 0;
            whichDraw *= -1;
            if (whichDraw == 1)
            {
                Util.sop("We will now traverse the tree to draw");
            }
            else 
            {
                Util.sop("We will now draw the entity list");
            }
            
            Util.sop("Total number of entities in Tree: " + partitionTree.getSize());
            Util.sop("Total number of partitions in Tree: " + partitionTree.getPartitionCount());
            
            
        }
        
        if (removeCounter > 19)
        {
            removeCounter = 0;
            //now let's remove one.
            if (entities.size() > 0) {
                int index = Util.rand(0, entities.size() - 1);
                Entity toBeRemoved = entities.remove(index);
                partitionTree.removeEntity(toBeRemoved);
            }
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
        camera.draw(g);
        //gui.draw(g);
          
    }

    /**
     * The main method<br/>
     * Create the game window and start the game!
     * @param args command line arguments
     * @throws SlickException something went horribly wrong with Slick
     */
    public static void main(String[] args) throws SlickException {
        // Start new game window
        AppGameContainer app = new AppGameContainer(new VoyageQuest());
        
        app.setDisplayMode(X_RESOLUTION, Y_RESOLUTION, FULLSCREEN);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(60);

        app.start();
        
    }
}
