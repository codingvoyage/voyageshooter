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
        playerVelocity = 0.15;
    }
}
