package prototype.tiffany_lau_kho_jen;
import java.awt.*;
public class PlayerMovement {

    public static boolean isValidMove(Point from, Point to, int fromLevel, int toLevel, Player playerOne,
    Player playerTwo) {
        if (to.x < 0 || to.y < 0 || to.x >= 5 || to.y >= 5) {
            return false;
        }

        if (Math.abs(from.x - to.x) > 1 || Math.abs(from.y - to.y) > 1) {
            return false;
        }

        if ((playerOne.getRow() == to.y && playerOne.getColumn() == to.x) || (playerTwo.getRow() == to.y
        && playerTwo.getColumn() == to.x)) {
            return false;
        }

        if (toLevel >= 4) {
            return false;
        }

        if (toLevel > fromLevel + 1) {
            return false;
        }

        return true;
    }
}
