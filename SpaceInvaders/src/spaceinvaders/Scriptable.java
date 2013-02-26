package spaceinvaders;

/**
 *
 * @author Edmund
 */
public interface Scriptable {
    //Line number on Script
    public int getLineNumber();
    public void setLineNumber(int newLineNumber);
    
    //Which script on the ScriptManager class's array?
    public int getScriptID();
    
    //Progress state
    public boolean isRunning();
    
    //Waiting
    public void beginWait(double millisecondsToWait);
    public boolean continueWait(double delta);
    
    //Memory manipulation
    public void newVariable(String identifier);
    public void setVariable(String identifier, Parameter value);
    public void deleteVariable(String identifier);
    public Parameter getVariable(String identifier);


}
