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
     * @param vx x velocity in mph
     * @param vy y velocity in mph
     */
    public void setVelocity(double vx, double vy);
    
    /**
     * Get X velocity
     * @return the entity's x velocity
     */   
    public double getVx();
    
    /**
     * Get Y velocity
     * @return the entity's y velocity
     */    
    public double getVy();
}
