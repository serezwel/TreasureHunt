import java.util.Comparator;

public class DistanceComparator implements Comparator<Entity>{
    private Player player;
    public int compare(Entity o1, Entity o2) {
        return Double.compare(o1.getDistance(player), o2.getDistance(player));
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
}
