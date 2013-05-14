package map;

import org.newdawn.slick.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import voyagequest.VoyageQuest;

/**
 *
 * @author Edmund
 */
public class Camera {
    
    public Camera()
    {
        
    }
    
    public Rectangle getViewRect()
    {
        
        return new Rectangle(0, 0, 1200, 800);
    }
    
    public void legitDraw(Graphics g)
    {
        drawPartitionBoxes(g);
        
        //get the rectangle representing the Camera's range of vision
        Rectangle vRect = getViewRect();
        
        //get the entities which we need to draw:
        ArrayList<Entity> entList = VoyageQuest.partitionTree.rectQuery(vRect);
        
        //now draw them all
        for (Entity e : entList)
        {
            g.drawRect(
                    e.r.x,
                    e.r.y,
                    e.r.width,
                    e.r.height);
        }
                
    }
    
    
    public void draw(Graphics g)
    {
        drawPartitionBoxes(g);
        
        g.setColor(org.newdawn.slick.Color.blue);
        for (int i = 0; i < VoyageQuest.entities.size(); i++)
        {
            Entity e = VoyageQuest.entities.get(i);
            g.drawRect(
                    e.r.x,
                    e.r.y,
                    e.r.width,
                    e.r.height);
        }
        
    }
    
    public void drawPartitionBoxes(Graphics g)
    {
        g.setColor(org.newdawn.slick.Color.yellow);
        ArrayList<TreeNode> partitionBoxes = VoyageQuest.partitionTree.getPartitions();
        for (TreeNode t : partitionBoxes)
        {
            Rectangle r = t.boundary;
            g.drawRect(
                    r.x,
                    r.y,
                    r.width,
                    r.height);
            
        }
        
    }
    
}