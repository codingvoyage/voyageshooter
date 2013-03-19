package spaceinvaders.script;

import java.util.HashMap;

/**
 *
 * @author Edmund
 */
public class ScriptableClass implements Scriptable {
    
    protected Thread mainThread;
    
    //Basically, a catch all variable usable by the current method 
    private Parameter progressTemp;
    
    //Holds the variables declared by the Scriptable in the scripting engine
    private HashMap<String, Parameter> memoryBox;
    
    
    public ScriptableClass(int newScriptID) 
    {
        mainThread.setScriptID(newScriptID);
        mainThread.setLineNumber(0);
        mainThread.setRunningState(false);
        
        memoryBox = new HashMap<String, Parameter>();
    }
    
    
    public ScriptableClass() 
    {
        memoryBox = new HashMap<String, Parameter>();
    }
    
    public void setMainThread(Thread t)
    {
        mainThread = t;
    }
    
    public Thread getMainThread()
    {
        return mainThread;
    }
    
    //Accessors and mutators for the temporary variable
    protected void setTemporaryParameter(Parameter newParameter) 
    {
        progressTemp = newParameter;
    }
    
    protected Parameter getTemporaryParameter() 
    {
        return progressTemp;
    }
    
}
