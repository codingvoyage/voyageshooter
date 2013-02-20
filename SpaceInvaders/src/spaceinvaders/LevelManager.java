/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

/**
 *
 * @author Edmund
 */
public class LevelManager implements Scriptable {
    private int scriptID;
    private int currentLineNumber;
    private boolean inProgress;
    
    public LevelManager()
    {
        scriptID = 1;
        currentLineNumber = 0;
        inProgress = false;
    }
    
    
    public void spawnEntity() {
        
    
    }
    
    public int getLineNumber()
    {
        return currentLineNumber;
    }
    
    public int getScriptID()
    {
        return scriptID;
    }
    
    public boolean isRunning()
    {
        return inProgress;
    }
    
  
}
