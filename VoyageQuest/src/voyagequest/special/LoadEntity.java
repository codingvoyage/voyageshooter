package voyagequest.special;

import java.util.LinkedList;

/**
 * A special class used simply so Json can load animation data 
 * This data is then processed and passed on to Slick2D's animation class
 * @author Edmund Qiu
 */
public class LoadEntity {
    
    private String name;
    private int initialX;
    private int initialY;
    private int width;
    private int height;
    private int mainScriptID;
    private String mainThreadName;
    private LinkedList<String> animations;
    private boolean profLeft;
    private int startingAnimationDirection;
    private int onClickScript;
    private int onTouchScript;
    
    public String getName() {
        return name;
    }
    
    public int getInitialX() {
        return initialX;
    }
    
    public int getInitialY() {
        return initialY;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getMainScriptID() {
        return mainScriptID;
    }
    
    public String getMainThreadName() {
        return mainThreadName;
    }
    
    public LinkedList<String> getAnimations() {
        return animations;
    }
    
    public boolean getOrientation() {
        return profLeft;
    }
    
    public int getStartingAnimationDirection() {
        return startingAnimationDirection;
    }
    
    public int getOnClickScript() {
        return onClickScript;
    }
    
    public int getOnTouchScript() {
        return onTouchScript;
    }
    
}
