package voyagequest;

import gui.GuiManager;
import gui.VoyageGuiException;
import gui.special.DialogBox;
import java.io.InputStream;

import map.Camera;
import map.Entity;
import map.Map;
import map.Player;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import scripting.ScriptManager;
import scripting.ScriptReader;
import scripting.Thread;
import scripting.ThreadManager;

import org.lwjgl.opengl.GL11;
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
    /** Are we debugging? */
    public static final boolean DEBUG_MODE = false;
    
    /** All the scripts to be read */
    public static ScriptManager scriptCollection;
    /** The actual script reader */
    public static ScriptReader scriptReader;
    /** Manages all the scripting threads */
    public static ThreadManager threadManager;
    
    /** menu */
    public static final int MENU = 0;
    /** regular RPG */
    public static final int RPG = 1;
    /** navy shooter */
    public static final int SHOOTER = 2;
    
    /** status */
    public static int state;
    
    public static Entity player;
    double time;
    public DialogBox dialog;
    
    //testing
    public static boolean haschangedmaps = false;
    
    
    
   /** The alpha map being applied */
   private Image alphaMap;
   
   
    
    
    /**
     * Construct a new game
     */
    public VoyageQuest() {
       super("Voyage Quest Alpha");
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
        
        // Load animation data
        JsonReader<Res> reader = new JsonReader<>(Res.class, "res/Animations.json");
        reader.readJson();
        
        // Initialize the rest of the resource manager
        Res.init();
        
        // Load all the scripts
        loadScripts();
        
        // IDK what this is for.
        EventListener.initGc(gc);
        
        //Create the player
        //The player needs to have a thread...
        Thread playerThread = new Thread(1);
        playerThread.setLineNumber(0);
        playerThread.setName("SebastianThread");
        threadManager.addThread(playerThread);
        player = new Player(new DoubleRect(1400, 4300, 64, 128));
                
        player.setMainScriptID(1);
        player.setMainThread(threadManager.getThreadAtName("SebastianThread"));

        player.forward = Res.animations.get("Sebastian Forward");
        player.backward = Res.animations.get("Sebastian Backwards");
        player.left = Res.animations.get("Sebastian Left");
        player.right = Res.animations.get("Sebastian Right");
        
        player.name = "Sebastian";

        player.profile = Res.animations.get("Sebastian Profile");
        if (player.profile == null)
            System.out.println("Still null");


        player.setAnimation(0);
      
        //Now create the Camera.
        Global.camera = new Camera();
        
        //Now that we're done with the player and camera, we can load the map itself...
        threadManager.clear();
        Thread loadingThread = new Thread(42);
        loadingThread.setName("LOADINITIALSCRIPT");
        loadingThread.setLineNumber(0);
        threadManager.addThread(loadingThread);
        threadManager.act(0.0);
       
        
//        String lorem = "Welcome to VoyageQuest! I am your commander, Bakesale. I will now guide you through this test "
//                + "of my dialog box system. When this dialog box is finished, press E to create a new one. "
//                + "This box should automatically disappear after you press Z for the last time when it's done printing. "
//                + "This has been a successful test of my dialog box system. Remember, press E gently or it will spawn multiple ones stacked on top of each other for now. ! "
//                + "Thank you and I hope you enjoy your adventure, which beings NOW!";
//        dialog = new DialogBox(lorem);
        //dialog.start();
        
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
        loadingThread.setName("LOADINGTHREAD");
        loadingThread.setLineNumber(0);
        threadManager.addThread(loadingThread);
        threadManager.act(0.0);
        
        
        InputStream is = getClass().getClassLoader().getResourceAsStream("res/alphamini.png");
        alphaMap = new Image(is, "res/alphamini.png", false, Image.FILTER_NEAREST);
      
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
                e.act(gc, delta);
            }
        }
        
        Input input = gc.getInput();
        
        EventListener.keyboardControl(player, delta);
        
        threadManager.act(delta);
        
//        if(input.isKeyDown(Input.KEY_E))
//        {
//            
//            String lorem = "Hi I'm Panther. I'm a cool tester entity that Edmund is using to test his new fancy map "
//                    + "system with cool collision boxes. But check it out, I'm speaking! I'm actually speaking! "
//                    + "This is filler space so the dialog box overflows and requires you to press Z to continue. "
//                    + "This has been a successful test of my dialog boxes. Good day to you sir.  ";
//            player.speak(lorem);
//        } 
           
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
        
        lights(g, 20);
        
        try {
            GuiManager.draw();
            GuiManager.display();
        } catch (VoyageGuiException ex) {}
      
        Util.FONT.drawString(10, 10, "FPS: " + gc.getFPS());
        Util.FONT.drawString(10, 40,
                "Coordinates of player: (" + player.r.x + ", " + player.r.y + ")");
        
    }

    
    private void lights(Graphics g, int size) {

      //Scaling stuff down */
      float invSizeX = 1f / 20;
      float invSizeY = 1f / 15;
      g.scale(20, 15);

      //setting alpha channel ready so lights add up instead of clipping */
      g.clearAlphaMap();
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
      
      alphaMap.drawCentered(X_RESOLUTION/2 * invSizeX, Y_RESOLUTION/2 * invSizeY);
      
      //Scaling back stuff so we don't have to use big alpha map image */
      g.scale(invSizeX, invSizeY);

      //setting alpha channel for clearing everything but light map just added
      GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_DST_ALPHA);

      //paint everything else with black
      g.fillRect(0, 0, X_RESOLUTION, Y_RESOLUTION);
      
      //setting drawing mode back to normal
      g.setDrawMode(Graphics.MODE_NORMAL);

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
    @Override
   public void mouseMoved(int oldx, int oldy, int newx, int newy) {
       //EventListener.mouseMoved(oldx, oldy, newx, newy);
   }

   /**
    * @see org.newdawn.slick.InputListener#mouseDragged(int, int, int, int)
    */
    @Override
   public void mouseDragged(int oldx, int oldy, int newx, int newy) {
       EventListener.mouseDragged(oldx, oldy, newx, newy);
   }

   /**
    * @see org.newdawn.slick.InputListener#mouseClicked(int, int, int, int)
    */
    @Override
   public void mouseClicked(int button, int x, int y, int clickCount) {
      EventListener.mouseClicked(button, x, y, clickCount);
   }
}
