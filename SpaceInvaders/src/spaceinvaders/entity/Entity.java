package spaceinvaders.entity;

import spaceinvaders.Parameter;
import spaceinvaders.ScriptableClass;


/**
 * Entity 
 * Used by all entities
 * 
 * @author Brian Yang
 * @author Edmund
 */
public class Entity extends ScriptableClass
{
    
    private int id;
    private double x;
    private double y;
    private double vx;
    private double vy;
    private String name;
    
    
    //Basic entity class for testing purposes.
    public Entity()
    {
        super();
        
        // Default values if the entity isn't built with JSON
        id = 1337;
        x = 200;
        y = 200;
        vx = 10;
        vy = 0;
        name = "The Greatest Entity on Earth";
    }
    
    /*
     * Get ID
     * @return the entity's ID
     */
    public int getID() {
        return id;
    }
    
    /*
     * Get Name
     * @return the entity's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get X
     * @return the entity's x-coordinate
     */    
    public double getX() {
        return x;
    }

    /**
     * Get Y
     * @return the entity's y-coordinate
     */    
    public double getY() {
        return y;
    }
    
     /**
     * Get X velocity
     * @return the entity's x velocity
     */    
    public double getXVelocity() {
        return vx;
    }   
    
     /**
     * Get Y velocity
     * @return the entity's y velocity
     */    
    public double getYVelocity() {
        return vy;
    }   
    
    
    public boolean continueMove(double delta)
    {
        x += vx * delta;
        y += vy * delta;
        
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
    
    public void beginMove(double pixelsToMove)
    {
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
     * @return boolean indicating whether or not the orbit is complete
     */
    public boolean continueOrbit(double delta) {
        
        double angle = getTemporaryParameter().getDoubleArrayValue()[0];
        double radius = getTemporaryParameter().getDoubleArrayValue()[1];
        
        // temporary entity - represents the origin entity (such as the player)
        Entity origin = new Entity();
        
        // convert degrees to radians
        double radians = angle * (Math.PI / 180);
        
        // convert tangential velocity (vx and vy) to angular velocity (w for omega)
        double w = (Math.sqrt(Math.pow(vx,2) + Math.pow(vy,2)))/radius;
        
        // determine coordinates of the orbiter
        x = origin.getX() + radius * Math.cos(radians);
        y = origin.getY() + radius * Math.sin(radians);
        
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
    
}
