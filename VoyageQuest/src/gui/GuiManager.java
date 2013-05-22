package gui;

import java.util.LinkedList;
import java.util.ListIterator;
import org.newdawn.slick.GameContainer;

/**
 * Manages all active Gui objects
 * @author Brian Yang
 */
public abstract class GuiManager {
    
    /** sorted list of active GUI objects, arranged with the active object at the end */
    private static LinkedList<Gui> activeGui = new LinkedList<>();
    
    public static void draw() {
        ListIterator<Gui> activeIterator = activeGui.listIterator();
        while (activeIterator.hasNext())
            activeIterator.next().draw();
    }
    
    public static void select(Gui select) {
        activeGui.remove(select);
        activeGui.offer(select);
    }
    
    public static void add(Gui open) {
        activeGui.offer(open);
    }
    
    public static void close(Gui close) {
        activeGui.remove(close);
    }
    
    public static void update(GameContainer gc, int delta) {
        ListIterator<Gui> activeIterator = activeGui.listIterator();
        while (activeIterator.hasNext())
            activeIterator.next().next(gc, delta);
    }
    
    public static void display() throws VoyageGuiException {
        ListIterator<Gui> activeIterator = activeGui.listIterator();
        while (activeIterator.hasNext())
            activeIterator.next().display();
    }
        
    public static LinkedList<Gui> getList() {
        return activeGui;
    }
    
    public int getSize() {
        return activeGui.size();
    }
    
}