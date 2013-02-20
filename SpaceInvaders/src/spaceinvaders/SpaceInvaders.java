/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import org.newdawn.slick.*;

/**
 * Space Invaders Game
 * A simple space invaders game
 * 
 * @author Brian Yang
 * @author Edmund Qiu
 * @version 0.1
 */
public class SpaceInvaders extends BasicGame {

    public SpaceInvaders() {
       super("Hello World");
    }

    public void init(GameContainer gc) throws SlickException {

    }

    public void update(GameContainer gc, int delta) throws SlickException {

    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
       g.drawString("Hello World", 100, 100);
    }

    public static void main(String[] args) throws SlickException
    {
       AppGameContainer app = new AppGameContainer(new SpaceInvaders());

       app.setDisplayMode(800, 600, false);
       app.start();
    }
}
