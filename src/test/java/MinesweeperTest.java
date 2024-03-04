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

    @Test
    void testCheckMine() {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.checkMine(0, 0);

        assertFalse(minesweeper.board[0][0].isEnabled());
        assertTrue(minesweeper.gameOver);
        assertEquals("Game Over!", minesweeper.minesRemainingLabel.getText());
    }

    // Add more test cases for other methods and scenarios as needed
}
