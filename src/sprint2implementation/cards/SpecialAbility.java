package sprint2implementation.cards;

import sprint2implementation.characters.Player;
import sprint2implementation.grounds.Tile;
import sprint2implementation.towers.Tower;


public interface SpecialAbility {
    boolean executeSpecialAbility(Player currentPlayer, Tile selectedTile, Tile clickedTile, Tower tower);
}
