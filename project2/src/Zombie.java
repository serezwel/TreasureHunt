import bagel.Image;
import bagel.util.Point;

public class Zombie{

    // image and type
    private final Image image = new Image("res/images/zombie.png");

    // render position
    private Point pos;
    private boolean visible;

    public Zombie(double x, double y){
        this.pos = new Point(x,y);
        this.visible = true;
    }

    public Point getPos() {
        return pos;
    }
    public boolean isVisible() { return visible;}

    // render image
    public void draw() {
        image.drawFromTopLeft(pos.x, pos.y);
    }

    public boolean meets(Player player) {
        boolean hasMet = false;
        double distanceToPlayer = player.getPos().distanceTo(pos);
        if (distanceToPlayer < ShadowTreasureComplete.ClOSENESS) {
            hasMet = true;
        }
        return hasMet;
    }

}
