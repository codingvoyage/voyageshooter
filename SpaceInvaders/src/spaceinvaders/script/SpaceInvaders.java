package spaceinvaders.script;

import spaceinvaders.entity.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.fills.GradientFill;

/**
 * Space Invaders Game
 * 
 * An exciting space invaders game created 
 * by two AP Computer Science students at 
 * Hunter College High School
 * 
 * Copyright (c) 2013 Team Coding Voyage
 *
 * Licensed under the terms of the GNU General Public License v3
 * as published by the Free Software Foundation.
 * 
 * @author Brian Yang
 * @author Edmund Qiu
 * @version 0.5
 */

/**
 * Creates, renders, and updates the actual game window
 */
public class SpaceInvaders extends BasicGame {
    /** All the scripts to be read */
    ScriptManager scriptCollection;
    /** The actual script reader */
    ScriptReader scriptReader;
    
    /** Manages all the scripting threads */
    ThreadManager threadManager;
        
    /** A test enemy entity that moves */
    MovableEntity testEntity;
    
    /** The player spaceship */
    Image ship;
    /** The background */
    Image space;
    /** Test laser beam */
    Rectangle beam;
    
    /** origin */
    public static final double ORIGIN = 0.0;
    /** horizontal resolution */
    public static final int X_RESOLUTION = 1024;
    /** vertical resolution */
    public static final int Y_RESOLUTION = 768;
    /** map boundary factor */
    public static final float EDGE_FACTOR = 75.0f;
    /** velocity conversion from miles to pixels */
    public static final double VELOCITY_FACTOR = 0.01;
    
    /** rotation size */
    public static final float ROTATION_SIZE = 0.2f;
    /** step size */
    public static final float STEP_SIZE = 0.4f;
    /** back up size */
    public static final float BACK_SIZE = 0.075f;
    
    /** Default x coordinates */
    float x = 400;
    /** Default y coordinates */
    float y = 300;
    /** Default scaling factor */
    float scale = 1;
    
    /** TEMPORARY TEST VARIABLES */
    Weapon laser;
    boolean shooting = false;
    double shootDir;
    
    /** Data for all entities */
    private EntityGroup entities;
    
    /**
     * Construct a new game
     */
    public SpaceInvaders() {
       super("We are Team Coding Voyage!");
    }

    /**
     * Initialize the game container and start the scripting engine
     * @param gc the new game container
     * @throws SlickException something went wrong with the Slick engine
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
        //This initializes stuff
        gc.setMinimumLogicUpdateInterval(20);
        
        /* Load images */
        beam = new Rectangle(-500, -500, 10, 10); // render off screen so its not visible yet
        ship = new Image("src/spaceinvaders/images/spaceship.png");
        space = new Image("src/spaceinvaders/images/bluestar.jpg");
        
        
        /* load the data for all entities */     
        if(loadEntities()) {
            System.out.println("Entity data has successfully been loaded.");
        } else {
            /* The entities failed to load, therefore we will use the default entities */
            System.out.println("WARNING - Entity data has failed to load! Loading blank entity group.");
        }
    }


    /**
     * Update the screen
     * @param gc the game container
     * @param delta time interval
     * @throws SlickException something went horribly wrong with Slick
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        //Should work UNTIL we start having the ability to create
        //new threads, upon which you should examine this carefully to make
        //sure that there aren't massive off-by-one-errors.
        
       // threadManager.act(delta);
        
        /** user keyboard control */
        control(gc, delta);
 
    }

    /**
     * Render the game window
     * @param gc the game container
     * @param g the graphics engine
     * @throws SlickException something went horribly wrong with Slick
     */
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        //Any and all graphics/rendering functions which should be called
        //with the drawing of each frame go HERE
        
        space.draw(0,0);
        ship.draw(x,y,scale);
        ShapeFill filler = new GradientFill(0, 0, Color.red, 10, 10, Color.green, true);
        g.fill(beam, filler);
        
        g.drawString("Player: " + x + ", " + y, 600, 25);
        g.drawString("Bullet: " + beam.getX() + ", " + beam.getY(), 600, 40);

    }

    /**
     * The main method<br/>
     * Create the game window and start the game!
     * @param args command line arguments
     * @throws SlickException something went horribly wrong with Slick
     */
    public static void main(String[] args) throws SlickException {
        /*
         * Start Game Window Construction
         */
        
        AppGameContainer app = new AppGameContainer(new SpaceInvaders());

        app.setDisplayMode(X_RESOLUTION, Y_RESOLUTION, false);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(60);
        //This is important. I found out that with this command, it will limit
        //the frames displayed per second to around 60. We DON'T want frames
        //being drawn 2000 times per second.

        app.start();

    }
    
    /**
     * Loads all entity data from a separate data file
     * @return a boolean indicating whether or not the entity data have been successfully loaded
     */
    private boolean loadEntities() {
        JsonReader<EntityGroup> reader = new JsonReader<EntityGroup>(EntityGroup.class, "src/spaceinvaders/entity/EntityData.json");
        if(reader.readJson()) {
            entities = reader.getObject();
            return true;
        }
        entities = new EntityGroup();
        return false;
    }
    
    /**
     * Player keyboard control<br/>
     * Will be moved to the Entity package later
     * @param gc the game container
     * @param delta time interval 
     */
    private void control(GameContainer gc, int delta) throws SlickException {
        Input input = gc.getInput();
 
        /* rotate to the left */
        if(input.isKeyDown(Input.KEY_LEFT)) {
            ship.rotate(-ROTATION_SIZE * delta);
        }
 
        /* rotate to the right */
        if(input.isKeyDown(Input.KEY_RIGHT)) {
            ship.rotate(ROTATION_SIZE * delta);
        }
 
        /* move forward in current direction */
        if(input.isKeyDown(Input.KEY_UP)) {
            /* size for one single step */
            float step = STEP_SIZE * delta;
 
            /* which direction are we facing? */
            float rotation = ship.getRotation();
            
            /* move the player */
            if(x + step * Math.sin(Math.toRadians(rotation)) > ORIGIN - EDGE_FACTOR && x + step * Math.sin(Math.toRadians(rotation)) < X_RESOLUTION - EDGE_FACTOR) 
                x += step * Math.sin(Math.toRadians(rotation));
            if(y - step * Math.cos(Math.toRadians(rotation)) > ORIGIN - EDGE_FACTOR && y - step * Math.cos(Math.toRadians(rotation)) < Y_RESOLUTION - EDGE_FACTOR) 
                y -= step * Math.cos(Math.toRadians(rotation));
        }
        
        /* back up a bit */
        if(input.isKeyDown(Input.KEY_DOWN)) {
            /* size for one single back step */
            float step = BACK_SIZE * delta;
 
            /* which direction are we facing? */
            float rotation = ship.getRotation() * -1;
 
            /* move the player */
            if(x + step * Math.sin(Math.toRadians(rotation)) > ORIGIN - EDGE_FACTOR && x + step * Math.sin(Math.toRadians(rotation)) < X_RESOLUTION - EDGE_FACTOR) 
                x += step * Math.sin(Math.toRadians(rotation));
            if(y + step * Math.cos(Math.toRadians(rotation)) > ORIGIN - EDGE_FACTOR && y + step * Math.cos(Math.toRadians(rotation)) < Y_RESOLUTION - EDGE_FACTOR) 
                y += step * Math.cos(Math.toRadians(rotation));
        }
        
        if(input.isKeyDown(Input.KEY_SPACE)) {
            if(!shooting) fire();
        }
        
        if(shooting) {
            /* size for one single back step */
            double step = laser.getVx() * VELOCITY_FACTOR * delta;
            
            beam.setX((float)(beam.getX() + step * Math.sin(shootDir)));
            beam.setY((float)(beam.getY() - step * Math.cos(shootDir)));
            if (beam.getX() > X_RESOLUTION + EDGE_FACTOR || beam.getX() < 0 - EDGE_FACTOR || beam.getY() > Y_RESOLUTION + EDGE_FACTOR || beam.getY() < 0 - EDGE_FACTOR) { // its off the map! stop the bullet!
                /* Stop moving the bullet */
                shooting = false;
                /* Reset the bullet to the default location */
                beam.setLocation(-500, -500);
            }
        }
    }
    
    /**
     * Firing a bullet from the player<br/>
     * Uses Weapon data from EntityGroup<br/>
     * Will be moved to the Entity package later (more specifically the Attacker interface)
     */
    private void fire() {
        /* Use Bomb Formula 9001 */
        laser = entities.getWeapon("Bomb Formula 9001");
        
        /* The graphics references will be included in the data file later, but for now... */
        beam.setLocation(x + 115, y + 25);
        /* Define the direction here and not update because once the bullet is fired, we want it to maintain direction */
        shootDir = Math.toRadians(ship.getRotation());
        shooting = true;
        
    }
}