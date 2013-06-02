
package voyagequest;

import java.util.Properties;
import scripting.Thread;
/**
 *
 * @author Edmund Qiu
 */
public class Interaction {
    
    public Interaction(Properties props)
    {
        if (props.containsKey("runScript")) 
            executeScript(Integer.parseInt((String)props.get("runScript")));
    }
    
    
    public Interaction(int scriptID)
    {
        executeScript(scriptID);
    }
    
    
    
    public void executeScript(int scriptIndex)
    {
        
        Thread t = new Thread(scriptIndex);
        t.setRunningState(false);
        t.setLineNumber(0);
       
        //So we have to run it first when it comes out...
        VoyageQuest.scriptReader.act(t, 20);
        
        //Then we add it to threadmanager so it continues to run
        VoyageQuest.threadManager.addThread(t);
    }
}
