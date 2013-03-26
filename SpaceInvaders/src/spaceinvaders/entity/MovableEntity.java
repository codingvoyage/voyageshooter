package spaceinvaders.entity;

import spaceinvaders.script.*;

/**
 * Any entity with velocity
 * @author Brian Yang
 */
public abstract class MovableEntity extends Entity implements Movable {
    
    /** The entity's current x-velocity in mph */
    public Double vx;
    /** The entity's current y-velocity in mph */
    public Double vy;
     
    /** origin */
    public static final double ORIGIN = 0.0;
    
    /** map boundary factor */
    public static final float EDGE_FACTOR = 75.0f;
    /** velocity conversion from miles to pixels */
    public static final double VELOCITY_FACTOR = 0.01;
    
    /** rotation size */
    public static final float ROTATION_SIZE = 0.2f;
    /** step size */
    public static final float STEP_SIZE = 0.4f;
    /** back up size */
    public static final float BACK_SIZE = 0.075f;
    
    /**
     * Constructs a new MovableEntity
     */
    public MovableEntity() {
        super();
    }
    
    /**
     * Constructs a new MovableEntity
     * @param name name of entity
     * @param id id (index) of entity
     * @param description description of entity
     * @param vx x-velocity of movable entity
     * @param vy y-velocity of movable entity
     */
    public MovableEntity(String name, int id, String image, String description, double vx, double vy) {
        super(name, id, image, description);
        this.vx = vx * VELOCITY_FACTOR;
        this.vy = vy * VELOCITY_FACTOR;
    }
    
    /**
     * Checks if the entity should continue moving (straight line)
     * @param delta elapsed time between checks
     * @return boolean indicating whether or not the entity should continue moving
     */
    @Override
    public boolean continueMove(double delta) {
        move(vx * delta, vy * delta);
        
        double movedDistance = Math.abs((vx * delta) + (vy * delta));
        
        Parameter tempParam = getTemporaryParameter();
        tempParam.setDoubleValue(
               tempParam.getDoubleValue() - 
               movedDistance);
        
        setTemporaryParameter(tempParam);
        
        if (tempParam.getDoubleValue() < 0)
        {
            System.out.println("We're done walking.");
            //Oh, so we're done moving. Great.
            mainThread.setRunningState(false);
            return false;
        }
        
        //Alright, we still have to move. Keep moving.
        return true;
    }
    
    /**
     * Starts the movement
     * @param pixelsToMove how many pixels the entity should move
     */
    @Override
    public void beginMove(double pixelsToMove) {
        setTemporaryParameter(new Parameter(pixelsToMove));
        mainThread.setRunningState(true);
    }
    
    /**
     * Circular motion
     * Move the entity in an orbital path
     * @param startAngle initial angle in degrees the orbit starts at (Think polar coordinates)
     * @param radius radius of the orbital circle
     * @param clockwise indicates whether the orbit is clockwise (0) or counterclockwise (anything else)
     */
    public void beginOrbit(double startAngle, double radius, double clockwise) {

        // Create an array containing the angle, radius, and clockwise selection
        double [] orbitParams = {startAngle, radius, clockwise};

        // Store the angle and radius
        setTemporaryParameter(new Parameter(orbitParams));

        // Orbit is now running!
        mainThread.setRunningState(true);
    }
    
    /**
     * Continue circular motion
     * @param delta the total time of the orbit
     * @param vx x velocity
     * @param vy y velocity
     * @return boolean indicating whether or not the orbit is complete
     */
    public boolean continueOrbit(double delta, double vx, double vy) {
        
        double angle = getTemporaryParameter().getDoubleArrayValue()[0];
        double radius = getTemporaryParameter().getDoubleArrayValue()[1];
        
        // temporary entity - represents the origin entity (such as the player)
        Enemy origin = new Enemy();
        
        // convert degrees to radians
        double radians = angle * (Math.PI / 180);
        
        // convert tangential velocity (vx and vy) to angular velocity (w for omega)
        double w = (Math.sqrt(Math.pow(vx,2) + Math.pow(vy,2)))/radius;
        
        // determine coordinates of the orbiter
       // x = origin.getX() + radius * Math.cos(radians);
       // y = origin.getY() + radius * Math.sin(radians);
        
        // the angle needs to change
        if (getTemporaryParameter().getDoubleArrayValue()[2] == 0.0) { // clockwise
            angle += w;
        } else { // counterclockwise
            angle -= w;
        }
        
        // implement the delta time, the checks (Are we done stuff), and everything else in this formula later :)
                
        // temporary only
        return true;
    }
     
    /**
     * Set the velocity
     * @param vx x velocity in mph
     * @param vy y velocity in mph
     */
    @Override
    public void setVelocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }
    
    /**
     * Get X velocity
     * @return the entity's x velocity
     */
    @Override
    public double getVx() {
        return vx;
    }   
    
    /**
     * Get Y velocity
     * @return the entity's y velocity
     */   
    @Override
    public double getVy() {
        return vy;
    } 
    
}
