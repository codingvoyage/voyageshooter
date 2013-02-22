package spaceinvaders;

/**
 *
 * @author Edmund
 */
public abstract class ScriptableClass implements Scriptable {
    private int scriptID;
    private int currentLineNumber;
    private boolean inProgress;
    private Parameter progressTemp;
    
    
    public ScriptableClass(int newScriptID) 
    {
        this.scriptID = newScriptID;
        currentLineNumber = 0;
        inProgress = false;
    }
    
    
    public ScriptableClass() 
    {
        this.scriptID = -1;
        currentLineNumber = 0;
        inProgress = false;
    }
    
    public void beginWait(double millisecondsToWait)
    {
        
        progressTemp = new Parameter(millisecondsToWait);
        
        System.out.println("Alright, time to wait for " + 
                progressTemp.getDoubleValue() + " seconds.");
        
        inProgress = true;
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
    
    public void setLineNumber(int newLineNumber)
    {
        currentLineNumber = newLineNumber;
        
    }
   
    public int getLineNumber()
    {
        return currentLineNumber;
    }
    
    protected void setScriptID(int newScriptID)
    {
        scriptID = newScriptID;
    }
    
    public int getScriptID()
    {
        return scriptID;
    }
    
    public boolean isRunning()
    {
        return inProgress;
    }
    
    protected void setRunningState(boolean progress)
    {
        inProgress = progress;
    }
    
    protected void setTemporaryParameter(Parameter newParameter) 
    {
        progressTemp = newParameter;
    }
    
    protected Parameter getTemporaryParameter() 
    {
        return progressTemp;
    }
}
