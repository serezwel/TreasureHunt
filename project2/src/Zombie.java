import bagel.Image;
import bagel.util.Point;

/**
 * Zombie class, which is a subclass of entity, to determine the characteristics of the zombie in the game
 */
public class Zombie extends Entity{

    // image and type
    private final Image image = new Image("res/images/zombie.png");

    // render position
    private Point pos;
    private boolean visible;
    private boolean isShot;

    /**
     * zombie constructor specifying its coordinates, visiblity and whether it has been shot or not
     * The attribute isShot is used to avoid the player from shooting the same zombie twice.
     * @param x x coordinate of the zombie.
     * @param y y coordinate of the zombie.
     */
    public Zombie(double x, double y){
        this.pos = new Point(x,y);
        this.visible = true;
        this.isShot = false;
    }

    /**
     * gets the coordinate of the zombie object
     * @return pos the Point object specifying the coordinates
     */
    public Point getPos() {
        return pos;
    }

    /**
     * method from the Entity class
     * @return visible the visibility attribute of the zombie
     */
    public boolean isVisible() { return visible;}

    /**
     * method from Entity class
     * @param visibility boolean attribute to set the visibility of an entity
     */
    public void setVisible(boolean visibility) { this.visible = visibility; }
    // render image

    /**
     * method from Entity class
     */
    public void draw() {
        image.drawFromTopLeft(pos.x, pos.y);
    }

    /**
     * check whether the zombie has been shot or not
     * @return this.isShot the attribute specifying whether the zombie has been shot or not.
     */
    public boolean getIsShot() { return this.isShot; }

    /**
     * updates the attribute isShot to specify whether zombie has been shot or not
     * @param shot
     */
    public void setShot(boolean shot) { this.isShot = shot; }

    /**
     * method meets checks if a bullet is within the BULLET_CLOSENESS range from the zombie
     * @param bullet bullet object in the game
     * @return hasMet true if bullet is within range of the zombie, false if not.
     */
    public boolean meets(Bullet bullet) {
        boolean hasMet = false;
        double distanceToBullet = bullet.getPos().distanceTo(pos);
        if (distanceToBullet < ShadowTreasure.BULLET_CLOSENESS) {
            hasMet = true;
        }
        return hasMet;
    }

}
