package spaceinvaders.entity;
import org.newdawn.slick.*;

/**
 * Entity with Defense
 * <strong>Known Implementations:</strong> Enemy, Misc, Immovable
 * @author Brian Yang
 */
public interface Defender {
    /** Base defense of entity */
    public double getDefense();
    /** Get HP */
    public int getHp();
    /** Get Max HP */
    public int getMaxHp();
    /** 
     * Deduct HP 
     * @param hp the number of points to deduct
     */
    public void deductHp(int hp) throws SlickException;
    /** Die - set HP to 0 */
    public void die();
}