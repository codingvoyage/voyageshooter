package gui;

/**
 * Doubled nested elements
 * @author user
 */
public class DualGui<E extends Displayable> {
    
    /** the first object */
    private E first;
    /** the second object */
    private E second;
    
    public DualGui(E first, E second) {
        this.first = first;
        this.second = second;
    }
    
    /**
     * 
     * @return 
     */
    public E getFirst() {
        return first;
    }
    
    /**
     * 
     * @return 
     */
    public E getSecond() {
        return second;
    }
    
    public void draw() throws VoyageGuiException {
        first.print();
        second.print();
    }
    
    
}
