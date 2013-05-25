package voyagequest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.XMLPackedSheet;

import voyagequest.special.LoadAnimations;

/**
 * Global resources, like animations, sprites, etc...
 * @author Brian Yang
 */
public class Res {
    
    /** Sprite sheet for Sebastian */
    public static XMLPackedSheet sebastian; 
    static {
        try {
            sebastian = new XMLPackedSheet("src/res/sebastian.png", "src/res/sebastian.xml");
        } catch (SlickException e) {}
    }
    
    /** Sebastian animation, data mapped by JSON */
    public static LinkedList<LoadAnimations> animationData = new LinkedList<>();
    
    /** The actual animations, mapped by ID strings */
    public static HashMap<String, Animation> animations = new HashMap<>();
    
    /**
     * Initialize all the remaining resources. 
     * Must be called <em>after</em> data is loaded from JSON
     */
    public static void init() {
        
        ListIterator<LoadAnimations> animationIterator = animationData.listIterator();
        while (animationIterator.hasNext()) {
            LoadAnimations next = animationIterator.next();
            
            Image[] frames = new Image[next.getImages().size()];
            int i = 0;
            ListIterator<String> frameIterator = next.getImages().listIterator();
            while (frameIterator.hasNext()) {
                frames[i] = sebastian.getSprite(frameIterator.next());
                i++;
            }
                
            animations.put(next.getName(), new Animation(frames, next.getDuration()));
        }
    }
    
}