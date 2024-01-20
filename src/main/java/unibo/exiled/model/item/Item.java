package unibo.exiled.model.item;
/**
 * This interface models an object present in the game.
 * An object represents a fundamental element of the game context.
 * Each item has a name and description.
 * Implementing this interface allows you to define specific behaviors
 * for each type of item in the game.
 */
interface Item {
    /**
     * Returns the name of the item.
     *
     * @return the name of the item.
     */
    public String getName();
    /**
     * Returns the description of the item
     *
     * @return the description of the item.
     */
    public String getDescription();
}
