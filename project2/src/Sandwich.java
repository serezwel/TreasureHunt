import bagel.Image;
import bagel.util.Point;

/**
 * Sandwich class specifies the characteristics of the sandwich object in the game, is also a subclass of Entity
 */
public class Sandwich extends Entity{
    // image
    private final Image image = new Image("res/images/sandwich.png");
    private boolean visible;

    // render position
    private Point pos;
    /**
     * Sandwich constructor specifies the visibility and the coordinate position of the sandwich in the game
     */
    public Sandwich(double x, double y){
        this.pos = new Point(x,y);
        this.visible = true;
    }

    /**
     * similar to entity class
     * @return pos coordinate of the sandwich
     */
    public Point getPos() {
        return pos;
    }

    /**
     * similar to entity class
     * @return visibility attribute of the sandwich
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * similar to entity class
     * @param visiblility sets the visible attribute of the sandwich
     */
    public void setVisible(boolean visiblility) {
        this.visible = visiblility;
    }

    /**
     * similar to entity class, draws the sandwich into the game
     */
    public void draw() {
        if (visible) {
            image.drawFromTopLeft(pos.x, pos.y);
        }
    }

    /**
     * check if sandwich is within CLOSENESS range to the player
     * @param player the player object in the game
     * @return hasMet boolean attribute if sandwich is within range or not
     */
    public boolean meets(Player player) {
        boolean hasMet = false;

        if (isVisible()){
            if (this.pos.distanceTo(player.getPos()) < ShadowTreasure.CLOSENESS) {
                hasMet = true;
            }
        }
        return hasMet;
    }


}
