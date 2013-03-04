package spaceinvaders;

import java.util.ArrayList;

/**
 *
 * @author Edmund
 */
public class ThreadManager {
    
    ArrayList<Thread> threadCollection;
    ScriptReader scriptReader;
    
    public ThreadManager(ScriptReader scriptReaderInstance) 
    {
        threadCollection = new ArrayList<Thread>();
        scriptReader = scriptReaderInstance;
    }
    
    public void addThread(Thread newThread)
    {
        threadCollection.add(newThread);
    }
    
    public void markForDeletion(String targetThread)
    {
        boolean targetFound = false;
        int index = 0;
        Thread t;
        
        //While you haven't found it and we are still under the size limit
        while (!targetFound && ( index < threadCollection.size() ))
        {
            t = threadCollection.get(index);
            
            //If this one is it...
            if (t.getName().equals(targetThread))
            {
                //Look no further.
                targetFound = true;
                
                //Mark for deletion.
                t.markForDeletion();
            }
            index++;
        }
    }
            
    public void act(double delta)
    {
        boolean continueStepping = !threadCollection.isEmpty();
        int index = 0;
        while (continueStepping)
        {
            //Should any threads be deleted right now?
            if (threadCollection.get(index).isMarkedForDeletion())
            {
                threadCollection.remove(index);
                //index is unchanged, since everything shifts back by one
            }
            else 
            //Otherwise, just act on it.
            {
                scriptReader.act(threadCollection.get(index), delta);
                index++;
            }
            
            //Stop when we've reached the last thread.
            if (index == threadCollection.size()) {
                continueStepping = false;
            }
        }
    }
    
}
