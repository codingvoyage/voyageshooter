/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

/**
 * @author Bakesale
 * @author Edmund
 */
public class Entity extends ScriptableClass
{
    
    double xcoordinate;
    double ycoordinate;
    double vx;
    double vy;
    
    
    //Basic entity class for testing purposes.
    public Entity()
    {
        super();
        
        xcoordinate = 200;
        ycoordinate = 200;
        vx = 0.2;
        vy = 0.0;
        
    }
    
    
    public boolean continueMove(double delta)
    {
        xcoordinate += vx * delta;
        ycoordinate += vy * delta;
        
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
    
    
}
