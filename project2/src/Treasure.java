import bagel.Image;
import bagel.util.Point;

public class Treasure {
    private final Image image = new Image("res/images/treasure.png");
    private Point pos;

    public Treasure(double x, double y){
        this.pos = new Point(x, y);
    }
    public Point getPos() { return pos; }
    public void draw() {
        image.drawFromTopLeft(pos.x, pos.y);
    }


}
