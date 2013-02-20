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
    ScriptManager scr;
    
    public ScriptReader() 
    {
        //Initialize ScriptManager! rather, change the constructor such that
        //it can receive the object reference to ScriptManager, which probably
        //has been initialized sometime during the initialization stage of the
        //program
        
    }
    
    public void act(Scriptable s)
    {
        //We need to know what line of what script the Scriptable is on
        int currentLineNumber = s.getLineNumber();
        int currentScriptID = s.getScriptID();
        
        //Now we call ScriptManager to have it return a reference to the
        //Script class which currentScriptID refers to
        Script currentScript = scr.getScriptAtID(currentScriptID);
        
        //Now, we get the current line
        Line currentLine = currentScript.getLine(currentLineNumber);
        
        //Now, our response depends on whether 
        
        
    }
    
    
    
    
}
