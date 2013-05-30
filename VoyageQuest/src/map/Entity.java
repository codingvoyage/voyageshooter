package map;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;

import gui.GuiManager;
import gui.special.DialogBox;

import voyagequest.VoyageQuest;
import voyagequest.DoubleRect;
import voyagequest.Global;
import scripting.*;
import voyagequest.Res;

/**
 *
 * @author user
 */
public class Entity extends ScriptableClass implements Rectangular {
    /** The entity's boundary rectangle. For this, r.x and r.y are
      * the actual coordinates of the Entity in Map space. Width and
      * height are the width and height of the entity, which are likely
      * to be 64, 128 respectively.
      */
    public DoubleRect r;
    
    /** 
     * The entity's collision rectangle RELATIVE to its boundary.
     */
    public DoubleRect collRect;
    
    //Used for rendering Entities, set by another class.
    public RenderSetting renderSetting;
    
    //The dialog box associated with this Entity
    public DialogBox dialog;
    
    //Animation
    double accumulatedDelta = 0.0d;
    int currentFrame = 0;
    protected Animation currentAnimation;
    
    //Automated movement with scripting uses these
    public double velocityX;
    public double velocityY;
    

    /**
     * Constructs an Entity with only its boundary Rectangle
     * 
     * @param r the boundary Rectangle
     * @throws SlickException 
     */
    public Entity(DoubleRect boundaryRect) throws SlickException
    {
        this.r = boundaryRect;
        this.collRect = new DoubleRect(5.0d, 70.0d, 50.0d, 50.0d);
        
        currentAnimation = Res.animations.get("Sebastian Forward");
    }
    
    
    public DoubleRect getCollRect()
    {
        return new DoubleRect(
                r.x + collRect.x,
                r.y + collRect.y,
                collRect.width,
                collRect.height);
    }
    
    public void updateAnimation(double delta)
    {
        accumulatedDelta += delta;
        double currentFrameDuration = currentAnimation.getDuration(currentFrame);
        
        if (accumulatedDelta > currentFrameDuration)
        {
            currentFrame++;
            if (currentFrame >= currentAnimation.getFrameCount())
            {
                currentFrame = 0;
            }
            
            accumulatedDelta =- currentFrameDuration;
            
        }
        
        
    }
    
    public void draw(Graphics g, float xOffset, float yOffset)
    {
        currentAnimation.getImage(currentFrame).draw(xOffset, yOffset);
        
        //Global.character.draw(xOffset, yOffset);
        
        //If debugging, then draw the boundary boxes and the collision boxes
        if (VoyageQuest.DEBUG_MODE == true)
        {
            g.drawRect(xOffset, yOffset, (float)r.width, (float)r.height);
            g.drawRect((float)(xOffset + collRect.x),
                       (float)(yOffset + collRect.y),
                       (float)collRect.width,
                       (float)collRect.height);
        }
        
    }
    
    public void setVelocity(double vx, double vy)
    {
        velocityX = vx;
        velocityY = vy;
    }
    
    public void beginMove(double pixelsToMove) {
        setTemporaryParameter(new Parameter(pixelsToMove));
        mainThread.setRunningState(true);
    }
    
    public void beginMove(int tilesToMove) {
        int pixelsToMove = tilesToMove * Global.currentMap.TILE_LENGTH;
        setTemporaryParameter(new Parameter(pixelsToMove));
        mainThread.setRunningState(true);
    }
    
    
    /**
     * Checks if the entity should continue moving (straight line)
     * @param delta elapsed time between checks
     * @return boolean indicating whether or not the entity should continue moving
     */
    public boolean continueMove(double delta) {
        //Get the distance left to move...
        Parameter tempParam = getTemporaryParameter();

        double xStep = velocityX * delta;
        double yStep = velocityY * delta;
        
        boolean didWeMove = attemptMove(xStep, yStep, delta);
        
        if (didWeMove)
        {
            //Successfully moved, update temporary variable
            double movedDistance = Math.sqrt(xStep*xStep + yStep*yStep);

            tempParam.setDoubleValue(
                   tempParam.getDoubleValue() - 
                   movedDistance);

            setTemporaryParameter(tempParam);

            if (tempParam.getDoubleValue() < 0)
            {
                System.out.println("We're done walking.");
                //Oh, so we're done moving. Great.
                mainThread.setRunningState(false);
                return false;
            }
        }
        
        //Alright, we still have to move. Keep moving.
        return true;
    }
    
    public boolean attemptMove(double xMove, double yMove, double delta)
    {
        //Where would we end up at
        double candidateX = r.x + collRect.x + xMove;
        double candidateY = r.y + collRect.y + yMove;
        DoubleRect collCandidate = new DoubleRect(candidateX,
                                                    candidateY,
                                                    collRect.getWidth(),
                                                    collRect.getHeight());

        //Now query the QuadTree for any possible collisions
        LinkedList<Rectangular> collisionCandidates = Global.currentMap.collisions.rectQuery(collCandidate);

        //Now we see if collides
        boolean collides = false;
        for (Rectangular e : collisionCandidates)
        {
            if (collCandidate.intersects(e.getRect()) && !e.equals(this))
            {
                collides = true;
                break;
            }
        }


        if (collides == true)
        {
            //Could not move
            
            
            
            return false;
        }
        else
        {
            //Update the animation:
            this.updateAnimation(delta);
        
        
            //Move to the proposed location
            Global.currentMap.collisions.removeEntity(this);
            r.x = r.x + xMove;
            r.y = r.y + yMove;
            Global.currentMap.collisions.addEntity(this);
            
            return true;
        }
        
    }
    
    /**
     * Remember, collision box is the box of collision, with its position in space
     * as well as its width and height.
     * Boundary box is the entity's x, y location in space, and its width and height.
     * 
     * @return the double rect of the boundaries of this Entity.
     */
    public DoubleRect getRect()
    {
        return r;
    }
    
    public void act(GameContainer gc, int delta)
    {
        
    }
    
    
    /**
     * Make the entity speak with a dialog box
     * @param text the dialog text
     */
    public void speak(String text) {
        dialog = new DialogBox(text);
        dialog.start();
        mainThread.setRunningState(true);
        
    }
    
    public boolean continueSpeak()
    {
        if (dialog.continuePrinting() == false)
        {
            mainThread.setRunningState(false);
            return false;
        }
        
        return true;
        
    }
    
    
}