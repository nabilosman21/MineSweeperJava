import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Minesweeper {
    private class MineTile extends JButton {
        int row;
        int col;

        public MineTile(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static final int TILE_SIZE = 70;
    private static final int NUM_ROWS = 8;
    private static final int NUM_COLS = NUM_ROWS;
    private static final int BOARD_WIDTH = NUM_COLS * TILE_SIZE;
    private static final int BOARD_HEIGHT = NUM_ROWS * TILE_SIZE;
    private static final int MINE_COUNT = 10;

    private JFrame frame = new JFrame("Minesweeper");
    private JLabel minesRemainingLabel = new JLabel();
    private JPanel headerPanel = new JPanel();
    private JPanel boardPanel = new JPanel();

    private MineTile[][] board = new MineTile[NUM_ROWS][NUM_COLS];
    private ArrayList<MineTile> mineList;
    private Random random = new Random();

    private int tilesClicked = 0;
    private boolean gameOver = false;

    public Minesweeper() {
        frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        minesRemainingLabel.setFont(new Font("Arial", Font.BOLD, 25));
        minesRemainingLabel.setHorizontalAlignment(JLabel.CENTER);
        minesRemainingLabel.setText("Minesweeper: " + MINE_COUNT);
        minesRemainingLabel.setOpaque(true);

        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(minesRemainingLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(NUM_ROWS, NUM_COLS));
        frame.add(boardPanel);

        initializeBoard();
        frame.setVisible(true);

        setMines();
    }

    private void initializeBoard() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                MineTile tile = new MineTile(row, col);
                board[row][col] = tile;

                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (gameOver) {
                            return;
                        }
                        MineTile clickedTile = (MineTile) e.getSource();

                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (clickedTile.getText().isEmpty()) {
                                if (mineList.contains(clickedTile)) {
                                    revealMines();
                                } else {
                                    checkMine(clickedTile.row, clickedTile.col);
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (clickedTile.getText().isEmpty() && clickedTile.isEnabled()) {
                                clickedTile.setText("🚩");
                            } else if (clickedTile.getText().equals("🚩")) {
                                clickedTile.setText("");
                            }
                        }
                    }
                });

                boardPanel.add(tile);
            }
        }
    }

    private void setMines() {
        mineList = new ArrayList<>();

        int minesLeft = MINE_COUNT;
        while (minesLeft > 0) {
            int row = random.nextInt(NUM_ROWS);
            int col = random.nextInt(NUM_COLS);

            MineTile tile = board[row][col];
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                minesLeft--;
            }
        }
    }

    private void revealMines() {
        for (MineTile mine : mineList) {
            mine.setText("💣");
        }

        gameOver = true;
        minesRemainingLabel.setText("Game Over!");
    }

    private void checkMine(int row, int col) {
        if (row < 0 || row >= NUM_ROWS || col < 0 || col >= NUM_COLS) {
            return;
        }

        MineTile tile = board[row][col];
        if (!tile.isEnabled()) {
            return;
        }
        tile.setEnabled(false);
        tilesClicked += 1;

        int minesFound = 0;

        minesFound += countMine(row - 1, col - 1); // top left
        minesFound += countMine(row - 1, col); // top
        minesFound += countMine(row - 1, col + 1); // top right

        minesFound += countMine(row, col - 1); // left
        minesFound += countMine(row, col + 1); // right

        minesFound += countMine(row + 1, col - 1); // bottom left
        minesFound += countMine(row + 1, col); // bottom
        minesFound += countMine(row + 1, col + 1); // bottom right

        if (minesFound > 0) {
            tile.setText(Integer.toString(minesFound));
        } else {
            tile.setText("");

            checkMine(row - 1, col - 1); // top left
            checkMine(row - 1, col); // top
            checkMine(row - 1, col + 1); // top right

            checkMine(row, col - 1); // left
            checkMine(row, col + 1); // right

            checkMine(row + 1, col - 1); // bottom left
            checkMine(row + 1, col); // bottom
            checkMine(row + 1, col + 1); // bottom right
        }

        if (tilesClicked == NUM_ROWS * NUM_COLS - mineList.size()) {
            gameOver = true;
            minesRemainingLabel.setText("Mines Cleared!");
        }
    }

    private int countMine(int row, int col) {
        if (row < 0 || row >= NUM_ROWS || col < 0 || col >= NUM_COLS) {
            return 0;
        }
        if (mineList.contains(board[row][col])) {
            return 1;
        }
        return 0;
    }


}
