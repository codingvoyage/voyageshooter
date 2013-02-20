<<<<<<< HEAD
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
=======

>>>>>>> origin/BrianBranch
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
<<<<<<< HEAD
public class SpaceInvaders extends BasicGame {

    public SpaceInvaders() {
       super("Hello World");
=======

public class SpaceInvaders extends BasicGame {

    public SpaceInvaders() {
       super("We are Team Coding Voyage!");
>>>>>>> origin/BrianBranch
    }

    public void init(GameContainer gc) throws SlickException {
        //This initializes stuff
    }

    public void update(GameContainer gc, int delta) throws SlickException {
        //This part handles game logic
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        //Any and all graphics/rendering functions which should be called
        //with the drawing of each frame go HERE
        
<<<<<<< HEAD
        g.drawString("Hello World", 100, 100);
=======
        g.drawString("We are Team Coding Voyage!", 100, 100);
>>>>>>> origin/BrianBranch
    }

    public static void main(String[] args) throws SlickException
    {
       AppGameContainer app = new AppGameContainer(new SpaceInvaders());

<<<<<<< HEAD
       app.setDisplayMode(800, 600, false);
=======
       app.setDisplayMode(1024, 768, false);
>>>>>>> origin/BrianBranch
       app.start();
    }
}
