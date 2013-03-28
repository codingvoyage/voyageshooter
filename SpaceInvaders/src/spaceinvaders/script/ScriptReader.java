package spaceinvaders.script;

import java.util.HashMap;
import java.util.ArrayList;

import spaceinvaders.entity.*;


/**
 *
 * @author Edmund
 */

public class ScriptReader
{
    private ScriptManager scr;
    private ThreadManager threadManager;
    private EntityGroup entities;
    
    //These will get changed every time act(Scriptable s, double deltaTime)
    //is called. It makes it convenient since now the ScriptReader methods
    //do not have to be passed this every time.
    private Scriptable currentScriptable;
    private Thread currentThread;
    private double currentDeltaTime;
    
    private boolean justJumped;
    
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
    
    public void setEntityHandle(EntityGroup entities)
    {
        this.entities = entities;
    }
    
    public void act(Thread t, double deltaTime)
    {
        currentDeltaTime = deltaTime;
        currentThread = t;
        
        //Get Scriptable Object
        currentScriptable = currentThread.getScriptable();
        
        
        //We need to know what line of what script the Thread is on
        int currentLineNumber = currentThread.getCurrentLine();
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
                        currentThread.getCurrentLine() + 1);
            }
        }
        
        //Now, if by now there is no command already in execution, then...
        if (!currentThread.isRunning()) 
        {
            
            
            
            //We will go from line to line
            boolean doWeContinue = true;
            while (doWeContinue) 
            {
                justJumped = false;
                        
                //First, get the current Line of the current Script
                //This is kind of a mouthful, so first it gets the ScriptID
                //from the class implementing Scriptable, and then grabs from
                //the ScriptManager, using that scriptID, the Script object
                //itself, and then finally, from that Script object the program
                //retrieves the line which is located at the line number
                //specified in the class implementing Scriptable.
                Line thisLine = scr.getScriptAtID(currentThread.getScriptID()).
                        getLine(currentThread.getCurrentLine());
                
                //Now that we have the line, we execute it.
                doWeContinue = executeCommand(thisLine);
                
                
               // if  (!currentThread.functionStack.isEmpty())
               //     System.out.println(currentThread.getCurrentLine() + " is the current line"
               //             + "of thread " + currentThread.getName());
                    
                //Now, if we aren't halting, then after the line is over, 
                //move on to the next line! UNLESS it was a goto statement!
                //Of course, if it is a goto statement, it will remain a
                //"Yes we continue!" but without switching lines
                if (doWeContinue && (justJumped == false)) 
                {
                    //System.out.println("Next line!");
                    currentThread.setLineNumber(
                        currentThread.getCurrentLine() + 1);
                
                
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
                result = ((MovableEntity)currentScriptable).continueMove(currentDeltaTime);
                break;
            case 53:
                System.out.println("continuing le orbitee");
                result = ((MovableEntity)currentScriptable).continueOrbit(currentDeltaTime);
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
                
                justJumped = true;
                
                
                break;
            case 2:
                //System.out.println(currentThread.getCurrentLine() + " is the current line "
                //            + "of the current thread we're on... which is " + currentThread.getName());
                //We'll leave this out for now...
                break;
                
            case 6:
                scr.loadScript(identifierCheck(currentLine, 0).getStringValue(),
                        (int)identifierCheck(currentLine, 1).getDoubleValue());
                break;
                
            case 7: //new Thread
                //newThread scriptID 
                createNewThread(currentLine);
                break;
                
            //killThread by marking the target thread for deletion.
            case 8:
                String targetThread = currentLine.getStringParameter(0);
                threadManager.markForDeletion(targetThread);
                break;
                
            //This thread is done
            case 9:
                currentThread.markForDeletion();
                //Ending the thread obviously means that you DON'T go to the next line
                continueExecuting = false;
                break;
               
            //createVariable variableType identifier (Optional value)
            case 10:
                createVariable(currentLine);
                break;
                
            //setVariable identifier newValue
            case 11:
                setVariable(currentLine);
                break;
               
            //if statement
            case 12:
                //Evaluate line
                Parameter result = evaluateExpression(currentLine,
                        0, currentLine.getParameterCount() - 1);
                
                //if it evaluates to true, then program logic continues
                //that's why we check if it's false, and then if it's false,
                //we look for where we skip to.
                if (result.getBooleanValue() == false)
                {
                    //findEndLimiter starts on the indexed line, so that's why we 
                    //compensate by adding 1.
                    int newLine = findEndLimiter(scr.getScriptAtID(currentThread.getScriptID()),
                            "if", "endif", currentThread.getCurrentLine() + 1, 1);
                    
                    currentThread.setLineNumber(newLine + 1);
                    justJumped = true;
                }
                break;
                
            case 13:
                
                break;
            
                
            case 15:
                //Evaluate line
                Parameter whileResult = evaluateExpression(currentLine,
                        0, currentLine.getParameterCount() - 1);
                
                //if it evaluates to true, then program logic continues
                //that's why we check if it's false, and then if it's false,
                //we look for where we skip to.
                if (whileResult.getBooleanValue() == false)
                {
                    //findEndLimiter starts on the indexed line, so that's why we 
                    //compensate by adding 1.
                    int newLine = findEndLimiter(scr.getScriptAtID(currentThread.getScriptID()),
                            "while", "wend", currentThread.getCurrentLine() + 1, 1);
                    
                    currentThread.setLineNumber(newLine + 1);
                    justJumped = true;
                }
                break;
                
            case 16:
                //wend
                int newLine = findEndLimiter(scr.getScriptAtID(currentThread.getScriptID()),
                            "wend", "while", currentThread.getCurrentLine() - 1, -1);
                currentThread.setLineNumber(newLine);
                justJumped = true;
                
                break;
                
            case 17:
                //for ___ = ___ and BLAHBLAH
                
                //First, do the declaration and initialization
                String varName = currentLine.getStringParameter(0);
                
                int equalsLoc = findCorrespondingBracket(currentLine, "=", 0, 1);
                int andLoc = findCorrespondingBracket(currentLine, "and", 0, 1);
                
                if (andLoc - 2 == equalsLoc)
                {
                    //Then it's a simple initialization at index 2
                    Parameter varValue = identifierCheck(currentLine, 2);
                    currentThread.setVariable(varName, varValue);
                }
                else
                {
                    //Alright, Goddamn. The scripter decided to actually have
                    //an expression in the for loop initialization. Seriously?
                    Parameter varValue = evaluateExpression(currentLine, equalsLoc + 1, andLoc - 1);
                    currentThread.setVariable(varName, varValue);
                    
                }
                
                //Next, check if we have to run the for loop at all.
                Parameter checkResult = evaluateExpression(currentLine, andLoc + 1, currentLine.getParameterCount() - 1);
                if (checkResult.getBooleanValue() == false)
                {
                    //We skip past the next...
                    int skippingNext = findEndLimiter(scr.getScriptAtID(currentThread.getScriptID()),
                            "for", "next", currentThread.getCurrentLine() + 1, 1);
                    
                    currentThread.setLineNumber(skippingNext + 1);
                    
                    justJumped = true;
                }
                //Otherwise, we're really just fine. We'll go along with the for loop
                
                break;
                
            case 18:
                //next blah blah burp herp blah --> blah
                
                //That's actually it. evaluate it.
                evaluate(currentLine);
                
                //Used a lot in next calculations
                Script currentScriptObj = scr.getScriptAtID(currentThread.getScriptID());
                
                //Get location of the last for tag
                int forLocation = findEndLimiter(currentScriptObj,
                        "next", "for", currentThread.getCurrentLine() -1, -1);
                
                //We want to evaluate that expression again to see if the condition
                //is still satisfied
                
                int andLocation = findCorrespondingBracket(currentScriptObj.getLine(forLocation),
                        "and", 0, 1);
                
                Parameter doWeKeepGoing = evaluateExpression(
                        currentScriptObj.getLine(forLocation),
                        andLocation + 1, 
                        currentScriptObj.getLine(forLocation).getParameterCount() - 1);
                
                if (doWeKeepGoing.getBooleanValue() == true)
                {
                    //Then we will set the line to the one right in front of the for
                    //tag
                    currentThread.setLineNumber(forLocation + 1);
                    justJumped = true;
                }
                else
                {
                    //Alright, we're done. We do nothing, and we let the act()
                    //carry us to the line after the "next"
                }
                
                break;
                
            case 19:
                callFunction(currentLine);
                break;
                
            //This is like calling a static function.
            case 20:
                callScriptFunction(currentLine);
                break;
                
            //Case 21 works with calling the function
            //located in another thread.
            case 21:
                callThreadFunction(currentLine);
                break;
                
            //The return statement. Returns the thread
            //to its previous layer.
            case 25:
                returnFromFunction(currentLine);
                break;
                
            //Print a variable, for debugging
            case 30:
                print(currentLine);
                break;
                
            case 31:
                evaluate(currentLine);
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
                ((MovableEntity)currentScriptable).beginMove(pixelsToWalk);
                continueExecuting = false;
                break;
                
            case 52:
                //SPAWN
                //Spawn entityType theNameWeGiveIT optionalx optionaly
                String entityTypeName = currentLine.getStringParameter(0);
                String nameID = currentLine.getStringParameter(1);
                int xLoc = (int)currentLine.getDoubleParameter(2);
                int yLoc = (int)currentLine.getDoubleParameter(3);
                Entity spawnedEntity = entities.spawn(entityTypeName, nameID, xLoc, yLoc);
                
                //Now, load the thread
                int mainScriptID = spawnedEntity.getMainScriptID();
                System.out.println("we now create a thread from script ID # " + mainScriptID);
                
                //I'm just going to name the Thread the same as the Entity
                Thread spawnedEntityThread = this.createNewThread(mainScriptID, nameID);
                
                //Now the entity must know the thread, and vice versa
                spawnedEntity.setMainThread(spawnedEntityThread);
                spawnedEntityThread.setScriptable(spawnedEntity);
                
                //Add thread to collection
                threadManager.addThread(spawnedEntityThread);
                
                
                break;
                
            case 53:
                //ORBIT
                double angle = currentLine.getDoubleParameter(0);
                double radius = currentLine.getDoubleParameter(1);
                //0 is CW, else CCW
                double direction = currentLine.getDoubleParameter(2);
                    ((MovableEntity)currentScriptable).beginOrbit(angle, radius, direction);
                    
                continueExecuting = false;
                
                System.out.println("STARTING ORBIT");
                
                break;
                
                
            case 55:
                double newx = identifierCheck(currentLine, 0).getDoubleValue();
                double newy = identifierCheck(currentLine, 1).getDoubleValue();
                ((Entity)currentScriptable).place((float)newx, (float)newy);
                break;
                
                
            case 60:
                //new velocity
                double newvx = currentLine.getDoubleParameter(0);
                double newvy = currentLine.getDoubleParameter(1);
                ((MovableEntity)currentScriptable).setVelocity(newvx, newvy);
                break;
                
            case 80:
                getSystemMilliTime(currentLine);
                break;
            case 81:
                getSystemNanoTime(currentLine);
                break;
            case 82:
                double min = identifierCheck(currentLine, 0).getDoubleValue();
                double max = identifierCheck(currentLine, 1).getDoubleValue();
                double randomNumber = min + (int)(Math.random() * ((max - min) + 1));
                String identifier = currentLine.getStringParameter(3);
                currentThread.setVariable(identifier,
                            new Parameter(randomNumber));
                break;
            
        }
        
        //Returns whether to continue loading more commands
        return continueExecuting;
    }
    
    
    public void getSystemMilliTime(Line currentLine)
    {
        String variableIdentifier = currentLine.getStringParameter(0);
        Parameter referencedParam = new Parameter(System.currentTimeMillis());
        
        
        currentThread.setVariable(variableIdentifier,
                        referencedParam);
    }
    
     public void getSystemNanoTime(Line currentLine)
    {
        String variableIdentifier = currentLine.getStringParameter(0);
        Parameter referencedParam = new Parameter(System.nanoTime());
        
        currentThread.setVariable(variableIdentifier,
                        referencedParam);
    }
    
    /**************************************************************************
    ***************************************************************************
    ********************ALL THE INDIVIDUAL METHODS GO HERE*********************
    ***************************************************************************
    **************************************************************************/
    
     
    private Parameter identifierCheck(Parameter iDunno)
    {
        if (iDunno.isIdentifier())
        {
            return currentThread.getVariable(iDunno.getStringValue());
        }
        return iDunno;
    }
    
    private Parameter identifierCheck(Line currentLine, int indexOnLine)
    {
        Parameter iDunno = currentLine.getParameter(indexOnLine);
        return identifierCheck(iDunno);
    }
    
    private void createVariable(Line currentLine)
    {

        //The name of the variable.
        String variableIdentifier = currentLine.getStringParameter(0);

        //We're trying to check if we're initializing the variable
        //as well as declaring it
        if (currentLine.getParameterCount() >= 2)
        {
            //So they decided to declare and initialize.
            Parameter initParameter = currentLine.getParameter(1);
            
            //Now set variableIdentifier to either the literal or to the
            //value pointed to by the identifier
            currentThread.setVariable(variableIdentifier,
                    identifierCheck(currentLine, 1));
            
        }
        else 
        {
            //They decided to declare a variable without initialization
            currentThread.newVariable(variableIdentifier);
        }
    }
    
    private void setVariable(Line currentLine)
    {
        String variableIdentifier = currentLine.getStringParameter(0);
        
        currentThread.setVariable(variableIdentifier,
                    identifierCheck(currentLine, 1));
        
    }
    
    private void print(Line currentLine)
    {
        Parameter toBePrinted = currentLine.getParameter(0);
            
        //We might evaluate something before printing the result
        if (toBePrinted.getStoredType() == Parameter.STRING
                && toBePrinted.getStringValue().equals("<--"))
        {
            Parameter result = evaluateExpression(currentLine, 1, currentLine.getParameterCount() - 1);
            System.out.println(result.toString());
        }
        else
        {
            Parameter message = identifierCheck(toBePrinted);
            System.out.println(message.toString());
        }
        
    }               
    
    private void createNewThread(Line currentLine) 
    {
        //Extract from currentLine...
        int scriptID = (int)currentLine.getDoubleParameter(0);
        String threadName = currentLine.getStringParameter(1);

        //Create a new thread with that scriptID, giving it scriptName
        Thread newThread = createNewThread(scriptID, threadName);

        //Now add it to the global Thread list.
        threadManager.addThread(newThread);
    }
    
    private Thread createNewThread(int scriptID, String threadName) 
    {
        //Create a new thread with that scriptID, giving it scriptName
        Thread newThread = new Thread(scriptID);
            newThread.setName(threadName);
            newThread.setLineNumber(0);
            newThread.setRunningState(false);
            newThread.setScriptable(currentScriptable);

        //for the convenience of certain functions
        return newThread;
    }
    
    private Object[] formatFunctionLine(Line currentLine, Line functionLine, int searchIndexOnLine)
    {
        //We will act differently if searchIndexOnLine is a 1, since that means
        //we're doing callfunction and we need to adjust our indices
        boolean doingCallFunction = (searchIndexOnLine == 1);
        
        ArrayList<String> returnKeys = new ArrayList<String>();
        HashMap<String, Parameter> newMemoryBox = new HashMap<String, Parameter>();
       
        if (currentLine.getParameterCount() <= 2)
        {
            //In this case, don't do anything 
        }
        else 
        {
            boolean isArrowReached = false;
            int searchIndex = searchIndexOnLine;
            while (searchIndex < currentLine.getParameterCount())
            {
                //Our current Parameter at searchIndex
                Parameter currentParameter = currentLine.getParameter(searchIndex);
                
                //See if the currently indexed thing is a -->
                if ( (currentParameter.getStoredType() == 1) &&
                    (currentParameter.getStringValue().equals("-->")))
                {
                    //If so, then now we have reached the arrow
                    isArrowReached = true;
                    
                    //We go on to next parameter
                }
                else
                {
                    //So we have reached something meaningful. Now, our
                    //response depends on whether the arrow has been reached yet
                    
                    if (isArrowReached)
                    {
                        //So it's a return, so add the Parameter's name to the
                        //return thing
                        String nameOfReturn = currentLine.getParameter(searchIndex).getStringValue();
                        returnKeys.add(nameOfReturn);
                    }
                    else
                    {
                        //We are adding to the memorybox
                        String ourIdentifier;
                                
                        //Get the name that the variable will be referred as
                        if (doingCallFunction)
                        {
                            
                            ourIdentifier = functionLine.getStringParameter(searchIndex);
                        }
                        else 
                        {
                            ourIdentifier = functionLine.getStringParameter(searchIndex - 1);
                        }
                        
                        //But hold on a second. currentParameter could be a literal, or it
                        //could be an identifier to something else. Use identifierCheck
                        Parameter checkedParam = identifierCheck(currentParameter);
                        newMemoryBox.put(ourIdentifier, checkedParam);
                        
                    }
                }
                //Increment searchIndex
                searchIndex++;
            }
        }
        
        Object[] returnArray = new Object[2];
        returnArray[0] = returnKeys;
        returnArray[1] = newMemoryBox;
        return returnArray;
    }
    
    
    
    //callFunction [act] param1 param2 param3 --> returned1 returned2
    private void callScriptFunction(Line currentLine)
    {
        //callFunction 5 [act] param1 param2 param3 --> returned1 returned2
        
        //Get the Script object that 5 refers to
        int scriptID = currentLine.getIntegerParameter(0);
        Script jumpedScript = scr.getScriptAtID(scriptID);
        
        //Basically, extracting the label [that'smyshit]
        String labelName = currentLine.getStringParameter(1);
        
        //Now find the line number that [that'smyshit] is located on
        int lineNumber = jumpedScript.getLabelIndexOnLineList(labelName);
        
        //But BEFORE setting the currentplace to that line, first store the
        //old script ID and old line number for returning purposes
        currentThread.makeReturnPoint();
        
        //Set the current place to that line, that script
        currentThread.setLineNumber(lineNumber);
        currentThread.setScriptID(scriptID);
        
        //But here's the thing. We need to know what to make its identifier.
        //In order to do that, we need to go all the way to the line we're jumping
        //to and getting a copy of their line object.
        Line functionLine = jumpedScript.getLineAtLabel(labelName);
        //function [nameblah] param1identifier param2identifier ...
        
        Object[] formattedLineData = formatFunctionLine(currentLine, functionLine, 2);
        
        ArrayList<String> returnKeys = (ArrayList<String>)formattedLineData[0];
        HashMap<String, Parameter> newMemoryBox = (HashMap<String, Parameter>)formattedLineData[1];
        
        
        //Now convert returnKeys to an array
        String[] returnKeyArray = new String[returnKeys.size()];
        returnKeys.toArray(returnKeyArray);
        
        currentThread.setFunctionReturns(returnKeyArray);
        currentThread.setMemoryBox(new HashMap<String, Parameter>());
        currentThread.setLocalMemoryBox(newMemoryBox);
        currentThread.increaseFunctionLayer();
        
        
    }
    
    private void callThreadFunction(Line currentLine)
    {
        //callFunction "threadname" [act] param1 param2 ... --> returned1 returned2 ...
       
        //Get the Thread object which threadname refers to
        String threadName = currentLine.getStringParameter(0);
        Thread jumpedThread = threadManager.getThreadAtName(threadName);
    
        HashMap<String, Parameter> jumpedBox = jumpedThread.getMemoryBox();
        
        
        //Basically, extracting the label [act]
        String labelName = currentLine.getStringParameter(1);
        
        //Alright, this gets complicated.
        //Thread --> what is its first script ID number?
        //Go find that Script object at the scriptID
        //Get the label index
        int threadScriptID = jumpedThread.baseScriptID;
        //System.out.println(threadScriptID + " is the ID of the thread I'm jumping TO");
        
        Script jumpedScript = scr.getScriptAtID(threadScriptID);
        int newLine = jumpedScript.getLabelIndexOnLineList(labelName);
        //System.out.println("I am jumping onto line " + newLine);
        
        //But BEFORE setting the currentplace to that line, first store the
        //old script ID and old line number for returning purposes
        currentThread.makeReturnPoint();
        
        //Set the current place to that line, that script
        currentThread.setLineNumber(newLine);
        currentThread.setScriptID(threadScriptID);
        
        //But here's the thing. We need to know what to make its identifier.
        //In order to do that, we need to go all the way to the line we're jumping
        //to and getting a copy of their line object.
        Line functionLine = jumpedScript.getLineAtLabel(labelName);
        //function [nameblah] param1identifier param2identifier ...
        
        Object[] formattedLineData = formatFunctionLine(currentLine, functionLine, 2);
        
        ArrayList<String> returnKeys = (ArrayList<String>)formattedLineData[0];
        HashMap<String, Parameter> newMemoryBox = (HashMap<String, Parameter>)formattedLineData[1];
        
        
        //System.out.println("Local Memorybox is size" + newMemoryBox.size());
        
        //Now convert returnKeys to an array
        String[] returnKeyArray = new String[returnKeys.size()];
        returnKeys.toArray(returnKeyArray);
        
        currentThread.setFunctionReturns(returnKeyArray);
        currentThread.setMemoryBox(jumpedBox);
        currentThread.setLocalMemoryBox(newMemoryBox);
        currentThread.increaseFunctionLayer();
        
    }
    
    //The difference is that this callFunction is for the SAME script file
    //I basically just took the more advanced callThreadFunction and
    //modified it for this
    private void callFunction(Line currentLine)
    {
        //callFunction [act] param1 param2 ... --> returned1 returned2 ...
        
        //The script object we are working with
        Script thisScript = scr.getScriptAtID(currentThread.getScriptID());
        
        
        //Basically, extracting the label [act] and finding out where it is
        String labelName = currentLine.getStringParameter(0);
        int newLine = thisScript.getLabelIndexOnLineList(labelName);
        
        //But BEFORE setting the currentplace to that line, first store the
        //old script ID and old line number for returning purposes
        currentThread.makeReturnPoint();
        
        //Set the current place to that line, that script
        currentThread.setLineNumber(newLine);
        currentThread.setScriptID(currentThread.getScriptID());
        
        //But here's the thing. We need to know what to make its identifier.
        //In order to do that, we need to go all the way to the line we're jumping
        //to and getting a copy of their line object.
        Line functionLine = thisScript.getLineAtLabel(labelName);
        
        Object[] formattedLineData = formatFunctionLine(currentLine, functionLine, 1);
        
        ArrayList<String> returnKeys = (ArrayList<String>)formattedLineData[0];
        HashMap<String, Parameter> newMemoryBox = (HashMap<String, Parameter>)formattedLineData[1];
        
        
        //Now convert returnKeys to an array
        String[] returnKeyArray = new String[returnKeys.size()];
        returnKeys.toArray(returnKeyArray);
        
        //Make all settings
        currentThread.setFunctionReturns(returnKeyArray);
        currentThread.setMemoryBox(currentThread.getMemoryBox());
        currentThread.setLocalMemoryBox(newMemoryBox);
        currentThread.increaseFunctionLayer();
        
    }
    
    //return val1 param2 val3
    private void returnFromFunction(Line currentLine)
    {
        //Remember how we passed the returned variables' names?
        //Now we retrieve them
        String[] returnKeys = currentThread.getFunctionReturns();

        //Before restoreLastReturnPoint(), retain the values which
        //need to be retained
        Parameter[] parameters = new Parameter[returnKeys.length];
        for (int i = 0; i < returnKeys.length; i++)
        {
            Parameter currentParam = currentLine.getParameter(i);
            
            //A literal, or a variable?
            parameters[i] = identifierCheck(currentLine, i);
        }
        
        //Return, and also decrease the function layer
        currentThread.restoreLastReturnPoint();
        currentThread.decreaseFunctionLayer();
        
        //Now add those retained variables to the current thread
        //layer's memory.
        for (int i = 0; i < returnKeys.length; i++)
        {
            currentThread.setVariable(returnKeys[i], parameters[i]);
        }
        
    }
    
    public void evaluate(Line currentLine)
    {
        Parameter result = evaluateExpression(currentLine, 0, currentLine.getParameterCount() - 3);
        
        currentThread.setVariable(
                currentLine.getStringParameter(currentLine.getParameterCount() - 1),
                result);
    }
    
    public Parameter evaluateExpression(Line l, int front, int back) 
    {
        //We are at our base case if _ _ _ front is two less than back
        if (front == back - 2)
        {
            return simpleEvaluate(l, front, back);
        }
        
        //Alright, NOT at our base case. Continue splitting expression
        //up into separate expressions
        
        //Get the thing at the first indexed Parameter
        Parameter firstParam = l.getParameter(front);
        
        Parameter resultOnLeft;
        Parameter resultOnRight;
        
        //If it is a "["...
        if (firstParam.getStoredType() == Parameter.STRING
                && firstParam.getStringValue().equals("["))
        {
            
            //Find the corresponding end bracket
            int paramEnd = findCorrespondingBracket(l, "]", "[", front + 1, 1);
            
            //Evaluate that expression
            resultOnLeft = evaluateExpression(l, front + 1, paramEnd - 1);
            
            //Alright, get the opCode...
            Parameter opCode = l.getParameter(paramEnd + 1);
            
            //Now perform the same "is it a [" check for the one after that...
            if (l.getParameter(paramEnd + 2).getStoredType() == Parameter.STRING 
                && l.getParameter(paramEnd + 2).getStringValue().equals("["))
            {
                int secondParamEnd = findCorrespondingBracket(l, "]", "[", paramEnd + 3, 1);
                
                resultOnRight = evaluateExpression(l, paramEnd + 3, secondParamEnd - 1);
            }
            else
            {
                //It's simply that.
                resultOnRight = l.getParameter(paramEnd + 2);
            }
            
            return simpleEvaluate(resultOnLeft, opCode, resultOnRight);
            
        }
        else
        {
            //Alright, this is not a "["...
            //So on the left side of the equation is in fact the first parameter
            resultOnLeft = firstParam;
            
            //A opcode comes right after.
            Parameter opCode = l.getParameter(front + 1);
            
            //There must be an open bracket after, so find the close-bracket
            //which corresponds to it, then do recursion
            int secondParamEnd = findCorrespondingBracket(l, "]", "[", front + 3, 1);
            resultOnRight = evaluateExpression(l, front + 3, secondParamEnd - 1);
            
            //Now that we have all components, evaluate normally.
            return simpleEvaluate(resultOnLeft, opCode, resultOnRight);
            
        }
        
    }
    
    public int findCorrespondingBracket(Line l, String targetBracket, int currentSearchLoc, int stepDirection)
    {
        boolean found = false;
        int index = currentSearchLoc;
        int totalParameters = l.getParameterCount();
        
        //Keep going if we haven't found it, and we haven't reached the last one 
        //already OR overshot the beginning
        while (!found && (index < totalParameters || index >= 0))
        {
            //Ignore if it isn't a string.
            if (l.getParameterType(index) == Parameter.STRING)
            {
                if (l.getStringParameter(index).equals(targetBracket))
                {
                    return index;
                }
            }
            
            //Don't forget
            index += stepDirection;
        }
        
        //Done goof'd
        return -1;
    }
    
    public int findCorrespondingBracket(Line l, String targetBracket, String ignoredBracket, int currentSearchLoc, int stepDirection)
    {
        boolean found = false;
        
        int additionalLayers = 0;
        int index = currentSearchLoc;
        int totalParameters = l.getParameterCount();
        
        //Keep going if we haven't found it, and we haven't reached the last one 
        //already OR overshot the beginning
        while (!found && (index < totalParameters || index >= 0))
        {
            //Ignore if it isn't a string.
            if (l.getParameterType(index) == Parameter.STRING)
            {
                if (l.getStringParameter(index).equals(ignoredBracket))
                {
                    //Alright, one more to go then
                    additionalLayers++;
                } 
                else if (l.getStringParameter(index).equals(targetBracket))
                {
                    //If we found a "]" then we have to see
                    //if we have more to go first.
                    if (additionalLayers > 0)
                    {
                        additionalLayers--;
                    }
                    else
                    {
                        //Alright, now we found it for good.
                        return index;
                    }
                }
            }
            
            //Don't forget
            index += stepDirection;
        }
        
        return -1; //We failed.
    }
    
    public int findEndLimiter(Script currentScript, String openingLimiter, String closingLimiter, int start, int stepDirection)
    {
        boolean found = false;
        int additionalLayers = 0;
        int index = start;
        
        int totalParameters = currentScript.getLineCount();
        int openingCommandID = currentScript.findCommandID(openingLimiter);
        int closingCommandID = currentScript.findCommandID(closingLimiter);
        
        //Keep going if we haven't found it, and we haven't reached the last one 
        //already OR overshot the beginning
        while (!found && (index < totalParameters || index >= 0))
        {
            int currentCommandID = currentScript.getLine(index).getCommandID();
            
            if (currentCommandID == openingCommandID)
            {
                //Alright, one more to go then
                additionalLayers++;
            } 
            else if (currentCommandID == closingCommandID)
            {
                //If we found one of them, then we have to see
                //if we have more to go first.
                if (additionalLayers > 0)
                {
                    additionalLayers--;
                }
                else
                {
                    //Alright, now we found it for good.
                    return index;
                }
            }
            
            //Don't forget
            index += stepDirection;
        }
        
        return -1; //We failed somehow if we reached this
    }
    
    public Parameter simpleEvaluate(Parameter p1, Parameter opCode, Parameter p2)
    {
        //If either of the Parameters are identifiers, then load their
        //identified value and replace them.
        p1 = identifierCheck(p1);
        p2 = identifierCheck(p2);
        
        String opCodeName = opCode.getStringValue();
        
        //What we will return
        Parameter result;
        
        if (p1.getStoredType() == Parameter.BOOLEAN)
        {
            if (opCodeName.equals("&&"))
            {
                result = new Parameter(p1.getBooleanValue() && p2.getBooleanValue());
                return result;
            }
            else if (opCodeName.equals("||"))
            {
                result = new Parameter(p1.getBooleanValue() || p2.getBooleanValue());
                return result;
            }
        }
        
        if (opCodeName.equals("+"))
        {
            //Hold on, this may be a string...
            if (p1.getStoredType() == Parameter.STRING || p2.getStoredType() == Parameter.STRING)
            {
                result = new Parameter(p1.toString() + p2.toString());
            }
            else 
            {
                //Oh, so it's just a normal double we're adding
                result = new Parameter(p1.getDoubleValue() + p2.getDoubleValue());
            }
            
            return result;
        }   
        else if (opCodeName.equals("-"))
        {
            result = new Parameter(p1.getDoubleValue() - p2.getDoubleValue());
            return result;
        }
        else if (opCodeName.equals("*"))
        {
            result = new Parameter(p1.getDoubleValue() * p2.getDoubleValue());
            return result;
        }
        else if (opCodeName.equals("/"))
        {
            result = new Parameter(p1.getDoubleValue() / p2.getDoubleValue());
            return result;
        }
        else if (opCodeName.equals("%"))
        {
            result = new Parameter(p1.getDoubleValue() % p2.getDoubleValue());
            return result;
        }
        
        //Alright, now that we've eliminated the option of 
        //doing addition, ect, we're doing comparisons.
        if (opCodeName.equals("=="))
        {
            if (p1.getStoredType() == 1)
            {
                //Then we are comparing Strings
                result = new Parameter(p1.getStringValue().equals(p2.getStringValue()));
                return result;
            }
            if (p1.getStoredType() == 2)
            {
                //Then we are comparing booleans
                result = new Parameter(p1.getBooleanValue() == p2.getBooleanValue());
                return result;
            }
            if (p1.getStoredType() == 3)
            {
                //Then we are comparing doubles
                result = new Parameter(p1.getDoubleValue() == p2.getDoubleValue());
                return result;
            }
        }
        if (opCodeName.equals("!="))
        {
            if (p1.getStoredType() == 1)
            {
                //Then we are comparing Strings
                result = new Parameter(!p1.getStringValue().equals(p2.getStringValue()));
                return result;
            }
            if (p1.getStoredType() == 2)
            {
                //Then we are comparing booleans
                result = new Parameter(p1.getBooleanValue() != p2.getBooleanValue());
                return result;
            }
            if (p1.getStoredType() == 3)
            {
                //Then we are comparing doubles
                result = new Parameter(p1.getDoubleValue() != p2.getDoubleValue());
                return result;
            }
        }
        else if (opCodeName.equals("<"))
        {
            //We can only be comparing doubles
            result = new Parameter(p1.getDoubleValue() < p2.getDoubleValue());
            return result;
        }
        else if (opCodeName.equals("<="))
        {
            //We can only be comparing doubles
            result = new Parameter(p1.getDoubleValue() <= p2.getDoubleValue());
            return result;
        }
        else if (opCodeName.equals(">"))
        {
            result = new Parameter(p1.getDoubleValue() > p2.getDoubleValue());
            return result;
        }
        else if (opCodeName.equals(">="))
        {
            result = new Parameter(p1.getDoubleValue() >= p2.getDoubleValue());
            return result;
        }

        //Crash the program on purpose now
        int bomb = 0;
        System.out.println("DROPPING THE NUKE");
        return new Parameter(1/bomb);
        
    }
    
    public Parameter simpleEvaluate(Line l, int front, int back)
    {
        //We expect it to be in format __ __ __
        Parameter p1 = l.getParameter(front);
        Parameter p2 = l.getParameter(back);
        
        //Get the name of the operation
        Parameter opCode = l.getParameter(front + 1);
        
        return simpleEvaluate(p1, opCode, p2);
        
    }
    
}