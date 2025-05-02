package sprint2implementation.cards;

import sprint2implementation.characters.Player;
import sprint2implementation.grounds.Tile;
import sprint2implementation.towers.Tower;

/**
 * Interface representing a special ability associated with a God card.
 *
 * @author Pee Yee Peen
 */
public interface SpecialAbility {


    /**
     * Executes the special ability of the God card for the given player.
     *
     * @param currentPlayer the player using the special ability
     * @param selectedTile the tile currently selected
     * @param clickedTile the tile that the player clicked to act upon
     * @param tower the tower present at the clicked tile, if any
     *
     * @return true if the ability execution results in a continued phase
     */
    boolean executeSpecialAbility(Player currentPlayer, Tile selectedTile, Tile clickedTile, Tower tower);
}
