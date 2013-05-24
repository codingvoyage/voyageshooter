package gui.types;

import gui.Displayable;
import gui.VoyageGuiException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.RoundedRectangle;

/**
 * Rectangular box within a rectangular box
 * @author user
 */
public class NestedRect implements Displayable {

    private RoundedRectangle outer;
    private RoundedRectangle inner;
    
    private Color outerColor;
    private Color innerColor;
    
    public NestedRect() {
        
    }
    
    public NestedRect(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        outer = new RoundedRectangle(x1, y1, w1, h1, 15);
        inner = new RoundedRectangle(x1, y1, w1, h1, 15);
        
        this.outerColor = new Color(166, 250, 252, 95);
        this.innerColor = new Color(205, 255, 145, 95);
    }
    
    public NestedRect(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2, Color color) {
        outer = new RoundedRectangle(x1, y1, w1, h1, 15);
        inner = new RoundedRectangle(x1, y1, w1, h1, 15);
        
        this.outerColor = color;
        this.innerColor = color;
    }
    
    public NestedRect(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2, Color outerColor, Color innerColor) {
        outer = new RoundedRectangle(x1, y1, w1, h1, 15);
        inner = new RoundedRectangle(x1, y1, w1, h1, 15);
        
        this.outerColor = outerColor;
        this.innerColor = innerColor;
    }
    
    public NestedRect(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2, int radius, Color color) {
        outer = new RoundedRectangle(x1, y1, w1, h1, radius);
        inner = new RoundedRectangle(x1, y1, w1, h1, radius);
        
        this.outerColor = color;
        this.innerColor = color;
    }
    
    public NestedRect(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2, int radius, Color outerColor, Color innerColor) {
        outer = new RoundedRectangle(x1, y1, w1, h1, radius);
        inner = new RoundedRectangle(x1, y1, w1, h1, radius);
        
        this.outerColor = outerColor;
        this.innerColor = innerColor;
    }
    
    @Override
    public void next(GameContainer gc, int delta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void print() throws VoyageGuiException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}