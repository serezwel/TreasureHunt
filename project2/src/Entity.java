import bagel.util.Point;

/**
 * abstract class Entity defines the non-moving objects in the game (zombie and sandwich).
 */
public abstract class Entity {
    /**
     * abstract class getPos() returns a Point object which is the coordinates of the Entity object in the game.
     * @return Point object specifying the coordinates of the entity.
     */
    public abstract Point getPos();

    /**
     * abstract class isVisible() returns true if entity is still visible in the game and false if it is not
     * @return boolean true if visible, false if not.
     */
    public abstract boolean isVisible();

    /**
     * abstract class sets the visibility of the entity in the game
     * @param visibility boolean attribute to set the visibility of an entity
     */
    public abstract void setVisible(boolean visibility);

    /**
     * draws the entity into the game.
     */
    public abstract void draw();
}
