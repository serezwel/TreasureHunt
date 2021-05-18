import bagel.*;
import bagel.util.Point;

public class Bullet implements Pointable{
    private final Image image = new Image("res/images/shot.png");
    private double directionX;
    private double directionY;

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
}
