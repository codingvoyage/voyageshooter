package spaceinvaders;

/**
 *
 * @author Edmund
 */
public interface Scriptable {
    //Waiting
    public void beginWait(double millisecondsToWait);
    public boolean continueWait(double delta);
    
    //Memory manipulation
    public void newVariable(String identifier);
    public void setVariable(String identifier, Parameter value);
    public void deleteVariable(String identifier);
    public Parameter getVariable(String identifier);


}
