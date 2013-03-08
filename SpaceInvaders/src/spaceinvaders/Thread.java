package spaceinvaders;

import java.util.HashMap;
import java.util.Stack;
/**
 *
 * @author Edmund
 */
public class Thread {
    //Base scriptID, the one which is on the top and its "main".
    public final int baseScriptID;
    
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
    
    //Identifier, the name of which basically allows us to find the thread
    //and kill it or something. 
    private String name;
    
    //For jumping back to whence we came 
    //private
    Stack functionStack;
    
    //For functions
    Thread currentThreadLayer;
    
    
    
    public Thread(int scriptID) 
    {
        currentThreadLayer = this;
        
        currentThreadLayer.markedForDeletion = false;
        
        currentThreadLayer.setScriptID(scriptID);
        baseScriptID = scriptID;
        
        currentThreadLayer.functionStack = new Stack();
        currentThreadLayer.memoryBox = new HashMap<String, Parameter>();
    }
    
    public boolean atBaseThread()
    {
        if (currentThreadLayer == this)
            return true;
        else
            return false;
    }
    
    
    public void markForDeletion() 
    { 
        currentThreadLayer.markedForDeletion = true;
    }
    
    public boolean isMarkedForDeletion()
    {
        return currentThreadLayer.markedForDeletion;
    }
    
    //Accessors and mutators for the mannequin Scriptable
    public void setScriptable(Scriptable scriptableObj)
    {
        currentThreadLayer.linkedScriptable = scriptableObj;
    }
   
    public Scriptable getScriptable()
    {
        return currentThreadLayer.linkedScriptable;
    }   
    
    
    //Accessors and mutators for the line number on the script
    public void setLineNumber(int newLineNumber)
    {
        currentLineNumber = newLineNumber;
    }
   
    public int getCurrentLine()
    {
        return currentLineNumber;
    }
    
    //Accessors and mutators for ScriptID
    protected void setScriptID(int newScriptID)
    {
        currentThreadLayer.scriptID = newScriptID;
    }
   
    public int getScriptID()
    {
        return currentThreadLayer.scriptID;
    }
    
    //Running state, or basically, is the script in the middle of executing
    //some command
    public boolean isRunning()
    {
        return inProgress;
    }
    
    protected void setRunningState(boolean progress)
    {
        currentThreadLayer.inProgress = progress;
    }
    
    //Accessors and mutators for the name
    public void setName(String newName)
    {
        currentThreadLayer.name = newName;
    }
   
    public String getName()
    {
        return currentThreadLayer.name;
    }
    
    //Waiting
    public void beginWait(double millisecondsToWait)
    {
        currentThreadLayer.waitMilliseconds = millisecondsToWait;
        currentThreadLayer.setRunningState(true);
    }
    
    public boolean continueWait(double delta)
    {
        //Update the temporary value with the delta time
        currentThreadLayer.waitMilliseconds -= delta;
        
        if (currentThreadLayer.waitMilliseconds < 0)
        {
            //Oh, so we're done waiting. Great.
            //System.out.println("Finished waiting!");
            currentThreadLayer.setRunningState(false);
            return false;
        }
        
        //Alright, we still have milliseconds left to wait. Keep going
        return true;
    }
    
    //Memory/variable magic
    public void setVariable(String identifier, Parameter value) 
    {
        currentThreadLayer.memoryBox.put(identifier, value);
    }
    
    public void newVariable(String identifier) 
    {
        //Scripter, it's YOUR FAULT if it crashes because you fail to
        //initialize the variable! LOL
        currentThreadLayer.memoryBox.put(identifier, null);
    }
    
    public Parameter getVariable(String identifier) 
    {
        return currentThreadLayer.memoryBox.get(identifier);
    }
    
    public void deleteVariable(String identifier)
    {
        currentThreadLayer.memoryBox.remove(identifier);
    }
    
    public HashMap<String, Parameter> getMemoryBox()
    {
        return currentThreadLayer.memoryBox;
    }
    
    public void setMemoryBox(HashMap<String, Parameter> newMemoryBox)
    {
        currentThreadLayer.memoryBox = newMemoryBox;
    }

    //Stack stuff!
    
            
            
            
    public void makeReturnPoint()
    {
        System.out.println("We are a creating a return point: " 
                + "at scriptID " + currentThreadLayer.getScriptID() + ". " + 
                "The line we're saving is " + currentThreadLayer.getCurrentLine() + 
                ".");
        returnPoint foo = new returnPoint(currentThreadLayer.getScriptID(), 
                currentThreadLayer.getCurrentLine(), 
                currentThreadLayer, currentThreadLayer.memoryBox);
        
        functionStack.push(foo);
        
    }
    
    public void restoreLastReturnPoint()
    {
        
        returnPoint foo = (returnPoint)functionStack.pop();
        
        
       System.out.println("omg");
       System.out.println("restoring to ID #" + foo.getScriptID() +
               " and lastLine is " + foo.getLastLine());
       
            setScriptID(foo.getScriptID());
            setLineNumber(foo.getLastLine());
            currentThreadLayer = foo.getLastThread();
            
            System.out.println(currentThreadLayer.getName() + " is the name of the"
                    + "current thread layer now! The one we jump back to...");
            currentThreadLayer.memoryBox = foo.getMemoryBox();
    }
    
    private class returnPoint
    {
        private int scriptID;
        private int lastLine;
        private Thread lastThread;
        private HashMap<String, Parameter> memoryBox;
        
        returnPoint(int newScriptID, int newCurrentLine, Thread lastThread, 
                HashMap<String, Parameter> lastMemoryBox)
        {
            scriptID = newScriptID;
            lastLine = newCurrentLine;
            memoryBox = lastMemoryBox;
            this.lastThread = lastThread;
        }
        
        int getScriptID()
        {
            return scriptID;
        }
                
        int getLastLine()
        {
            return lastLine;
        }
        
        Thread getLastThread()
        {
            return lastThread;
        }
        
        HashMap<String, Parameter> getMemoryBox()
        {
            return memoryBox;
        }
        
    }
}
