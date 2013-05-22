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
    
    private double colldimx, colldimy;
    boolean hasDrawn;
    public boolean isPlayer = false;
    double accumulatedDeltaT = 0.0d;
    int currentFrame = 0;

    public Entity(DoubleRect r) throws SlickException
    {
        this.r = r;
        colldimx = 5.0d;
        colldimy = 80.0d;
    }
    
    public DoubleRect getCollRect()
    {
        return new DoubleRect(r.x + colldimx, r.y + colldimy, 50.0d, 50.0d);
    }
    
    public void draw(Graphics g, float xOffset, float yOffset)
    {
        Global.character.draw(xOffset, yOffset);
        g.drawRect((float)(VoyageQuest.X_RESOLUTION/2 - 32 + colldimx),
                   (float)(VoyageQuest.Y_RESOLUTION/2 - 64 + colldimy),
                   50.0f,
                   50.0f);
    }
    
    public void attemptMove(double xMove, double yMove)
    {
        //Where would we end up at
        double candidateX = r.x + colldimx + xMove;
        double candidateY = r.y + colldimy + yMove;
        DoubleRect collCandidate = new DoubleRect(candidateX, candidateY, 50.0d, 50.0d);

        LinkedList<Rectangular> collisionCandidates = Global.currentMap.collisions.rectQuery(collCandidate);
        //System.out.println(collisionCandidates.size());

        //Now we see if collides
        boolean collides = false;
        for (Rectangular e : collisionCandidates)
        {
            if (collCandidate.intersects(e.getRect()) && !e.equals(this))
            {
                collides = true;
                //System.out.println("collides with something at " + e.getRect().x + " " + e.getRect().y);
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
