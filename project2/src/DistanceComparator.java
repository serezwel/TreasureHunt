import java.util.Comparator;

/**
 * The public class DistanceComparator implements Comparator with type Entity which will be used to sort
 * the zombie and sandwich ArrayList by its distance to the player and sorts it ascendingly.
 */
public class DistanceComparator implements Comparator<Entity>{
    private Player player;

    /**
     * Method compare takes in two entities and measures both of their distances to the player.
     * @param o1 first entity to be compared
     * @param o2 second entity to be compared
     * @return integer which will be negative if distance o1 < o2, 0 if o1 = o2 and positive if o1 > o2
     */
    public int compare(Entity o1, Entity o2) {
        return Double.compare(o1.getPos().distanceTo(player.getPos()), o2.getPos().distanceTo(player.getPos()));
    }

    /**
     * Method to give the player object so that the compare method can compare the distances.
     * @param player the player object in the game.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
