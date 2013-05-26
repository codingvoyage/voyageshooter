package voyagequest;

import gui.GuiManager;
import map.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/**
 * Listener for keyboard input and mouse interactions
 * @author Brian Yang
 */
public abstract class EventListener {
    
    public static GameContainer gc;
    public static final double STEP_SIZE = 0.25;
    
    /**
     * Initializes the event listener
     * @param gc game container of the game
     */
    public static void initGc(GameContainer gc) {
        EventListener.gc = gc;
    }
    
    /**
     * Controls an entity, typically the player by keyboard
     * @param player the entity to control
     * @param delta delta time
     */
    public static void keyboardControl(Entity player, int delta) {
        Input input = gc.getInput();
        
        double step = STEP_SIZE*delta;
        step = player.velocityX;
            
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
    }
    
    /**
     * Called when the mouse is moved
     */
    public static void mouseMoved(int oldx, int oldy, int newx, int newy) {
        Util.p("Moved");
    }

    /**
     * Called when the mouse is dragged
     */
    public static void mouseDragged(int oldx, int oldy, int newx, int newy) {
        GuiManager.mouseDragged(oldx, oldy, newx, newy);
    }

    /**
     * Called when the mouse is clicked (but not dragged)
     */
    public static void mouseClicked(int button, int x, int y, int clickCount) {
        Util.p("Clicked");
        
        //Later this will be configured so that this is ONLY EXECUTED
        //when the GUI isn't hit, but for now, let's pretend ...
        
    }
}
