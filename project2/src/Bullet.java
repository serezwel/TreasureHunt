import bagel.*;
import bagel.util.Point;

public class Bullet implements Pointable{
    private final Image image = new Image("res/images/shot.png");
    private double directionX;
    private double directionY;
    private Point pos;
    private Boolean visible;

    public Bullet(double x, double y){
        this.pos = new Point(x, y);
        this.visible = false;
    }
    public Boolean isVisible() { return visible; };
    public void setVisible(Boolean visibility) { this.visible = visibility; }

    public Point getPos() {
        return pos;
    }
    public void setPos(Point newPos) { this.pos = newPos; }

    public void pointTo(Point dest) {
        this.directionX = dest.x-this.pos.x;
        this.directionY = dest.y-this.pos.y;
        normalizeD();
    }

    public void normalizeD(){
        double len = Math.sqrt(Math.pow(this.directionX,2)+Math.pow(this.directionY,2));
        this.directionX /= len;
        this.directionY /= len;
    }

    public void draw() {
        image.drawFromTopLeft(pos.x, pos.y);
    }
    public double getDirectionX() { return this.directionX; }
    public double getDirectionY() { return this.directionY; }
}
