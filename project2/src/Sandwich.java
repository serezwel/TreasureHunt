import bagel.Image;
import bagel.util.Point;

public class Sandwich extends Entity{
    // image
    private final Image image = new Image("res/images/sandwich.png");
    private boolean visible;

    // render position
    private Point pos;

    public Sandwich(double x, double y){
        this.pos = new Point(x,y);
        this.visible = true;
    }

    public Point getPos() {
        return pos;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visiblility) {
        this.visible = visiblility;
    }

    // render image
    public void draw() {
        if (visible) {
            image.drawFromTopLeft(pos.x, pos.y);
        }
    }

    public double getDistance(Player player) {
        return player.getPos().distanceTo(pos);
    }

    public boolean meets(Player player) {
        boolean hasMet = false;

        if (isVisible()){
            if (this.getDistance(player) < ShadowTreasureComplete.ClOSENESS) {
                hasMet = true;
            }
        }
        return hasMet;
    }


}
