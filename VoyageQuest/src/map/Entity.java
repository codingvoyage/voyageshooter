package map;

import java.awt.Rectangle;
import java.util.LinkedList;
import voyagequest.VoyageQuest;

/**
 *
 * @author user
 */
public class Entity {
    
    Rectangle r;
    public float vx;
    public float vy;
    
    boolean hasDrawn;
    
    public Entity(Rectangle r)
    {
        this.r = r;
    }
    
    public void act(double deltaT)
    {
        int candidateX = (int)(vx * deltaT);
        int candidateY = (int)(vy * deltaT);
        
        Rectangle candidate = new Rectangle(candidateX, candidateY, r.width, r.height);
        
        LinkedList<Entity> collisionCandidates = VoyageQuest.partitionTree.rectQuery(candidate);
        
        //Now we see if collides
        boolean collides = false;
        for (Entity e : collisionCandidates)
        {
            if (e.r.intersects(candidate))
            {
                collides = true;
                break;
            }
        }
    }
    
    
    
}
