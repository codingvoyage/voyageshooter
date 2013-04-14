package spaceinvaders.entity;

import java.util.ArrayList;

/**
 * A wrapper for ArrayList that stores a current 
 * index and requires unique items.
 * @author Brian Yang
 * @version 1.0
 */
public class SelectionList<E> {
    
    /** The ArrayList */
    private ArrayList<E> items;
    /** The currently selected index */
    private int index;
    
    /** 
     * Add a new unique object to the array
     * @param e the object to add
     * @return whether or not the object has been successfully added
     */
    public boolean add(E e) {
        if (!items.contains(e))
            return items.add(e);
        return false;
    }
    
    /**
     * Add a new object to the array, replacing the existing one if it exists
     * @param e the object to add
     * @param replace whether or not the existing object, if it exists, should be replaced
     * @return whether or not the object has been successfully added
     */
    public boolean add(E e, boolean replace) {
        if (items.contains(e)) {
            if (replace) {
                int tempIndex = items.lastIndexOf(e);
                items.remove(tempIndex);
                items.add(tempIndex, e);
                return true;
            } else {
                return false;
            }
        } else {
            return add(e);
        }
                
    }
    
    /**
     * Remove the object at the specified index
     * @param index index to remove
     * @return the removed object or <code>null</code>
     */
    public E remove(int index) {
        if (items.size() > index)
            return items.remove(index);
        return null;
    }
    
    /**
     * Remove the first occurrence object from the list
     * @param e the object to remove
     * @return whether or not the object has been removed
     */
    public boolean remove(E e) {
        if (items.contains(e))
            return items.remove(e);
        return false;
    }
    
    /**
     * Retrieves the next object in the list 
     * and increments the index
     * @return the next object in the list
     */
    public E getNext() {
        return items.get(next());
    }
    
    /**
     * Increments the index
     * @return the new index
     */
    public int next() {
        if (index + 1 < items.size())
            index++;
        else
            index = 0;
        return index;
    }
    
    /**
     * Retrieves the list
     * @return the stored list
     */
    public ArrayList<E> getList() {
        return items;
    }
    
}
