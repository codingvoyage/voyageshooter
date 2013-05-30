package voyagequest.special;

import java.util.LinkedList;

/**
 * A special class used simply so Json can load animation data 
 * This data is then processed and passed on to Slick2D's animation class
 * @author Brian Yang
 */
public class LoadAnimations {
    
    private String name;
    private String packedSheetID;
    private String id;
    private LinkedList<String> images;
    private int duration;
    
    public String getName() {
        return name;
    }
    
    public String getPackedSheetID() {
        return packedSheetID;
    }
    
    public String getId() {
        return id;
    }
    
    public LinkedList<String> getImages() {
        return images;
    }
    
    public int getDuration() {
        return duration;
    }
    
}
