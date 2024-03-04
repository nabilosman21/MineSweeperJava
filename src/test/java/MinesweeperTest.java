import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinesweeperTest {

    @Test
    void testRevealMines() {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.revealMines();

        assertTrue(minesweeper.gameOver);
        assertEquals("Game Over!", minesweeper.minesRemainingLabel.getText());
    }
}