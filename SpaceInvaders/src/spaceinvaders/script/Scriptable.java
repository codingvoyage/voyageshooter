package spaceinvaders.script;

/**
 *
 * @author Edmund
 */
public interface Scriptable {
    
    //Memory manipulation
    public void newVariable(String identifier);
    public void setVariable(String identifier, Parameter value);
    public void deleteVariable(String identifier);
    public Parameter getVariable(String identifier);


}
