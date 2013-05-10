package voyagequest;

import gui.types.Dialog;
import org.newdawn.slick.*;
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
        
        
    }

    /**
     * Render the game window
     * @param gc the game container
     * @param g the graphics engine
     * @throws SlickException something went horribly wrong with Slick
     */
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        Globals.FONT.drawString(600, 100, "Check the console for now!", Color.yellow);
        dialog.start();
    }

    /**
     * The main method<br/>
     * Create the game window and start the game!
     * @param args command line arguments
     * @throws SlickException something went horribly wrong with Slick
     */
    public static void main(String[] args) throws SlickException, InterruptedException {
        
        // Start new game window
        AppGameContainer app = new AppGameContainer(new VoyageQuest());
        
        app.setDisplayMode(X_RESOLUTION, Y_RESOLUTION, FULLSCREEN);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(60);

        app.start();
        
    }
}
