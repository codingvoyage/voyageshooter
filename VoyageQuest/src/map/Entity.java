package map;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Animation;

import voyagequest.VoyageQuest;
import voyagequest.DoubleRect;
import voyagequest.Global;

/**
 *
 * @author user
 */
public class Entity implements Rectangular {
    /** The entity's boundary rectangle. For this, r.x and r.y are
      * the actual coordinates of the Entity in Map space. Width and
      * height are the width and height of the entity, which are likely
      * to be 64, 128 respectively.
      */
    public DoubleRect r;
    
    /** 
     * The entity's collision rectangle RELATIVE to its boundary.
     */
    public DoubleRect collRect;
    
    public RenderSetting renderSetting;
    
    boolean hasDrawn;
    public boolean isPlayer = false;
    double accumulatedDeltaT = 0.0d;
    int currentFrame = 0;

    /**
     * Constructs an Entity with only its boundary Rectangle
     * 
     * @param r the boundary Rectangle
     * @throws SlickException 
     */
    public Entity(DoubleRect boundaryRect) throws SlickException
    {
        this.r = boundaryRect;
        this.collRect = new DoubleRect(5.0d, 70.0d, 50.0d, 50.0d);
    }
    
    
    public DoubleRect getCollRect()
    {
        return new DoubleRect(
                r.x + collRect.x,
                r.y + collRect.y,
                collRect.width,
                collRect.height);
    }
    
    public void draw(Graphics g, float xOffset, float yOffset)
    {
        Global.character.draw(xOffset, yOffset);
        
        //If debugging, then draw the boundary boxes and the collision boxes
        if (VoyageQuest.DEBUG_MODE == true)
        {
            g.drawRect(xOffset, yOffset, (float)r.width, (float)r.height);
            g.drawRect((float)(xOffset + collRect.x),
                       (float)(yOffset + collRect.y),
                       (float)collRect.width,
                       (float)collRect.height);
        }
        
    }
    
    public void attemptMove(double xMove, double yMove)
    {
        //Where would we end up at
        double candidateX = r.x + collRect.x + xMove;
        double candidateY = r.y + collRect.y + yMove;
        DoubleRect collCandidate = new DoubleRect(candidateX, candidateY, 50.0d, 50.0d);

        LinkedList<Rectangular> collisionCandidates = Global.currentMap.collisions.rectQuery(collCandidate);

        //Now we see if collides
        boolean collides = false;
        for (Rectangular e : collisionCandidates)
        {
            if (collCandidate.intersects(e.getRect()) && !e.equals(this))
            {
                collides = true;
                break;
            }
        }


        if (collides == true)
        {
            //System.out.println("fuuuu");
        }
        else
        {
            Global.currentMap.collisions.removeEntity(this);
            r.x = r.x + xMove;
            r.y = r.y + yMove;
            Global.currentMap.collisions.addEntity(this);
        }
        
    }
    
    /**
     * Remember, collision box is the box of collision, with its position in space
     * as well as its width and height.
     * Boundary box is the entity's x, y location in space, and its width and height.
     * 
     * @return the double rect of the boundaries of this Entity.
     */
    public DoubleRect getRect()
    {
        return r;
    }
    
    public void act(double deltaT)
    {
        
        
    }
    
    
    
}
