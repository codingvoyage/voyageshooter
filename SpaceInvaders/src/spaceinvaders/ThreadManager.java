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
    
    public Thread getThreadAtName(String targetThread)
    {
        for (Thread t: threadCollection)
        {
            if (t.getName().equals(targetThread))
            {
                return t;
            }
        }
        
        //couldn't find...
        return null;
    }
    
    public void markForDeletion(String targetThread)
    {
        boolean targetFound = false;
        int index = 0;
        Thread t;
        
        //While you haven't found it and we are still under the size limit
        while (!targetFound && ( index < getThreadCount() ))
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
        Thread currentThread;
        while (continueStepping)
        {
            //Get current thread...
            currentThread = threadCollection.get(index);
            //Should any threads be deleted right now?
            if (currentThread.isMarkedForDeletion())
            {
                threadCollection.remove(index);
                //index is unchanged, since everything shifts back by one
                //we'll be on track for the next one by NOT MOVING
            }
            else 
            //Otherwise, just act on it.
            {
                scriptReader.act(currentThread, delta);
                index++;
            }
            
            //Stop when we've reached the last thread.
            if (index == getThreadCount()) {
                continueStepping = false;
            }
        }
    }
    
    public int getThreadCount()
    {
        return threadCollection.size();
    }
}
