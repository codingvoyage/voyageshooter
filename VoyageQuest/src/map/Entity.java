package map;

import java.util.LinkedList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import voyagequest.VoyageQuest;
import voyagequest.DoubleRect;
import voyagequest.Global;

/**
 *
 * @author user
 */
public class Entity {
    Image character;
    
    DoubleRect r;
    public double vx;
    public double vy;
    boolean hasDrawn;
    
    public Entity(DoubleRect r) throws SlickException
    {
        this.r = r;
        character = new Image("res/CHARACTER.png");

    }
    
    public void draw(Graphics g, float xOffset, float yOffset)
    {
        character.draw((float)(VoyageQuest.X_RESOLUTION/2 - 32),
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
                Global.currentMap.collisions.removeEntity(this);
                r.x = (int)tempX;
                r.y = (int)tempY;
                Global.currentMap.collisions.addEntity(this);
            }

        }
    }
    
    public void act(double deltaT)
    {
        double candidateX = r.x + (vx * deltaT);
        double candidateY = r.y + (vy * deltaT);
        
        Global.currentMap.collisions.removeEntity(this);
        r.x = candidateX;
        r.y = candidateY;
        Global.currentMap.collisions.addEntity(this);
        
//        
//        DoubleRect candidate = new DoubleRect(candidateX, candidateY, r.width, r.height);
//        
//        LinkedList<Entity> collisionCandidates = VoyageQuest.partitionTree.rectQuery(candidate);
//        
//            
//        //Now we see if collides
//        boolean collides = false;
//        for (Entity e : collisionCandidates)
//        {
//            if (candidate.intersects(e.r) && !e.equals(this))
//            {
//                collides = true;
//                break;
//            }
//        }
//        
//        if (!VoyageQuest.SCREEN_RECT.contains(candidate))
//            collides = true;
//        
//        if (collides == true)
//        {
//            vx *= -1;
//            vy *= -1;
//        }
//        else
//        {
////            VoyageQuest.partitionTree.removeEntity(this);
////            r.x = candidateX;
////            r.y = candidateY;
////            VoyageQuest.partitionTree.addEntity(this);
//        }
        
        
    }
    
    
    
}
