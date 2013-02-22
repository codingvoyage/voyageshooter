package spaceinvaders;

/**
 *
 * @author Edmund
 */
public interface Scriptable {
   
    int getLineNumber();
    void setLineNumber(int newLineNumber);
    
    int getScriptID();
    boolean isRunning();
    
    public void beginWait(double millisecondsToWait);
    public boolean continueWait(double delta);

}
