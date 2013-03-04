package spaceinvaders;

import java.util.HashMap;

/**
 *
 * @author Edmund
 */
public class Thread {
    //Which script are we running, and what line are we on?
    private int scriptID;
    private int currentLineNumber;
    
    //Whether the Scriptable is in the middle of the indicated command
    private boolean inProgress;
    
    //Holds the memory
    //For Threads tied with Entities, set it equal to the Entity's 
    //memoryBox so that memory can be shared between Threads.
    //For Threads not tied with Entities, set it to an individual memoryBox
    private HashMap<String, Parameter> memoryBox;
    
    //This is the mannequin which the Thread object holds onto
    private Scriptable linkedScriptable;
    
    //Is this thread ready to die?
    private boolean markedForDeletion;
    
    //For waiting, which totally SHOULD be a thread function
    private double waitMilliseconds;
    
    //Identifier
    private String name;
    
    
    public Thread(int scriptID) 
    {
        setScriptID(scriptID);
        markedForDeletion = false;
    }
    
    
    public void markForDeletion() 
    { 
        markedForDeletion = true;
    }
    
    public boolean isMarkedForDeletion()
    {
        return markedForDeletion;
    }
    
    //Accessors and mutators for the mannequin Scriptable
    public void setScriptable(Scriptable scriptableObj)
    {
        linkedScriptable = scriptableObj;
    }
   
    public Scriptable getScriptable()
    {
        return linkedScriptable;
    }   
    
    
    //Accessors and mutators for the line number on the script
    public void setLineNumber(int newLineNumber)
    {
        currentLineNumber = newLineNumber;
    }
   
    public int getLineNumber()
    {
        return currentLineNumber;
    }
    
    //Accessors and mutators for ScriptID
    protected void setScriptID(int newScriptID)
    {
        scriptID = newScriptID;
    }
    
    public int getScriptID()
    {
        return scriptID;
    }
    
    //Running state, or basically, is the script in the middle of executing
    //some command
    public boolean isRunning()
    {
        return inProgress;
    }
    
    protected void setRunningState(boolean progress)
    {
        inProgress = progress;
    }
    
    //Accessors and mutators for the name
    public void setName(String newName)
    {
        name = newName;
    }
   
    public String getName()
    {
        return name;
    }
    
    //Waiting
    public void beginWait(double millisecondsToWait)
    {
        waitMilliseconds = millisecondsToWait;
        setRunningState(true);
    }
    
    public boolean continueWait(double delta)
    {
        //Update the temporary value with the delta time
        waitMilliseconds -= delta;
        
        if (waitMilliseconds < 0)
        {
            //Oh, so we're done waiting. Great.
            //System.out.println("Finished waiting!");
            setRunningState(false);
            return false;
        }
        
        //Alright, we still have milliseconds left to wait. Keep going
        return true;
    }
    
    
}
