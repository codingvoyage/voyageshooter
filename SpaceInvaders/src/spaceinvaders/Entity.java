/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

/**
 *
 * @author Edmund
 */
public class Entity implements Scriptable 
{
    private int scriptID;
    private int currentLineNumber;
    private boolean inProgress;
    private Parameter progressTemp;
    
    double xcoordinate;
    double ycoordinate;
    double vx;
    double vy;
    
    
    //Basic entity class for testing purposes.
    public Entity()
    {
        xcoordinate = 200;
        ycoordinate = 200;
        vx = 0.2;
        vy = 0.0;
        
        scriptID = 1;
        currentLineNumber = 0;
        inProgress = false;
    }
    
    
    public boolean continueMove(double delta)
    {
        xcoordinate += vx * delta;
        ycoordinate += vy * delta;
        
        double movedDistance = Math.abs((vx * delta) + (vy * delta));
        
        progressTemp.setDoubleValue(
               progressTemp.getDoubleValue() - 
               movedDistance);
        
        if (progressTemp.getDoubleValue() < 0)
        {
            System.out.println("We're done walking.");
            //Oh, so we're done moving. Great.
            inProgress = false;
            return false;
        }
        
        //Alright, we still have to move. Keep moving.
        return true;
    }
    
    public boolean continueWait(double delta)
    {
        progressTemp.setDoubleValue(
                progressTemp.getDoubleValue() - delta);
        
        if (progressTemp.getDoubleValue() < 0)
        {
            //Oh, so we're done waiting. Great.
            System.out.println("Finished waiting!");
            inProgress = false;
            return false;
        }
        
        //Alright, we still have milliseconds left to wait. Keep going
        
        return true;
    }
    
    public void beginWait(double millisecondsToWait)
    {
        
        progressTemp = new Parameter(millisecondsToWait);
        
        System.out.println("Alright, time to wait for " + 
                progressTemp.getDoubleValue() + " seconds.");
        
        inProgress = true;
    }
    
    public void beginMove(double pixelsToMove)
    {
        progressTemp = new Parameter(pixelsToMove);
        inProgress = true;
    }
    
    
    public int getLineNumber()
    {
        return currentLineNumber;
    }
    
    public void setLineNumber(int newLineNumber)
    {
        currentLineNumber = newLineNumber;
    }
    
    public int getScriptID()
    {
        return scriptID;
    }
    
    public boolean isRunning()
    {
        return inProgress;
    }
    
    
}
