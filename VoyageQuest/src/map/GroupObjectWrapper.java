package map;

import org.newdawn.slick.tiled.*;
import voyagequest.DoubleRect;

/**
 *
 * @author Edmund
 */
public class GroupObjectWrapper implements Rectangular {
    private GroupObject g;
    
    public GroupObjectWrapper(GroupObject g)
    {
        this.g = g;
    }
    
    public GroupObject getObject()
    {
        return g;
    }
    
    public DoubleRect getRect()
    {
        return new DoubleRect(g.x, g.y, g.width, g.height);
    }
}
