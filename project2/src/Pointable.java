import bagel.util.Point;

/**
 * Pointable interface contains method pointTo for the player or bullet to go to
 */
interface Pointable {
    /**
     * points the player or bullet to a certain direction (i.e. towards the zombie)
     * @param dest the destination for the player or bullet
     */
    void pointTo(Point dest);
}
