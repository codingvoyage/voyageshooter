package voyagequest;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.XMLPackedSheet;

/**
 * Global resources, like animations, sprites, etc...
 * @author Brian Yang
 */
public class Res {
    
    /** Sprite sheet for Entities */
    public static final XMLPackedSheet sprites; 
    static {
        XMLPackedSheet tempSprites = null; // required or compiler will complain that sprites isn't initialized
        try {
            tempSprites = new XMLPackedSheet("src/spaceinvaders/entity/sprites/sprites.png", "src/spaceinvaders/entity/sprites/sprites.xml");
        } catch (SlickException e) {
            System.out.println("Sprite sheet failure.");
        }
        sprites = tempSprites;
    }
    
}