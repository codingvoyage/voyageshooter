package spaceinvaders.script;

/**
 *
 * @author Edmund
 */
public interface Scriptable {
    
    public void setMainThread(Thread t);
    public Thread getMainThread();
    
}
