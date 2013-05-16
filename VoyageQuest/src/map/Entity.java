package map;

import java.util.LinkedList;
import voyagequest.VoyageQuest;
import voyagequest.DoubleRect;

/**
 *
 * @author user
 */
public class Entity {
    
    DoubleRect r;
    public double vx;
    public double vy;
    
    boolean hasDrawn;
    
    public Entity(DoubleRect r)
    {
        this.r = r;
    }
    
    public void act(double deltaT)
    {
        double candidateX = r.x + (vx * deltaT);
        double candidateY = r.y + (vy * deltaT);
        
        DoubleRect candidate = new DoubleRect(candidateX, candidateY, r.width, r.height);
        
        LinkedList<Entity> collisionCandidates = VoyageQuest.partitionTree.rectQuery(candidate);
        
            
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
            vx *= -1;
            vy *= -1;
        }
        else
        {
            VoyageQuest.partitionTree.removeEntity(this);
            r.x = candidateX;
            r.y = candidateY;
            VoyageQuest.partitionTree.addEntity(this);
        }
        
        
    }
    
    
    
}
