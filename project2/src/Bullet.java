import bagel.*;
import bagel.util.Point;

/**
 * Bullet class to specify the bullet object in the game and its characteristics
 */
public class Bullet implements Pointable{
    private final Image image = new Image("res/images/shot.png");
    private double directionX;
    private double directionY;
    private Point pos;
    private Boolean visible;
    /**
     * step size of the bullet for each tick
     */
    public static final double BULLET_STEP_SIZE = 25;

    /**
     * Bullet constructor to specify its coordinates and initiate its visibility
     * @param x x coordinate of the bullet
     * @param y y coordinate of the bullet
     */
    public Bullet(double x, double y){
        this.pos = new Point(x, y);
        this.visible = false;
    }
    //bullet visibility

    /**
     * check if bullet is currently visible
     * @return visible boolean attribute to be returned
     */
    public Boolean isVisible() { return visible; };

    /**
     * sets the visibility of the bullet
     * @param visibility boolean attribute to change the visible attribute of the bullet.
     */
    public void setVisible(Boolean visibility) { this.visible = visibility; }

    /**
     * gets the coordinate of the bullet in the game.
     * @return pos Point object specifying the current coordinate.
     */
    public Point getPos() {
        return pos;
    }

    /**
     * sets the coordinate of the bullet in the game
     * @param newPos the new position for the bullet
     */
    public void setPos(Point newPos) { this.pos = newPos; }

    /**
     * similar to player's pointTo
     * @param dest destination of the bullet
     */
    public void pointTo(Point dest) {
        this.directionX = dest.x-this.pos.x;
        this.directionY = dest.y-this.pos.y;
        normalizeD();
    }

    /**
     * similar to player's normalizeD
     */
    public void normalizeD(){
        double len = Math.sqrt(Math.pow(this.directionX,2)+Math.pow(this.directionY,2));
        this.directionX /= len;
        this.directionY /= len;
    }

    /**
     * draw the object into the game
     */
    public void draw() {
        image.drawFromTopLeft(pos.x, pos.y);
    }

    /**
     * get the direction in the x axis
     * @return the direction for the bullet to go
     */
    public double getDirectionX() { return this.directionX; }
    /**
     * get the direction in the y axis
     * @return the direction for the bullet to go
     */
    public double getDirectionY() { return this.directionY; }
}
