package tictactoe;

public class TicTacToeBoard {

  private enum Player {

    X("X"), O("O"), NONE(".");

    private final String STR;

    Player(String str) {
      STR = str;
    }

    public Player next() {
      if (this == X) {
        return O;
      } else {
        return X;
      }
    }

    @Override
    public String toString() {
      return STR;
    }

  }

  private final Player[] BOARD;

  private Player currentPlayer;

  public TicTacToeBoard() {
    currentPlayer = Player.X;
    BOARD = new Player[9]; // 9 == rows * columns
    for (int i = 0; i < BOARD.length; i++) {
      BOARD[i] = Player.NONE;
    }
  }

  public String getCurrentPlayer() {
    return currentPlayer.toString();
  }

  public void move(int move) {
    BOARD[move] = currentPlayer;
    currentPlayer = currentPlayer.next();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < BOARD.length; i++) {
      if (i % 3 == 0) { // 3 == columns
        sb.append("\n");
      } else {
        sb.append(" ");
      }
      sb.append(BOARD[i]);
    }
    return sb.substring(1);
  }

}
