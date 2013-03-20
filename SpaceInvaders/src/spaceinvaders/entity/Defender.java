package spaceinvaders.entity;

/**
 * Entity with Defense
 * <strong>Known Implementations:</strong> Enemy, Misc, Immovable
 * @author Brian Yang
 */
public interface Defender {
    /** Base defense of entity */
    public double getDefense();
}