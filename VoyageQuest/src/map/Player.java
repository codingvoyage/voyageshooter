package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import voyagequest.DoubleRect;
import static voyagequest.VoyageQuest.player;

/**
 *
 * @author Edmund
 */
public class Player extends Entity {
    
    private double playerVelocity;
    
    public Player(DoubleRect boundaryRect) throws SlickException
    {
        super(boundaryRect);
        playerVelocity = 0.25;
    }
    
    public void act(GameContainer gc, int delta)
    {
        Input input = gc.getInput();
        double step = playerVelocity*delta;
            
        /* tilt and move to the left */
        if (input.isKeyDown(Input.KEY_LEFT)) {
            this.attemptMove(-step, 0);
        }

        if (input.isKeyDown(Input.KEY_RIGHT)) {
            this.attemptMove(step, 0);
        }

        if (input.isKeyDown(Input.KEY_UP)) {
            this.attemptMove(0, -step);
        }
        
        if (input.isKeyDown(Input.KEY_DOWN)) {
            this.attemptMove(0, step);
        }
           
        
    }
    
    
}
