package unibo.exiled.model.game;

import java.util.Optional;

import unibo.exiled.model.character.CharacterClass;
import unibo.exiled.model.character.GameCharacter;
import unibo.exiled.model.character.attributes.AttributeIdentifier;
import unibo.exiled.model.character.enemy.EnemyCollection;
import unibo.exiled.model.character.player.Player;
import unibo.exiled.model.move.MoveSet;
import unibo.exiled.model.utilities.Direction;
import unibo.exiled.model.utilities.Position;

public interface CharacterModel {
        /**
     * Moves the player in the selected direction.
     *
     * @param dir The direction where to move the player.
     */
    void movePlayer(Direction dir);

    /**
     * Moves the enemies in random directions.
     */
    void moveEnemies();

    /**
     * Returns the player.
     * 
     * @return the player.
     */
    Optional<Player> getPlayer();

    /**
     * Returns the enemies.
     * 
     * @return the enemies.
     */
    Optional<EnemyCollection> getEnemies();

    /**
     * Gets the evaluated attribute of the player.
     *
     * @param id The attribute identifier to get.
     * @return A double representing the evaluated value of the attribute.
     */
    double getPlayerAttributeOf(AttributeIdentifier id);

    /**
     * Gets the level of the player.
     *
     * @return A integer representing the level of the player.
     */
    int getPlayerLevel();

    /**
     * Gets the elemental class of the player.
     *
     * @return The elemental class of the player.
     */
    CharacterClass getPlayerClass();

    /**
     * Gest the player move set.
     *
     * @return The a move set.
     */
    MoveSet getPlayerMoveSet();

    /**
     * Gets the position of the player.
     *
     * @return The position of the player.
     */
    Position getPlayerPosition();
    
    /**
     * Gets the character in the selected cell.
     *
     * @param pos The cell where to search for the character.
     * @return An optional containing the character if found, empty otherwise.
     */
    Optional<GameCharacter> getCharacterFromPosition(Position pos);

    /**
     * Sets the elemental class of the player.
     *
     * @param playerClass The ElementalType representing the new elemental class of
     *                    the player.
     */
    void assignPlayerClass(CharacterClass playerClass);

    /**
     * Retrieves the current experience of the player.
     *
     * @return The current experience of the player.
     */
    int getPlayerCurrentExperience();

    /**
     * Retrieves the experience cap of the player.
     *
     * @return The experience cap of the player.
     */
    int getPlayerExperienceCap();

    /**
     * Adds experience to the player.
     * 
     * @param amount the experience the player gained killink an enemy.
     */
    void addPlayerExperience(double amount);

    /**
     * Removes the enemy in the position.
     * 
     * @param pos the position of the enemy to remove.
     */
    void removeEnemyFromPosition(Position pos);

}
