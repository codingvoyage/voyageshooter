package spaceinvaders.entity;

/**
 * Entity Spawn Exception<br/>
 * Spawning failed, probably because you tried giving it a value it shouldn't have!
 * @author Brian
 */
public class EntitySpawnException extends Exception {

    public EntitySpawnException() {
        
    }
    
    public EntitySpawnException(String message) {
        super("EntitySpawnException: " + message);
    }
}
