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
    
    private Scriptable currentScriptable;
    private double currentDeltaTime;
    
    public ScriptReader(ScriptManager scriptManagerHandle) 
    {
        //Initialize ScriptManager! rather, change the constructor such that
        //it can receive the object reference to ScriptManager, which probably
        //has been initialized sometime during the initialization stage of the
        //program
        scr = scriptManagerHandle;
        
    }
    
    public void act(Scriptable s, double deltaTime)
    {
        this.currentDeltaTime = deltaTime;
        this.currentScriptable = s;
        
        //We need to know what line of what script the Scriptable is on
        int currentLineNumber = currentScriptable.getLineNumber();
        int currentScriptID = currentScriptable.getScriptID();
        
        //Now we call ScriptManager to have it return a reference to the
        //Script class which currentScriptID refers to
        Script currentScript = scr.getScriptAtID(currentScriptID);
        
        //Now, we get the current line
        Line currentLine = currentScript.getLine(currentLineNumber);
        
        //Now, our response depends on whether there is a command already in 
        //execution
        if (currentScriptable.isRunning()) 
        {
            //The only two commands we have like this are
            //wait and move
            boolean doesContinue = continuesRunning(currentLine);
            if (!doesContinue) 
            {
                //So it's false. Do not continue running, move on
                //to the next line too!
                currentScriptable.setLineNumber(
                        currentScriptable.getLineNumber() + 1);
            }
        }
        
        //Now, if by now there is no command already in execution, then...
        if (!currentScriptable.isRunning()) 
        {
            System.out.println("Aha, there's nothing being executed so far...");
            //Now we keep going from line to line...
            boolean doWeStop = false;
            while (!doWeStop) 
            {
                //Execute the next command
               
                //First, get the current Line of the current Script
                Line thisLine = scr.getScriptAtID(currentScriptable.getScriptID()).
                        getLine(currentScriptable.getLineNumber());
                
                doWeStop = executeCommand(thisLine);
                
                //Now, if we aren't halting, then after the line is over, 
                //move on to the next line! UNLESS it was a goto statement!
                if (!doWeStop && (thisLine.getCommandID() != 1)) 
                {
                    System.out.println("Next line!");
                    currentScriptable.setLineNumber(
                        currentScriptable.getLineNumber() + 1);
                }
                else 
                {
                    
                }
                
            }
            
            
        }
        
        
        
        
    }
    
    //The boolean it returns indicates whether it should continue running
    private boolean continuesRunning(Line currentLine)
    {
        boolean result = true;
        
        switch (currentLine.getCommandID())
        {
            case 0: //Waiting
                result = currentScriptable.continueWait(currentDeltaTime);
                break;
            case 11: //Moving... something only an Entity could do
                result = ((Entity)currentScriptable).continueMove(currentDeltaTime);
                break;
            
        }
        
        return result;
    }
    
    public boolean executeCommand(Line currentLine)
    {
        boolean stopAfterThis = false;
        
        switch (currentLine.getCommandID())
        {
            case 0:
                double thisLong = currentLine.getDoubleParameter(0);
                
                System.out.println("Let's start waiting for " + thisLong + " milliseconds!!");
                currentScriptable.beginWait(thisLong);
                stopAfterThis = true;
                break;
            case 1: //GOTO, huh?
                int newLine = (int)currentLine.getDoubleParameter(0);
                
                currentScriptable.setLineNumber(newLine);
                break;
            case 2:
                
                //We'll leave this out for now...
                break;
            case 10:
                //Facing a direction
                System.out.println("Alright we don't have code for facing a direction yet.");
                break;
            case 11:
                System.out.println("Starting to walk....");
                double pixelsToWalk = currentLine.getDoubleParameter(0);
                ((Entity)currentScriptable).beginMove(pixelsToWalk);
                stopAfterThis = true;
                //Moving
                break;
                
            
        }
        return stopAfterThis;
    }
    
}
