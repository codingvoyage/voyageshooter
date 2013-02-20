
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
       super("We are Team Coding Voyage!");
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
        
        g.drawString("We are Team Coding Voyage!", 100, 100);
    }

    public static void main(String[] args) throws SlickException
    {
       AppGameContainer app = new AppGameContainer(new SpaceInvaders());

       app.setDisplayMode(1024, 768, false);
       app.start();
    }
}
