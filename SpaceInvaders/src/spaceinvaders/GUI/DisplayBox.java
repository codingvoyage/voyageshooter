/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.GUI;


import org.newdawn.slick.*;
import spaceinvaders.script.*;

import java.awt.Font;

/**
 *
 * @author Edmund
 */
public class DisplayBox extends ScriptableClass {
    private Image testImage;
    private Image textBox;
    TrueTypeFont trueTypeFont;
    
    public boolean isDisplaying;
    public boolean displayCharacter;
    
    private String currentMessage;
    
    
    
    public DisplayBox() throws SlickException
    {
        testImage = new Image("src/spaceinvaders/images/commander.png");
        textBox = new Image("src/spaceinvaders/images/box.png");
        
        
        Font font = new Font("Britannic Bold", Font.BOLD, 25);
        trueTypeFont = new TrueTypeFont(font, true);
        
        isDisplaying = false;
    }
    
    public void display(String message, boolean displayCharacter)
    {
        currentMessage = message;
        this.displayCharacter = displayCharacter;
        isDisplaying = true;
    }
    
    public void stopDisplay()
    {
        isDisplaying = false;
    }
    
    public void draw(Graphics g)
    {
        if (displayCharacter)
        {
            drawCharacter();
        }
        
        drawBox();
        
        g.setFont(trueTypeFont);
        g.setColor(Color.black);
            drawText(g);
    }
    
    public void drawCharacter()
    {
        testImage.draw(0, 0);
    }
    
    public void drawBox()
    {
        textBox.draw(20, 500);
    }
    
    public void drawText(Graphics g)
    {
        int x = 50;
        int y = 550;
        
        //System.out.println(trueTypeFont.getWidth("Hello"));
        
        g.drawString(currentMessage, x, y);
    }
    
    
    //Called to signal that we want to move on.
    public void next()
    {
        isDisplaying = false;
    }
}
