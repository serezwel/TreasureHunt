import bagel.util.Point;

public abstract class Entity {
    public abstract Point getPos();

    public abstract boolean isVisible();

    public abstract void setVisible(boolean visibility);

    public abstract void draw();

    public abstract double getDistance(Player player);
}
