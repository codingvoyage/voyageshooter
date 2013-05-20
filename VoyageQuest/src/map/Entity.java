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
    DoubleRect r;
    
    /**
     * This describes the 
     */
    DoubleRect collisionRect;
    
    
    public double vx;
    public double vy;
    boolean hasDrawn;
    public boolean isPlayer = false;
    
    public Entity(DoubleRect r) throws SlickException
    {
        this.r = r;
        this.collisionRect = new DoubleRect(r.x + 5.0d,
                                            r.y + 70.0d,
                                            50.0d,
                                            50.0d);
    }
    
    public void draw(Graphics g, float xOffset, float yOffset)
    {
        Global.character.draw((float)(VoyageQuest.X_RESOLUTION/2 - 32),
                        (float)(VoyageQuest.Y_RESOLUTION/2 - 64),
                        (float)r.width,
                        (float)r.height);
    }
    
    public void attemptMove(double xMove, double yMove)
    {
        //Where would we end up at
        double tempX = r.getX() + xMove;
        double tempY = r.getY() + yMove;

        double mapWidth = Global.currentMap.MAP_WIDTH;
        double mapHeight = Global.currentMap.MAP_HEIGHT;
        
        //If we don't end up going less than 0, and if we don't
        //end up rendering stuff off the map...
        if (tempX >= 0 && tempY >= 0)
        {
            if ((tempX < mapWidth) && (tempY < mapHeight))
            {
                
        
                DoubleRect candidate = new DoubleRect(tempX, candidateY, r.width, r.height);

                LinkedList<Entity> collisionCandidates = Global.currentMap.collisions.rectQuery(candidate);
                System.out.println(collisionCandidates.size());

                //Now we see if collides
                boolean collides = false;
                for (Entity e : collisionCandidates)
                {
                    if (candidate.intersects(e.r) && !e.equals(this))
                    {
                        collides = true;
                        break;
                    }
                }

                if (!VoyageQuest.SCREEN_RECT.contains(candidate))
                    collides = true;

                if (collides == true)
                {
                }
                else
                {
                    Global.currentMap.collisions.removeEntity(this);
                    r.x = candidateX;
                    r.y = candidateY;
                    Global.currentMap.collisions.addEntity(this);
                }
        
            }

        }
    }
    
    public DoubleRect getCollRect()
    {
        collisionRect.x = r.x + 5.0d;
        collisionRect.y = r.y + 70.0d;
        return collisionRect;
    }
    
    public void act(double deltaT)
    {
        double candidateX = r.x + (vx * deltaT);
        double candidateY = r.y + (vy * deltaT);
    
        
        DoubleRect candidate = new DoubleRect(candidateX, candidateY, r.width, r.height);
        
        LinkedList<Entity> collisionCandidates = Global.currentMap.collisions.rectQuery(candidate);
        System.out.println(collisionCandidates.size());
            
        //Now we see if collides
        boolean collides = false;
        for (Entity e : collisionCandidates)
        {
            if (candidate.intersects(e.r) && !e.equals(this))
            {
                collides = true;
                break;
            }
        }
        
        if (!VoyageQuest.SCREEN_RECT.contains(candidate))
            collides = true;
        
        if (collides == true)
        {
        }
        else
        {
            Global.currentMap.collisions.removeEntity(this);
            r.x = candidateX;
            r.y = candidateY;
            Global.currentMap.collisions.addEntity(this);
        }
        
        
    }
    
    
    
}
