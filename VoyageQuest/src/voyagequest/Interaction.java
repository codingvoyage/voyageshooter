
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
    
    public void executeScript(int scriptIndex)
    {
        Thread t = new Thread(scriptIndex);
        t.setRunningState(false);
        t.setLineNumber(0);
       
        VoyageQuest.scriptReader.act(t, 0);
        
    }
}
