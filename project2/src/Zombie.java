import bagel.Image;
import bagel.util.Point;

public class Zombie extends Entity{

    // image and type
    private final Image image = new Image("res/images/zombie.png");

    // render position
    private Point pos;
    private boolean visible;
    private boolean isShot;

    public Zombie(double x, double y){
        this.pos = new Point(x,y);
        this.visible = true;
        this.isShot = false;
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

    public double getDistance(Player player) {
        return player.getPos().distanceTo(pos);
    }
    public boolean getIsShot() { return this.isShot; }
    public void setShot(boolean shot) { this.isShot = shot; }
    public boolean meets(Bullet bullet) {
        boolean hasMet = false;
        double distanceToBullet = bullet.getPos().distanceTo(pos);
        if (distanceToBullet < ShadowTreasureComplete.BULLET_CLOSENESS) {
            hasMet = true;
        }
        return hasMet;
    }

}
