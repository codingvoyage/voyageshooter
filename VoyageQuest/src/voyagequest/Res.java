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
    public static XMLPackedSheet njeri; 
    
    static {
        try {
            sebastian = new XMLPackedSheet("src/res/sebastian.png", "src/res/sebastian.xml");
            njeri = new XMLPackedSheet("src/res/njeri.png", "src/res/njeri.xml");
        } catch (SlickException e) {}
    }
    
    /** Sebastian animation, data mapped by JSON */
    public static LinkedList<LoadAnimations> animationData = new LinkedList<>();
    
    /** Every image */
    //public static HashMap<String, Image> images = new HashMap<>();
    
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
            
            //For lack of better implementation, I will hardcode this now.
            //Here's the problem: before, it was sebastian.getSprite(frameIterator.next());
            //Basically, since you didn't write something that transferred the images from
            //the XMLPackedSheets into a huge HashMap of Image objects, you went directly from
            //XMLPackedSheet --> Animation with this method. Unfortunately, we end up with two
            //issues: 
            //     1. How do we load any images that don't belong with Animations?
            //     2. How do we tell between which XMLPackedSheet to choose from? I decided to give
            //     a new JSON field called packedSheetID which solves the problem for now, but please
            //     later help me write something that creates a huge Hashmap<String, Image> please.
            
            XMLPackedSheet currentPackedSheet = null;
            System.out.println(next.getPackedSheetID());
            if (next.getPackedSheetID().equals("njeri")) currentPackedSheet = njeri;
            if (next.getPackedSheetID().equals("sebastian")) currentPackedSheet = sebastian;
            
            Image[] frames = new Image[next.getImages().size()];
            int i = 0;
            ListIterator<String> frameIterator = next.getImages().listIterator();
            while (frameIterator.hasNext()) {
                frames[i] = currentPackedSheet.getSprite(frameIterator.next());
                i++;
            }
                
            animations.put(next.getName(), new Animation(frames, next.getDuration()));
        }
        
        
        
    }
    
}