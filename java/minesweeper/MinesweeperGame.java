package mpc.sweep;

import mpc.math.Point;
import processing.core.PApplet;

/**
 * CLASS, the minesweeper game
 */
public class MinesweeperGame {

    /**
     * VARIABLE, the 2D, playable version of this minesweeper game's board
     */
    private MinesweeperBoard board;

    /**
     * VARIABLE, the point at the northwest corner of the rectangular board
     */
    private Point boardNW;

    /**
     * VARIABLE, the x width of the rectangular board
     */
    private double boardWidth;

    /**
     * VARIABLE, the y height of the rectangular board
     */
    private double boardHeight;

    /* TODO VARIABLE, a 3D representation of the board and all it's tiles
    private Cuboid board3D; */

    /**
     * CONSTRUCTOR, creates a minesweeper game with a board, in a rectangular space determined by parameters
     * @param boardNW The northwest corner of the rectangular board space, used in going from (x,y) to (r,c)
     * @param boardWidth The x width of the rectangular space for the game's board
     * @param boardHeight The y height of the rectangular space for the game's board
     * @param rows The number of rows to be put onto this game's board
     * @param columns The number of columns to be put onto this game's board
     */
    public MinesweeperGame(Point boardNW, double boardWidth, double boardHeight, int rows, int columns) {
        this.boardNW = boardNW;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        board = new MinesweeperBoard(rows, columns);
    }

    /**
     * ACCESSOR, returns this minesweeper game's playable board
     * @return The 2D, playable version of this game's board
     */
    public MinesweeperBoard getBoard() { return board; }

    public Point[] getBoardPoints() {
        double[] neCoords = {boardNW.get(Point.X) + boardWidth, boardNW.get(Point.Y)};
        Point ne = new Point(neCoords);
        double[] seCoords = {boardNW.get(Point.X) + boardWidth, boardNW.get(Point.Y) + boardHeight};
        Point se = new Point(seCoords);
        double[] swCoords = {boardNW.get(Point.X), boardNW.get(Point.Y) + boardHeight};
        Point sw = new Point(swCoords);
        Point[] boardPoints = {boardNW, ne, se, sw};
        return boardPoints;
    }

    /**
     * ACCESSOR, calculates the width of a tile on the rectangular board
     * @return The width of the rectangular board divided by the number of columns
     */
    public double tileWidth() { return boardWidth / board.columns(); }

    /**
     * ACCESSOR, calculates the length of a tile on the rectangular board
     * @return The length of the rectangular board divided by the number of rows
     */
    public double tileHeight() { return boardHeight / board.rows(); }

    /**
     * MUTATOR, calls the left click method in this game's board, converting x and y to row and column
     * @param x The x-coordinate at which the left click occurred
     * @param y The y-coordinate at which the left click occurred
     */
    public void leftClick(double x, double y) {
        if (x > 0 && x < boardWidth && y > 0 && y < boardHeight) {
            int row = (int) (x / tileWidth());
            int column = (int) (y / tileHeight());
            board.leftClick(row, column);
        }
    }

    /**
     * MUTATOR, calls the right click method in this game's board, converting x and y to row and column
     * @param x The x-coordinate at which the right click occurred
     * @param y The y-coordinate at which the right click occurred
     */
    public void rightClick(double x, double y) {
        if (x > 0 && x < boardWidth && y > 0 && y < boardHeight) {
            int row = (int) (x / tileWidth());
            int column = (int) (y / tileHeight());
            board.rightClick(row, column);
        }
    }

    public String getInfo(double x, double y) {
        if (x > 0 && x < boardWidth && y > 0 && y < boardHeight) {
            int row = (int) (x / tileWidth());
            int column = (int) (y / tileHeight());
            return board.getInfo(row, column) +
                    "\nboardNW: " + boardNW +
                    "\nboardWidth: " + boardWidth +
                    "\nboardHeight: " + boardHeight;
        }
        return "outside of board";
    }

    /**
     * MUTATOR, calls the method to generate tiles in this game's board, converting x and y to row and column
     * @param x The x-coordinate at which the right click occurred
     * @param y The y-coordinate at which the right click occurred
     * @param numBombs The number of bombs to be placed on this game's board when generated
     */
    public void generateTiles(double x, double y, int numBombs) {
        if (x > 0 && x < boardWidth && y > 0 && y < boardHeight) {
            int row = (int) (x / tileWidth());
            int column = (int) (y / tileHeight());
            board.generateTiles(row, column, numBombs);
        }
    }

    /**
     * ACCESSOR, calls this board's isOver() method
     * @return true if this game is over
     */
    public boolean isOver() { return board.isOver(); }

    /* TODO ACCESSOR, returns the Cuboid representation of this game's 3D board
    public Cuboid getBoard3D() { return board3D; } */

    /**
     * ACCESSOR, draws out this minesweeper game's board, with this game's board location
     * @param p The processing PApplet for this game's board to be rendered on
     */
    public void render(PApplet p) {
        for (int r = 0; r < board.rows(); r++) {
            for (int c = 0; c < board.columns(); c++) {
                Point nw = boardNW;
                double[] nwCoords = {nw.get(Point.X) + tileWidth()*(r), nw.get(Point.Y) + tileHeight()*(c)};
                double[] neCoords = {nw.get(Point.X) + tileWidth()*(r+1), nw.get(Point.Y) + tileHeight()*(c)};
                double[] seCoords = {nw.get(Point.X) + tileWidth()*(r+1), nw.get(Point.Y) + tileHeight()*(c+1)};
                double[] swCoords = {nw.get(Point.X) + tileWidth()*(r), nw.get(Point.Y) + tileHeight()*(c+1)};
                Point smlNW = new Point(nwCoords);
                Point smlNE = new Point(neCoords);
                Point smlSE = new Point(seCoords);
                Point smlSW = new Point(swCoords);
                Point smlPoints[] = {smlNW, smlNE, smlSE, smlSW};
                board.renderTile(r,c,smlPoints,p);
            }
        }
    }

    public void testRender(PApplet p) {
        GridItem boardSprite = board.getSprite();
        // boardSprite.updatePoints(getBoardPoints());
        boardSprite.draw(p);
    }

}
