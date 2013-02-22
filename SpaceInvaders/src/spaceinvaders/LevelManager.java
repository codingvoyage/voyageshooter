/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

/**
 *
 * @author Edmund
 */
public class LevelManager implements Scriptable {
    private int scriptID;
    private int currentLineNumber;
    private boolean inProgress;
    private double waitTempInfo;
    
    public LevelManager()
    {
        scriptID = 1;
        currentLineNumber = 0;
        inProgress = false;
    }
    
    
    public void spawnEntity() {
        
    
    }
    
    public int getLineNumber()
    {
        return currentLineNumber;
    }
    
    public int getScriptID()
    {
        return scriptID;
    }
    
    public boolean isRunning()
    {
        return inProgress;
    }
    
    public void setLineNumber(int newLineNumber) 
    {
        currentLineNumber = newLineNumber;
    }
    
    public boolean continueWait(double delta)
    {
        waitTempInfo -= delta;
        
        if (waitTempInfo < 0)
        {
            //Oh, so we're done waiting. Great.
            inProgress = false;
            return false;
        }
        
        //Alright, we still have milliseconds left to wait. Keep giong
        return true;
    }
    
    public void beginWait(double millisecondsToWait)
    {
        waitTempInfo = millisecondsToWait;
        inProgress = true;
    }
  
}
