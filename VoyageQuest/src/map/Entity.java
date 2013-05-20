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
public class Entity {
    
    double accumulatedDeltaT = 0.0d;
    int currentFrame = 0;
    
    
    /** The entity's boundary rectangle. For this, r.x and r.y are
      * the actual coordinates of the Entity in Map space. Width and
      * height are the width and height of the entity, which are likely
      * to be 64, 128 respectively.
      */
    public DoubleRect r;
    
    double colldimx, colldimy;
    
    public double vx;
    public double vy;
    boolean hasDrawn;
    public boolean isPlayer = false;
    
    public Entity(DoubleRect r) throws SlickException
    {
        this.r = r;
        colldimx = 5.0d; colldimy = 80.0d;
    }
    
    public void draw(Graphics g, float xOffset, float yOffset)
    {
        Global.character.draw((float)(VoyageQuest.X_RESOLUTION/2 - 32),
                        (float)(VoyageQuest.Y_RESOLUTION/2 - 64),
                        (float)r.width,
                        (float)r.height);
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

        double mapWidth = Global.currentMap.MAP_WIDTH;
        double mapHeight = Global.currentMap.MAP_HEIGHT;
        
    
        DoubleRect collCandidate = new DoubleRect(candidateX, candidateY, 50.0d, 50.0d);

        LinkedList<Entity> collisionCandidates = Global.currentMap.collisions.rectQuery(collCandidate);
        System.out.println(collisionCandidates.size());

        //Now we see if collides
        boolean collides = false;
        for (Entity e : collisionCandidates)
        {
            if (collCandidate.intersects(e.r) && !e.equals(this))
            {
                collides = true;
                System.out.println("collides with something at " + e.r.x + " " + e.r.y);
                break;
            }
        }


        if (collides == true)
        {
            System.out.println("fuuuu");
        }
        else
        {
            Global.currentMap.collisions.removeEntity(this);
            r.x = r.x + xMove;
            r.y = r.y + yMove;
            Global.currentMap.collisions.addEntity(this);
        }
        
    }
    
    public void act(double deltaT)
    {
        
        
    }
    
    
    
}
