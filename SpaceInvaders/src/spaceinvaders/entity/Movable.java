package spaceinvaders.entity;

/**
 * Movable is implemented with all entities that can move
 * @author Brian Yang
 */
public interface Movable {
    /** The entity's current x-coordinate */
    public double getX();
    /** The entity's current y-coordinate */
    public double getY();
    
    /** The entity's current x-velocity */
    public double getVx();
    /** The entity's current y-velocity */
    public double getVy();
    
    /** Checks whether the entity should continue moving */
    public boolean continueMove(double delta);
    
}
