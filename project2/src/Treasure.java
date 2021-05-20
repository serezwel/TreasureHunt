import bagel.Image;
import bagel.util.Point;

/**
 * Treasure class determines the characteristics of the treasure object in the game.
 */
public class Treasure {
    private final Image image = new Image("res/images/treasure.png");
    private Point pos;

    /**
     * Treasure constructor specifying its coordinates in the game
     * @param x x coordinate of the treasure
     * @param y y coordinate of the treasure
     */
    public Treasure(double x, double y){
        this.pos = new Point(x, y);
    }

    /**
     * gets the coordinates of the treasure in the game.
     * @return pos the coordinate of the treasure.
     */
    public Point getPos() { return pos; }

    /**
     * draws the treasure into the game
     */
    public void draw() {
        image.drawFromTopLeft(pos.x, pos.y);
    }

    /**
     * check if player is within CLOSENESS range of the treasure
     * @param player the player object in the game
     * @return boolean attribute whether the player is within range.
     */
    public boolean meets(Player player) {
        boolean hasMet = false;
        double distanceToPlayer = player.getPos().distanceTo(pos);
        if (distanceToPlayer < ShadowTreasure.CLOSENESS) {
            hasMet = true;
        }
        return hasMet;
    }
}
