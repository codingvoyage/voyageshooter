package spaceinvaders.entity;

import spaceinvaders.script.*;

/**
 * Any entity with velocity
 * @author Brian Yang
 */
public abstract class MovableEntity extends Entity implements Movable {
    
    /** The entity's current velocity in mph */
    public Double v;
    
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
        v = 10.0;
    }
    
    /**
     * Constructs a new MovableEntity
     * @param name name of entity
     * @param id id (index) of entity
     * @param image the image reference name of entity
     * @param description description of entity
     * @param vx x-velocity of movable entity
     * @param vy y-velocity of movable entity
     */
    public MovableEntity(String name, String id, String image, float radius, String description, double v) {
        super(name, id, image, radius, description);
        this.v = v * VELOCITY_FACTOR;
    }
    
    /**
     * Constructs a new scripted MovableEntity
     * @param name name of entity
     * @param id id (index) of entity
     * @param image the image reference name of entity
     * @param scriptID the script ID of scripted entity
     * @param description description of entity
     * @param vx x-velocity of movable entity
     * @param vy y-velocity of movable entity
     */
    public MovableEntity(String name, String id, String image, float radius, int scriptID, String description, double v) {
        super(name, id, image, radius, scriptID, description);
        this.v = v * VELOCITY_FACTOR;
    }
    
    /**
     * Move after turning by an angle
     * @param angle angle to turn by
     * @param pixelsToMove how many pixels the entity should move
     */
    public void beginMove(float angle, double pixelsToMove) {
        double[] param = {angle, pixelsToMove};
        setTemporaryParameter(new Parameter(param));
        beginMove(pixelsToMove);
    }
    
    /**
     * Move after turning to an angle
     * <strong>Note:</strong> To distinguish this method from the previous, 
     * which turns the entity <em>by</em> an angle instead of <em>to</em>, 
     * add a boolean 'true' as the third parameter to activate this method.
     * @param angle angle to turn to
     * @param pixelsToMove how many pixels the entity should move
     * @param turnTo true if turn to angle, false if turn by angle, defaults to false
     */
    public void beginMove(float angle, double pixelsToMove, boolean turnTo) {
        if(turnTo) {
            double[] param = {getRotation() - angle, pixelsToMove};
            setTemporaryParameter(new Parameter(param));
            beginMove(pixelsToMove);
        } else {
            beginMove(angle, pixelsToMove);
        }
    }
    
    /**
     * Move to another entity
     * @param entity the other entity
     */
    public void beginMove(Entity entity) {
        double[] param = {rotate(entity), this.position.distance(entity.position)};
        setTemporaryParameter(new Parameter(param));
        beginMove(param[1]);
    }
    
    /**
     * Move a number of pixels towards another entity
     * @param entity the other entity
     * @param pixelsToMove how many pixels the entity should move
     */
    public void beginMove(Entity entity, double pixelsToMove) {
        setRotation(entity);
        setTemporaryParameter(new Parameter(pixelsToMove));
        beginMove(pixelsToMove);
    }
    
    /**
     * Starts the movement
     * @param pixelsToMove how many pixels the entity should move
     */
    @Override
    public void beginMove(double pixelsToMove) {
        if (getTemporaryParameter() == null) // if its called from another beginMove method, don't reset the parameter
            setTemporaryParameter(new Parameter(pixelsToMove));
        mainThread.setRunningState(true);
    }
    
        
    /**
     * Checks if the entity should continue moving (straight line)
     * @param delta elapsed time between checks
     * @return boolean indicating whether or not the entity should continue moving
     */
    @Override
    public boolean continueMove(double delta) {
        Parameter tempParam = getTemporaryParameter();

        float step = STEP_SIZE * (float)delta;
        move(step * Math.sin(Math.toRadians(getRotation())), -step * Math.cos(Math.toRadians(getRotation())));
        
        double movedDistance = Math.abs((step * Math.sin(Math.toRadians(getRotation()))) + (-step * Math.cos(Math.toRadians(getRotation()))));
        
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
     * Circular motion
     * Move the entity in an orbital path
     * @param startAngle initial angle in degrees the orbit starts at (Think polar coordinates)
     * @param radius radius of the orbital circle
     * @param clockwise indicates whether the orbit is clockwise (0) or counterclockwise (anything else)
     * @param entity the entity to orbit around
     */
    public void beginOrbit( double startAngle, double radius, double clockwise, MovableEntity entity) {

        // Create an array containing the angle, radius, and clockwise selection
        Object [] orbitParams = {startAngle, radius, clockwise, entity};

        // Store the angle and radius
        setTemporaryParameter(new Parameter(orbitParams));

        // Orbit is now running!
        mainThread.setRunningState(true);
        
        
    }
    
    /**
     * Continue orbiting<br/>
     * Will move to the specified radius from the origin entity first
     * @param delta the total time of the orbit
     * @param vx x velocity
     * @param vy y velocity
     * @return boolean indicating whether or not the orbit is complete
     */
    public boolean continueOrbit(double delta) {
        
        Parameter tempParam = getTemporaryParameter();
        Object[] params = tempParam.getObjectArrayValue();
        /*
         * 0 - start angle
         * 1 - radius
         * 2 - clockwise
         * 3 - the origin entity
         */
        
        // distance from entity
        Entity origin = (Entity)params[3];
        float distance = this.position.distance(origin.position);
        
        
        // angular velocity
        double velocity = v/(Double)(params[1]);
        
        double step = v * delta;
        
        System.out.println("Move called");
        move(step * Math.sin(Math.toRadians(getRotation())), -step * Math.cos(Math.toRadians(getRotation())));
        
        rotate((float)(Math.toDegrees(velocity)*delta));
        
        System.out.println("Rotation is: " + this.getRotation());
        
        return true;
    }
     
    /**
     * Set the velocity
     * @param v velocity in mph
     */
    @Override
    public void setVelocity(double v) {
        this.v = v;
    }
    
    /**
     * Get velocity
     * @return the entity's velocity
     */
    @Override
    public double getVelocity() {
        return v;
    }   
    
}
