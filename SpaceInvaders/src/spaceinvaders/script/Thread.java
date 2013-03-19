package spaceinvaders.script;

import java.util.HashMap;
import java.util.Stack;
/**
 *
 * @author Edmund
 */
public class Thread {
    /** The identifier name of this Thread object */
    private String name;
    
    /** The script which this Thread is currently running */
    private int scriptID;
    
    /** The "main" scriptID for this Thread. */
    public final int baseScriptID;
    
    /** The current line of the current script this Thread is on */
    private int currentLineNumber;
    
    /** Whether the Scriptable is in the middle of the indicated command */
    private boolean inProgress;
    
    /** A reference to the Scriptable class which this Thread may control */
    private Scriptable linkedScriptable;
    
    /** The number of milliseconds this Thread has left to wait. */
    private double waitMilliseconds;
    
    /** Indicates whether this Thread is ready to die. */
    private boolean markedForDeletion;
    
    
    /** Holds the variables of the main part of this Thread. */
    private HashMap<String, Parameter> memoryBox;
    
    /** Holds the variables of the current function of this Thread. */
    private HashMap<String, Parameter> temporaryVariables;
    
    
    
    
    
    //For jumping back to whence we came 
    //private
    private Stack functionStack;
    
    //Keep track of how many functions deep we are
    public  int functionLayer;
    
    private String[] functionReturns;
    
    
    
    
    public Thread(int scriptID) 
    {
        markedForDeletion = false;
        
        setScriptID(scriptID);
        baseScriptID = scriptID;
        
        memoryBox = new HashMap<String, Parameter>();
        
        functionStack = new Stack();
        functionLayer = 0;
    }
    
    
    /**
     * Marks this Thread for deletion, indicating that it
     * should no longer run.
     */
    public void markForDeletion() 
    { 
        markedForDeletion = true;
    }
    
    
    /**
     * Returns whether this Thread has been killed or not
     * @return whether this Thread is marked for deletion.
     */
    public boolean isMarkedForDeletion()
    {
        return markedForDeletion;
    }
    
    
    /**
     * Accessors and mutators for the mannequin Scriptable
     */
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
   
    public int getCurrentLine()
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
    
    public void setRunningState(boolean progress)
    {
        inProgress = progress;
    }
    
    //Accessors and mutators for the name
    public void setName(String newName)
    {
        name = newName;
    }
   
    //maybe make a "base thread" name
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
    
    //Memory/variable magic
    public void setVariable(String identifier, Parameter value) 
    {
        //Basically, here's the rule. When we're in a function, all variables
        //end up being LOCAL. 
        if (functionLayer > 0)
        {
            temporaryVariables.put(identifier, value);
        }
        else
        {
            //Otherwise, place the variable in the main memory
            memoryBox.put(identifier, value);
        }
    }
    
    public void newVariable(String identifier) 
    {
        //Declaring a new Variable without a value yet...
        //That's just using setVariable, except with a null.
        setVariable(identifier, null);
    }
    
    public Parameter getVariable(String identifier) 
    {
        //When the caller wants to get the Parameter referred to by identifier,
        //we don't know if it's located in temporary memory or main memory.
        //Hence, just look for it in temporary first (if applicable)

        if (functionLayer > 0)
        {
            Parameter tryTempMemory = temporaryVariables.get(identifier);
            
            //NOTICE how local variables shadow the instance fields in main.
            if (tryTempMemory != null) {
                //Ah, we found it. Return it.
                return tryTempMemory;
            }
            else
            {
                //It must be in main memory
                return memoryBox.get(identifier);
            }
        }
        else 
        {
            //We're on the main, so don't bother with temporary memory
            
             return memoryBox.get(identifier);
            
        }
    }
    
    public void deleteVariable(String identifier)
    {
        memoryBox.remove(identifier);
    }
    
    public HashMap<String, Parameter> getMemoryBox()
    {
        return memoryBox;
    }
    
    public void setMemoryBox(HashMap<String, Parameter> newMemoryBox)
    {
        memoryBox = newMemoryBox;
    }
    
    public void setLocalMemoryBox(HashMap<String, Parameter> newTempVariables)
    {
        temporaryVariables = newTempVariables;
    }

    
    
    //Stack stuff! 
    public void makeReturnPoint()
    {
        //System.out.println("We are a creating a return point: " 
        //  + "at scriptID " + getScriptID() + ". " + 
        //  "The line we're saving is " + getCurrentLine() + 
        //  ".");
        returnPoint foo = new returnPoint(getScriptID(), 
                getCurrentLine(), 
                memoryBox,
                temporaryVariables,
                functionReturns);
        
        functionStack.push(foo);
        
    }
    
    public void restoreLastReturnPoint()
    {
        //Get the last save return point.
        returnPoint foo = (returnPoint)functionStack.pop();
        
        //Make sure you go to the line and ID which we left off...
        setScriptID(foo.getScriptID());
        setLineNumber(foo.getLastLine());
        
        //Retrieve the main memory and function memory which we left off...
        memoryBox = foo.getMemoryBox();
        temporaryVariables = foo.getTemporaryMemoryBox();
        functionReturns = foo.getFunctionReturns();
        //System.out.println(getName() + " is the name of the"
        //+ "current thread layer now! The one we jump back to...");
    }
    
    public void setFunctionReturns(String[] toReturn)
    {
        functionReturns = toReturn;
    }
    
    public String[] getFunctionReturns()
    {
        return functionReturns;
    }
    
    public void increaseFunctionLayer()
    {
        //We need to go deeper
        functionLayer++;
    }
    
    public void decreaseFunctionLayer()
    {
        //Non, je ne regrette rien
        functionLayer--;
    }
    
    
    
    
    private class returnPoint
    {
        private int scriptID;
        private int lastLine;
        private HashMap<String, Parameter> memoryBox;
        private HashMap<String, Parameter> temporaryVariables;
        private String[] functionReturns;        
        
        returnPoint(int newScriptID, int newCurrentLine, 
                HashMap<String, Parameter> lastMemoryBox, 
                HashMap<String, Parameter> lastTemporaryVarBox,
                String[] lastFunctionReturns)
        {
            
            scriptID = newScriptID;
            lastLine = newCurrentLine;
            memoryBox = lastMemoryBox;
            temporaryVariables = lastTemporaryVarBox;
            functionReturns = lastFunctionReturns;
            
        }
        
        int getScriptID()
        {
            return scriptID;
        }
                
        int getLastLine()
        {
            return lastLine;
        }
        
        HashMap<String, Parameter> getMemoryBox()
        {
            return memoryBox;
        }
        
        HashMap<String, Parameter> getTemporaryMemoryBox()
        {
            return temporaryVariables;
        }
        
        private String[] getFunctionReturns()
        {
            return functionReturns;
        }
        
    }
}