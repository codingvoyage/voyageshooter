package spaceinvaders;


import java.util.ArrayList;

/**
 *
 * @author Edmund
 */
public class ScriptManager {
    private Script[] scriptCollection;
    public final int SCRIPT_CAPACITY = 20;
    
    public ScriptManager() 
    {
        //Let's make space for 20 scripts
        scriptCollection = new Script[SCRIPT_CAPACITY];
        
        //Let's set each object to null
        for (int i = 0; i < SCRIPT_CAPACITY; i++) 
        {
            scriptCollection[i] = null;
        }
        
    }
    
    public boolean loadScript(String filename, int indexID) 
    {
        //We only want this function to work when there was no existing
        //script on the provided indexID. It should be null, in that case.
        if (scriptCollection[indexID] == null)
        {
            scriptCollection[indexID] = new Script(filename);
            return true;
        }
        
        //If there was one already, the user should have used the changeScript
        //function, so this one fails.
        return false;
    }
    
    
    public Script getScriptAtID(int scriptIDNumber) 
    {
        //Refers to the array, find the one with scriptIDnumber
        return scriptCollection[scriptIDNumber];
    }
    
    
}
