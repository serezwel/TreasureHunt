import bagel.Image;
import bagel.util.Point;

public class Zombie extends Entity{

    // image and type
    private final Image image = new Image("res/images/zombie.png");

    // render position
    private Point pos;
    private boolean visible;
    private boolean shot;

    public Zombie(double x, double y){
        this.pos = new Point(x,y);
        this.visible = true;
        this.shot = false;
    }

    public Point getPos() {
        return pos;
    }
    public boolean isVisible() { return visible;}
    public void setVisible(boolean visibility) { this.visible = visibility; }
    // render image
    public void draw() {
        image.drawFromTopLeft(pos.x, pos.y);
    }

    public boolean getShot() { return this.shot; }
    public void setShot(boolean isShot) { this.shot = isShot; }

    public boolean meets(Bullet bullet) {
        boolean hasMet = false;
        double distanceToPlayer = bullet.getPos().distanceTo(pos);
        if (distanceToPlayer < ShadowTreasureComplete.ClOSENESS) {
            hasMet = true;
        }
        return hasMet;
    }

}
