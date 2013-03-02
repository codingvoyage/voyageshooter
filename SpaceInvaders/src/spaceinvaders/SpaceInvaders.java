package spaceinvaders;

import org.newdawn.slick.*;
import java.util.HashMap;
/**
 * Space Invaders Game
 * A (maybe not so) simple space invaders game
 * 
 * @author Brian Yang
 * @author Edmund Qiu
 * @version 0.1
 */

public class SpaceInvaders extends BasicGame {
    ScriptManager scriptCollection;
    ScriptReader scriptReader;
    
    //Our testing
    Entity testEntity;
    
    
    public SpaceInvaders() {
       super("We are Team Coding Voyage!");
    }

    public void init(GameContainer gc) throws SlickException {
        //This initializes stuff
        gc.setMinimumLogicUpdateInterval(20);
        
        //Initialize the ScriptManager
        scriptCollection = new ScriptManager();
        scriptCollection.loadScript("script.txt", 0);
        scriptCollection.loadScript("shortdemo.txt", 1);
        scriptCollection.loadScript("toread.txt", 2);
        scriptCollection.loadScript("ROCKET MOTTO.txt", 3);
        
        
        
        
        //Initialize ScriptReader, passing it the ScriptManager handle
        scriptReader = new ScriptReader(scriptCollection);
        
        testEntity = new Entity();
    }

    public void update(GameContainer gc, int delta) throws SlickException {
        //This part handles game logic
        scriptReader.act(testEntity, delta);
        
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        //Any and all graphics/rendering functions which should be called
        //with the drawing of each frame go HERE
        
        g.drawString("We are Team Coding Voyage!", 100, 50);
        
        
        //Wow. it worked.
        //Okay, time to show that it worked.
        //myRandomScript
        
        Script sampleScript = scriptCollection.getScriptAtID(3);
        int linesToPrint = sampleScript.getLineCount();
        
        for (int i = 0; i < linesToPrint; i++)
        {
            g.drawString(sampleScript.getLine(i).toString(), 100, 100 + 16*i);     
        }
        
    }

    public static void main(String[] args) throws SlickException
    {
       AppGameContainer app = new AppGameContainer(new SpaceInvaders());

       app.setDisplayMode(1024, 768, false);
       app.setAlwaysRender(true);
       app.setTargetFrameRate(60);
       //This is important. I found out that with this command, it will limit
       //the frames displayed per second to around 60. We DON'T want frames
       //being drawn 2000 times per second.
       
       app.start();
    }
}
