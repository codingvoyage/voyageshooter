package map;

import org.newdawn.slick.geom.Rectangle;
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
        float candidateX = r.getX() + (float)(vx * deltaT/1000.0f);
        float candidateY = r.getY() + (float)(vy * deltaT/1000.0f);

         Rectangle 
        newRCandidate = new Rectangle(candidateX, candidateY, r.getWidth(), r.getHeight());;
        
        //Now we see if collides
        boolean collides = false;
        
        if (!VoyageQuest.screen.intersects(newRCandidate))
            collides = true;

        LinkedList<Entity> collisionCandidates =
                VoyageQuest.partitionTree.rectQuery(newRCandidate);
        
//        for (Entity asdf : collisionCandidates)
//        {
//            if (newRCandidate.intersects(asdf.r))
//            {
//                collides = true;
//                break;
//            }
//        }
        
        if (collides)
        {
            vx *= -1;
            vy *= -1;
        }
        else
        {
            VoyageQuest.partitionTree.removeEntity(this);
            r.setX(candidateX);
            r.setY(candidateY);
            VoyageQuest.partitionTree.addEntity(this);
        }
    }
    
    
    
}
