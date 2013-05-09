package voyagequest;

import org.newdawn.slick.*;
// import scripting.*;
import gui.*;

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
    
    private DialogBox dialog;
    
    private boolean print = true;
    
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
        
        String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum in mauris nisl. Duis eget quam ut quam aliquet mollis nec eget nulla. Vivamus laoreet rhoncus est sed malesuada. Ut adipiscing interdum hendrerit. Suspendisse potenti. Suspendisse potenti. Quisque tincidunt nisi id lorem malesuada commodo. Sed et dignissim erat. Maecenas a tortor metus. In lacinia aliquam libero, ut fringilla sem aliquam et. Aenean sed lorem magna. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed accumsan justo convallis enim semper condimentum.";
        DialogBox dialog = new DialogBox(10, 10, 10, 10, lorem);
        try {
            dialog.print();
        } catch (InterruptedException ex) {}

        System.out.println();
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
