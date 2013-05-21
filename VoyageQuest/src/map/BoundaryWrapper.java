package map;

import org.newdawn.slick.tiled.GroupObject;

/**
 *
 * @author Edmund
 */
public class BoundaryWrapper extends GroupObjectWrapper {
    GroupObjectWrapper secondary;
    
    public BoundaryWrapper(GroupObject boundaryGroupObject, GroupObjectWrapper secondaryGroupObject)
    {
        super(boundaryGroupObject);
        secondary = secondaryGroupObject;
    }
    
    
    public GroupObjectWrapper getSecondaryGroupObject()
    {
        return secondary;
    }
    
}
