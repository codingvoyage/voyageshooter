package voyagequest;

import gui.Gui;
import gui.VoyageGuiException;
import gui.GuiManager;
import gui.types.Dialog;
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
        
        // Give the event listener the game container to use
        EventListener.initGc(gc);
        
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
        
        String lorem = "Welcome to VoyageQuest! I am your commander, Bakesale. I will now guide you through this test "
                + "of my dialog box system. When this dialog box is finished, press E to create a new one. "
                + "This box should automatically disappear after you press Z for the last time when it's done printing. "
                + "This has been a successful test of my dialog box system. Remember, press E gently or it will spawn multiple ones stacked on top of each other for now. ! "
                + "Thank you and I hope you enjoy your adventure, which beings NOW!";
        dialog = new DialogBox(lorem);
        dialog.start();
        
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
        
        EventListener.keyboardControl(player, delta);
        
        if(input.isKeyDown(Input.KEY_E))
        {
            int rand = (int)(Math.random() * 2);
            String lorem = "Hi I'm Panther. I'm a cool tester entity that Edmund is using to test his new fancy map "
                    + "system with cool collision boxes. But check it out, I'm speaking! I'm actually speaking! "
                    + "This is filler space so the dialog box overflows and requires you to press Z to continue "
                    + "reading. Woohoo it works! Next step are the windows which is still in progress.";
            String lorem2 = "Hi I'm Edmund. I'm a cool tester entity that Bakesale is using to test his fancy dialog "
                    + "box printing with automatic line breaks and section breaks. I'm speaking! I'm actually speaking! "
                    + "This is filler space so the dialog box overflows and requires you to press Z to continue "
                    + "reading. Woohoo it works! Next step are the windows which is still in progress.";
            if (rand < 1)
                player.speak(lorem);
            else
                player.speak(lorem2);
        } 
           
        GuiManager.update(gc, delta);
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
    
    /**
         * @see org.newdawn.slick.InputListener#mouseMoved(int, int, int, int)
         */
        public void mouseMoved(int oldx, int oldy, int newx, int newy) {
            //EventListener.mouseMoved(oldx, oldy, newx, newy);
        }

        /**
         * @see org.newdawn.slick.InputListener#mouseDragged(int, int, int, int)
         */
        public void mouseDragged(int oldx, int oldy, int newx, int newy) {
            EventListener.mouseDragged(oldx, oldy, newx, newy);
        }
        
        /**
         * @see org.newdawn.slick.InputListener#mouseClicked(int, int, int, int)
         */
        public void mouseClicked(int button, int x, int y, int clickCount) {
           //EventListener.mouseClicked(button, x, y, clickCount);
        }
}