package spaceinvaders.entity;

/**
 * Anything that can move on its own (without user control)
 * @author Brian Yang
 */
public interface Movable {
    
    /**
     * Checks if the entity should continue moving (straight line)
     * @param delta elapsed time between checks
     * @return boolean indicating whether or not the entity should continue moving
     */
    public boolean continueMove(double delta);
    
     /**
     * Starts the movement
     * @param pixelsToMove how many pixels the entity should move
     */
    public void beginMove(double pixelsToMove);
    
    /**
     * Set the velocity
     * @param v velocity in mph
     */
    public void setVelocity(double v);
    
    /**
     * Get velocity
     * @return the entity's velocity
     */   
    public double getVelocity();
}
