package mpc.sweep;

import java.util.ArrayList;

/**
 * CLASS, represents a single tile on a minesweeper board
 */
public class MinesweeperTile {

    /**
     * VARIABLE, true if this tile is covered. Will always be true if isFlagged is true
     */
    private boolean isCovered;

    /**
     * VARIABLE, true if this tile is flagged. Only possible while isCovered is true
     */
    private boolean isFlagged;

    /**
     * VARIABLE, true if this tile is a bomb, false otherwise
     */
    private boolean isBomb;

    /**
     * VARIABLE, the supposed number of bombs adjacent to this tile
     */
    private int number;

    /**
     * VARIABLE, the visual grid that represents this tile and it's current state
     */
    private GridItem sprite;

    /**
     * VARIABLE, the board that this tile is a part of
     */
    private MinesweeperBoard board;

    /**
     * VARIABLES, the row and column location of this tile on this tile's board
     */
    private int row, column;

    /* TODO VARIABLE, a 3D representation of this tile.
    private Cuboid tile3D; */

    /**
     * CONSTRUCTOR, sets all the instance variables of this class
     *  Sets this tile's board, row, and column according to this constructor's parameters
     *  isCovered is true and isFlagged is false, as the tiles on a new minesweeper board are covered and not flagged
     *  This tile's sprite is initially set to a covered tile, in accordance with the isCovered boolean
     *  isBomb is false and the number is zero, because bombs have not yet been placed
     * @param board The board on which this tile is located
     * @param row This tile's row on it's board
     * @param column This tile's column on it's board
     */
    public MinesweeperTile(MinesweeperBoard board, int row, int column) {
        this.board = board;
        this.row = row;
        this.column = column;
        isCovered = true;
        isFlagged = false;
        sprite = new GridItem();
        sprite.makeScreen(MinesweeperTemplates.COVERED_TILE);
        isBomb = false;
        number = 0;
        // tile3D = new Cuboid();
    }

    /**
     * ACCESSOR, returns this tile's isCovered variable
     * @return true if this tile is covered, false otherwise
     */
    public boolean isCovered() { return isCovered; }

    /**
     * ACCESSOR, returns this tile's isFlagged variable
     * @return true if this tile is flagged, false otherwise
     */
    public boolean isFlagged() { return isFlagged; }

    /**
     * ACCESSOR, returns this tile's isBomb variable
     * @return true if this tile is a bomb, false otherwise
     */
    public boolean isBomb() { return isBomb; }

    /**
     * MUTATOR, sets this tile's isBomb variable to true
     */
    public void layMine() { isBomb = true; }

    /**
     * MUTATOR, adds one to this tile's current number
     */
    public void addToNumber() { number++; }

    /**
     * ACCESSOR, returns this tile's number variable
     * @return the supposed number of bombs adjacent to this tile
     */
    public int getNumber() { return number; }

    /**
     * MUTATOR, sets the values of the isFlagged boolean and the sprite GridItem,
     *  isFlagged is false if flagged, true if covered but not flagged, unchanged if uncovered
     *  When isFlagged is set to true, the sprite changes to a flagged tile
     *  When isFlagged is set to false, the sprite reverts to a covered tile
     */
    public void flag() {
        if (isFlagged) {
            isFlagged = false;
            sprite.makeScreen(MinesweeperTemplates.COVERED_TILE);
        } else if (isCovered) {
            isFlagged = true;
            sprite.makeScreen(MinesweeperTemplates.FLAGGED_TILE);
        }
    }

    /**
     * MUTATOR, sets the values of the isCovered boolean and the sprite GridItem, as well as uncovering other tiles
     *  Does nothing unless this tile is covered and not flagged, otherwise isCovered is set to false
     *  If this tile is a bomb and the game is not yet over, the sprite becomes a red bomb and the board is signaled
     *  If this tile is a bomb and the game is over, the sprite becomes a normal bomb
     *  If this tile is not a bomb, the sprite changes to show this tile's number
     *  If this tile is not a bomb and has no adjacent bombs, all adjacent tiles are uncovered if possible
     */
    public void uncover() {
        if (isCovered && !isFlagged) {
            isCovered = false;
            if (isBomb) {
                if (board.isOver()) {
                    sprite.makeScreen(MinesweeperTemplates.BOMB);
                } else {
                    sprite.makeScreen(MinesweeperTemplates.RED_BOMB);
                    board.bombUncovered();
                }
            } else {
                sprite.makeScreen(MinesweeperTemplates.NUMBERS[number]);
                if (number == 0) {
                    ArrayList<MinesweeperTile> adjacs = board.adjacentTiles(row,column);
                    for (MinesweeperTile adjac : adjacs) {
                        adjac.uncover();
                    }
                }
            }
        }
    }

    public String getInfo() {
        return "row: " + row + ", column: " + column +
                "\nnumber: " + number +
                "\nisBomb: " + isBomb +
                "\nisCovered: " + isCovered +
                "\nisFlagged: " + isFlagged;
    }

    /**
     * MUTATOR, changes this tile's sprite variable to represent a bomb under a red 'X'
     */
    public void notBomb() { sprite.makeScreen(MinesweeperTemplates.NOT_BOMB); }

    /**
     * ACCESSOR, returns this tile's sprite, without the points (update later)
     * @return The sprite GridItem that represents this
     */
    public GridItem getSprite() {
        return sprite;
    }

    /**
     * A method that determines if this tile is in an ArrayList of tiles
     * @param tiles The list of tiles that may or may not include a pointer to this tile
     * @return true if this tile was somewhere in the list, false otherwise
     */
    public boolean inList(ArrayList<MinesweeperTile> tiles) {
        for (MinesweeperTile tile : tiles) {
            if (equals(tile)) return true;
        }
        return false;
    }

    /* TODO ACCESSOR, returns this tile's cuboid, with current features
    public Cuboid getTile3D() { return tile3D; } */

}
