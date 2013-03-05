package spaceinvaders;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edmund
 */
public class ScriptReader
{
    private ScriptManager scr;
    private ThreadManager threadManager;
    
    //These will get changed every time act(Scriptable s, double deltaTime)
    //is called. It makes it convenient since now the ScriptReader methods
    //do not have to be passed this every time.
    private Scriptable currentScriptable;
    private Thread currentThread;
    private double currentDeltaTime;
    
    public ScriptReader(ScriptManager scriptManagerHandle) 
    {
        //Initialize ScriptManager! rather, change the constructor such that
        //it can receive the object reference to ScriptManager, which probably
        //has been initialized sometime during the initialization stage of the
        //program
        scr = scriptManagerHandle;
        
    }
    
    public void setThreadHandle(ThreadManager threadManagerHandle)
    {
        threadManager = threadManagerHandle;
    }
            
            
    
    public void act(Thread t, double deltaTime)
    {
        currentDeltaTime = deltaTime;
        currentThread = t;
        
        //Get Scriptable Object
        currentScriptable = currentThread.getScriptable();
        
        //We need to know what line of what script the Thread is on
        int currentLineNumber = currentThread.getLineNumber();
        int currentScriptID = currentThread.getScriptID();
        
        
        //Now we call ScriptManager to have it return a reference to the
        //Script class which currentScriptID refers to
        Script currentScript = scr.getScriptAtID(currentScriptID);
        
        //Now, we get the current line
        Line currentLine = currentScript.getLine(currentLineNumber);
        
        //Now, our response depends on whether there is a command already in 
        //execution
        if (currentThread.isRunning()) 
        {
            //Will we continue running the same command?
            boolean doesContinue = continuesRunning(currentLine);
            
            //This only gets called when we JUST finished the current command
            if (!doesContinue) 
            {
                //So since we're not staying on the same command, we now move on
                //to the next line!
                
                currentThread.setLineNumber(
                        currentThread.getLineNumber() + 1);
            }
        }
        
        //Now, if by now there is no command already in execution, then...
        if (!currentThread.isRunning()) 
        {
            
            //We will go from line to line
            boolean doWeContinue = true;
            while (doWeContinue) 
            {
                //First, get the current Line of the current Script
                //This is kind of a mouthful, so first it gets the ScriptID
                //from the class implementing Scriptable, and then grabs from
                //the ScriptManager, using that scriptID, the Script object
                //itself, and then finally, from that Script object the program
                //retrieves the line which is located at the line number
                //specified in the class implementing Scriptable.
                Line thisLine = scr.getScriptAtID(currentThread.getScriptID()).
                        getLine(currentThread.getLineNumber());
                
                //Now that we have the line, we execute it.
                doWeContinue = executeCommand(thisLine);
                
                //Now, if we aren't halting, then after the line is over, 
                //move on to the next line! UNLESS it was a goto statement!
                //Of course, if it is a goto statement, it will remain a
                //"Yes we continue!" but without switching lines
                if (doWeContinue && (thisLine.getCommandID() != 1)) 
                {
                    //System.out.println("Next line!");
                    currentThread.setLineNumber(
                        currentThread.getLineNumber() + 1);
                }
                else 
                {
                    //Well, we DID stop then, and so we DON'T want to move on
                    //to the next line...
                }
                
            } //End of the while loop which basically continues to execute the
            //next command until it reaches a command which takes time to do
          
        } //This is the end of the if-statement that executes commands. It will
        //only get called if there isn't a command in progress already for
        //the given script
        
    } //This is the end of the entire act(Scriptable s, double deltaTime) method
    
    
    //continuesRunning basically asks currentScriptable whether it has completed
    //its task. If it still has not, then continuesRunning returns a TRUE, as in
    //"yes, since the task isn't complete, DO CONTINUE TO RUN". If the class
    //instead indicates that it has completed its task, then continuesRunning
    //will return false.
    private boolean continuesRunning(Line currentLine)
    {
        boolean result = true;
        
        switch (currentLine.getCommandID())
        {
            case 0: //Waiting
                result = currentThread.continueWait(currentDeltaTime);
                break;
            case 51: //Moving... something only an Entity could do
                result = ((Entity)currentScriptable).continueMove(currentDeltaTime);
                break;
            
        }
        
        return result;
    }
    
    
    //Now, there are two types of commands - ones which occupy time
    //or ones which are executed immediately. When executeCommand goes
    //through and discovers what the command does, it will return a boolean
    //which indicates whether to continue to the next line. TRUE means 
    //"YES, we do continue" and FALSE means "FALSE, this is the last command!" 
    public boolean executeCommand(Line currentLine)
    {
        //By default it's true, unless the command is the type that makes it
        //false
        boolean continueExecuting = true;
        
        switch (currentLine.getCommandID())
        {
            //Remember, core functions are from 0-9. 
            case 0: //wait
                double thisLong = currentLine.getDoubleParameter(0);
                currentThread.beginWait(thisLong);
                continueExecuting = false;
                break;
            case 1: //GOTO, huh?
                Script currentScript = scr.getScriptAtID(currentThread.getScriptID());
                String theLabel = currentLine.getStringParameter(0);
                int newLineIndex = currentScript.getLabelIndexOnLineList(theLabel);
                currentThread.setLineNumber(newLineIndex);
                break;
            case 2:
                
                //We'll leave this out for now...
                break;
                
            case 7: //new Thread
                //newThread scriptID 
                
                int scriptID = (int)currentLine.getDoubleParameter(0);
                String scriptName = currentLine.getStringParameter(1);
                
                //Create a new thread with that scriptID, giving it scriptName
                Thread newThread = new Thread(scriptID);
                    newThread.setName(scriptName);
                    newThread.setLineNumber(0);
                    newThread.setRunningState(false);
                    newThread.setScriptable(currentScriptable);
                
                threadManager.addThread(newThread);
                break;
                
            case 8:
                //kill Thread.
                String targetThread = currentLine.getStringParameter(0);
                threadManager.markForDeletion(targetThread);
                break;
                
            //This thread is done
            case 9:
                currentThread.markForDeletion();
                //Ending the thread obviously means that you DON'T go to the next line
                continueExecuting = false;
                break;
            //The memory functions go here
               
            //createVariable variableType identifier (Optional value)
            case 10: //CreateVariable
                createVariable(currentLine);
                break;
                
            //setVariable identifier newValue
            case 11:
                
                
            //Print a variable, for debugging
            case 15:
                print(currentLine);
                break;
                
            //The manipulation of the locations of Displayables goes here    
            case 50:
                //Facing a direction
                System.out.println("Alright we don't have code for facing a direction yet.");
                break;
            case 51:
                //Moving
                System.out.println("Starting to walk....");
                double pixelsToWalk = currentLine.getDoubleParameter(0);
                ((Entity)currentScriptable).beginMove(pixelsToWalk);
                continueExecuting = false;
                break;
                
            
        }
        
        //Returns whether to continue loading more commands
        return continueExecuting;
    }
    
    
    
    
    /**************************************************************************
    ***************************************************************************
    ********************ALL THE INDIVIDUAL METHODS GO HERE*********************
    ***************************************************************************
    **************************************************************************/
    
    
    
    private void createVariable(Line currentLine)
    {

        //The name of the variable.
        String variableIdentifier = currentLine.getStringParameter(0);

        //System.out.println(variableIdentifier);

        //Check if the there is that third, optional value
        //Obviously, if that number is equal to or greater than 3, we
        //can access that third parameter
        int lineParameterCount = currentLine.getParameterCount();

        //System.out.println(lineParameterCount);
        if (lineParameterCount >= 2)
        {
            //So they decided to declare and initialize.
            Parameter initParameter = currentLine.getParameter(1);
            
            //Are they initializing from the literal, or the 
            //variable named by the literal?
            if (initParameter.isIdentifier())
            {
                //Is an identifier, so set the Variable equal to what
                //the initParameter refers to...
                Parameter referencedParam = 
                        currentScriptable.getVariable(initParameter.getStringValue());
                
                currentScriptable.setVariable(variableIdentifier,
                        referencedParam);
                
            }
            else 
            {
                //It's a literal.
                currentScriptable.setVariable(variableIdentifier,
                        initParameter);
            }
            
            
        }
        else 
        {
            //They decided to declare a variable without initialization
            currentScriptable.newVariable(variableIdentifier);
        }
    }
    
    private void print(Line currentLine)
    {
        Parameter toBePrinted = currentLine.getParameter(0);
        
        if (toBePrinted.isIdentifier())
        {
            //It prints what the identifier references
            Parameter message = currentScriptable.
                    getVariable(toBePrinted.getStringValue());
            System.out.println(message.toString());
            
        }
        else
        {
            //Instead, it prints the literal
            String message = toBePrinted.getStringValue();
            System.out.println(message);
        }
        
    }
    
    
    
    
}
