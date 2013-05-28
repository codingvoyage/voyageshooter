package voyagequest;

import map.*;
import org.newdawn.slick.Image;
import scripting.Thread;

/**
 *
 * @author Edmund
 */
public class Global {
    public static Map currentMap;
    public static Camera camera;
    public static Image character;
    
    public static boolean isFrozen = false;
    public static Thread unfrozenThread = null;
}
