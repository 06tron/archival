package mpc.sweep;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Collections;

import mpc.math.Point;

/**
 * CLASS, represents the board of a minesweeper game
 */
public class MinesweeperBoard {

    /**
     * VARIABLE, a two-dimensional array that stores all the tiles on the board
     */
    private MinesweeperTile[][] tiles;

    /**
     * VARIABLE, true if the game is over, won or lost. False if the game is still playable
     */
    private boolean isOver;

    /**
     * VARIABLE, the visual grid that represents this board, all it's tiles and it's current states
     */
    private GridItem sprite;

    /**
     * CONSTRUCTOR, creates a default board, with a given number of rows and columns
     * @param rows The number of rows of tiles on this board
     * @param columns The number of columns of tiles on this board
     */
    public MinesweeperBoard(int rows, int columns) {
        this.tiles = new MinesweeperTile[rows][columns];
        GridItem[][] tileSprites = new GridItem[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                tiles[r][c] = new MinesweeperTile(this, r,c);
                tileSprites[r][c] = tiles[r][c].getSprite();
            }
        }
        sprite = new GridItem(tileSprites);
        isOver = false;
    }

    /**
     * MUTATOR, lays bombs in the tiles of this board, and updates the tile's numbers accordingly
     *  Makes sure that no bombs are placed on the clicked tile and it's adjacent tiles
     *  The number of bombs laid will only differ from numBombs when there is not enough space available
     * @param rowClicked The row this board was clicked
     * @param columnClicked The column this board was clicked
     * @param numBombs The number of bombs to attempt to place on this board
     */
    public void generateTiles(int rowClicked, int columnClicked, int numBombs) {
        ArrayList<MinesweeperTile> clickedArea = adjacentTiles(rowClicked, columnClicked);
        clickedArea.add(tiles[rowClicked][columnClicked]);
        ArrayList<Boolean> bombBooleans = new ArrayList<>();
        for (int i = 0; i < (rows()*columns())-(clickedArea.size()); i++) {
            if (i < numBombs) {
                bombBooleans.add(true);
            } else {
                bombBooleans.add(false);
            }
        }
        Collections.shuffle(bombBooleans);
        int bombIndex = 0;
        for (int r = 0; r < rows(); r++) {
            for (int c = 0; c < columns(); c++) {
                if (!tiles[r][c].inList(clickedArea)) {
                    if (bombBooleans.get(bombIndex)) {
                        tiles[r][c].layMine();
                        ArrayList<MinesweeperTile> adjacs = adjacentTiles(r,c);
                        for (MinesweeperTile adjac : adjacs) {
                            adjac.addToNumber();
                        }
                    }
                    bombIndex++;
                }
            }
        }
    }

    /**
     * Used when this board is left clicked. Attempts to uncover the tile at the given row and column
     *  If the clicked tile is uncovered, the "sweep" function is executed
     * @param r The row on this board that the left click occurred
     * @param c The column on this board that the left click occurred
     */
    public void leftClick(int r, int c) {
        if (!isOver) {
            if (tiles[r][c].isCovered()) {
                tiles[r][c].uncover();
            } else {
                if (tiles[r][c].getNumber() == numAdjacentFlags(r,c)) { // SWEEP
                    ArrayList<MinesweeperTile> adjacs = adjacentTiles(r,c);
                    for (MinesweeperTile adjac : adjacs) {
                        if (!adjac.isFlagged() && adjac.isCovered()) {
                            adjac.uncover();
                        }
                    }
                }
            }
        }
    }

    /**
     * Used when this board is right clicked. Attempts to flag the tile at the given row and column
     * @param r The row on this board that the right click occurred
     * @param c The column on this board that the right click occurred
     */
    public void rightClick(int r, int c) {
        if (!isOver) {
            tiles[r][c].flag();
        }
    }

    public String getInfo(int r, int c) {
        return tiles[r][c].getInfo() + "\nisOver: " + isOver;
    }

    /**
     * ACCESSOR, unless the game is already over, determines if all of the non-bomb tiles have been uncovered
     * @return True if the game is over, and no longer playable. False otherwise
     */
    public boolean isOver() {
        if (!isOver) {
            for (MinesweeperTile[] row : tiles) {
                for (MinesweeperTile tile : row) {
                    if (!tile.isBomb() && tile.isCovered()) { // not a bomb and is covered
                        return false;
                    }
                }
            }
            isOver = true;
        }
        return true;
    }

    /**
     * The number of rows in this board
     * @return The number of rows in this board's tiles array
     */
    public int rows() { return tiles.length; }

    /**
     * The number of columns in this board
     * @return The number of tiles in one row of this board's tiles array
     */
    public int columns() { return tiles[0].length; }

    /**
     * Finds the all tiles adjacent to the given tile (given by it's row and column)
     * @param row The row of the tile
     * @param column The column of the tile
     * @return An array list of tiles adjacent to the tile on this board determined by the row and column
     */
    public ArrayList<MinesweeperTile> adjacentTiles(int row, int column) {
        ArrayList<MinesweeperTile> adjacs = new ArrayList<>();
        if (column > 0) adjacs.add(tiles[row][column-1]); // not a tile in the leftmost column
        if (column < columns()-1) adjacs.add(tiles[row][column+1]); // not a tile in the rightmost column
        if (row > 0) { // not a tile on the top row
            adjacs.add(tiles[row-1][column]);
            if (column > 0) adjacs.add(tiles[row-1][column-1]);
            if (column < columns()-1) adjacs.add(tiles[row-1][column+1]);
        }
        if (row < rows()-1) { // not a tile of the bottom row
            adjacs.add(tiles[row+1][column]);
            if (column > 0) adjacs.add(tiles[row+1][column-1]);
            if (column < columns()-1) adjacs.add(tiles[row+1][column+1]);
        }
        return adjacs;
    }

    /**
     * ACCESSOR, counts the number of tiles that are flagged, adjacent to the tile given by a row and a column
     * @param row The row of the tile to count around
     * @param column The column of the tile to count around
     * @return a total number of flagged tiles that are adjacent to the given tile
     */
    public int numAdjacentFlags(int row, int column) {
        int count = 0;
        for (MinesweeperTile adjac : adjacentTiles(row, column)) {
            if (adjac.isFlagged()) count++;
        }
        return count;
    }

    /**
     * ACCESSOR, counts the number of tiles that are covered, adjacent to the tile given by a row and a column
     * @param row The row of the tile to count around
     * @param column The column of the tile to count around
     * @return a total number of covered tiles that are adjacent to the given tile
     */
    public int numAdjacentCovered(int row, int column) {
        int count = 0;
        for (MinesweeperTile adjac : adjacentTiles(row, column)) {
            if (adjac.isCovered()) count++;
        }
        return count;
    }

    /**
     * MUTATOR, used by the a tile to let this board know that a bomb has been uncovered
     *  isOver is set to true, and all un-flagged bombs are uncovered
     *  If a tile is not a bomb, but is flagged, it is set to have a correction sprite
     */
    public void bombUncovered() {
        isOver = true;
        for (int r = 0; r < rows(); r++) {
            for (int c = 0; c < columns(); c++) {
                if (tiles[r][c].isBomb()) {
                    tiles[r][c].uncover();
                } else if (tiles[r][c].isFlagged()) {
                    tiles[r][c].notBomb();
                }
            }
        }

    }

    /**
     * ACCESSOR, draws a certain tile to a given PApplet, with given corners
     * @param row The row of the tile to be drawn
     * @param column The column of the tile to be drawn
     * @param points A length four array of points to be the corners of the quadrilateral drawing
     * @param p The PApplet that this tile should be drawn to
     */
    public void renderTile(int row, int column, Point[] points, PApplet p) {
        GridItem tileSprite = tiles[row][column].getSprite();
        // tileSprite.updatePoints(points);
        tileSprite.draw(p);
    }

    /**
     * MUTATOR, flags tiles on this board that a player could easily determine are bombs
     *  If a tile on this board is uncovered, and it's number (the number of adjacent tiles that are bombs) matches
     *   the the number of adjacent tiles that are covered, then those covered tiles are flagged
     */
    public void tabFlag() {
        if (!isOver) {
            for (int r = 0; r < rows(); r++) {
                for (int c = 0; c < columns(); c++) {
                    if (!tiles[r][c].isCovered() && tiles[r][c].getNumber() != 0 &&
                            tiles[r][c].getNumber() == numAdjacentCovered(r,c)) {
                        ArrayList<MinesweeperTile> adjacs = adjacentTiles(r, c);
                        for (MinesweeperTile adjac : adjacs) {
                            if (adjac.isCovered() && !adjac.isFlagged()) {
                                adjac.flag();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * MUTATOR, uncovers tiles that a player could easily determine are not bombs, assuming correct flagging
     *  If a tile is uncovered, enterUncover will initiate the "sweep" function by way of left clicking
     */
    public void enterUncover() {
        if (!isOver) {
            for (int r = 0; r < rows(); r++) {
                for (int c = 0; c < columns(); c++) {
                    if (!tiles[r][c].isCovered() && tiles[r][c].getNumber() != 0) {
                        leftClick(r,c);
                    }
                }
            }
        }
    }

    /**
     * ACCESSOR, returns this board's sprite, without the points (update later)
     * @return The sprite GridItem that represents this board
     */
    public GridItem getSprite() { return sprite; }

}
