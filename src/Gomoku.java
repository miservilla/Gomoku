import cs251.lab3.GomokuModel;


/**
 * @author Michael Servilla
 * @version date 2017-02-01
 */
public class Gomoku implements GomokuModel{
    private char[][] gomokuBoard = new char[getNumRows()][getNumCols()];
    private int setPlayer = 0;
    private char currentPlayer;



    @Override
    public int getNumCols() {
        return GomokuModel.DEFAULT_NUM_COLS;
    }

    @Override
    public int getNumRows() {
        return GomokuModel.DEFAULT_NUM_ROWS;
    }

    @Override
    public int getNumInLineForWin() {
        return GomokuModel.SQUARES_IN_LINE_FOR_WIN;
    }

    @Override
    public Outcome playAtLocation(int row, int column) {
        System.out.println(row + " " + column);
        while (gomokuBoard[row][column] == Square.EMPTY.toChar()) {
            if (setPlayer % 2 == 0) {
                gomokuBoard[row][column] = Square.RING.toChar();
            } else {
                gomokuBoard[row][column] = Square.CROSS.toChar();
            }
            currentPlayer = gomokuBoard[row][column];
            setPlayer++;
        }
        //Need to check for win here!!
        winDetectHorizontal(row, column, currentPlayer);
        winDetectVertical(row, column, currentPlayer);
        winDetectPositiveDiagonal(row, column, currentPlayer);
        winDetectNegativeDiagonal(row, column, currentPlayer);
        if (!getBoardString().contains("-")) { //Check for draw.
            return Outcome.DRAW;
        }
        return Outcome.GAME_NOT_OVER;
    }

    @Override
    public void startNewGame() {
        for (int row = 0; row < getNumRows(); row++) {
            for (int column = 0; column < getNumCols(); column++) {
                gomokuBoard[row][column] = Square.EMPTY.toChar();
            }
        }
    }

    @Override
    public String getBoardString() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < getNumRows(); row++) {
            for (int column = 0; column < getNumCols(); column++) {
                boardString.append(gomokuBoard[row][column]);
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    @Override
    public void setComputerPlayer(String s) {

    }
    private void winDetectHorizontal(int rowLoc, int columnLoc, char currentPlayer) {
        int x = columnLoc;
        while (x > 0 && x > (columnLoc -
                (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1))) {
            x--;
        }
        int locToStop = columnLoc + (GomokuModel.SQUARES_IN_LINE_FOR_WIN);
        int winCount = 0;
        while (x < locToStop && x < GomokuModel.DEFAULT_NUM_COLS) {
            if (gomokuBoard[rowLoc][x] == currentPlayer) {
                winCount++;
            } else winCount = 0;
            x++;
            if (winCount == GomokuModel.SQUARES_IN_LINE_FOR_WIN) {
                System.out.println(currentPlayer + " wins!!!!");
                break;
            }
        }
    }
    private void winDetectVertical(int rowLoc, int columnLoc, char currentPlayer) {
        int y = rowLoc;
        while (y > 0 && y > (rowLoc -
                (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1))) {
            y--;
        }
        int locToStop = rowLoc + (GomokuModel.SQUARES_IN_LINE_FOR_WIN);
        int winCount = 0;
        while (y < locToStop && y < GomokuModel.DEFAULT_NUM_ROWS) {
            if (gomokuBoard[y][columnLoc] == currentPlayer) {
                winCount++;
            } else winCount = 0;
            y++;
            if (winCount == GomokuModel.SQUARES_IN_LINE_FOR_WIN) {
                System.out.println(currentPlayer + " wins!!!!");
                break;
            }
        }
    }
    private void winDetectPositiveDiagonal(int rowLoc, int columnLoc,
                                           char currentPlayer) {
        int y = rowLoc;
        int x = columnLoc;
        while (y > 0 && y > (rowLoc -
                (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1)) && x > 0 && x >
                (columnLoc - (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1))) {
            y++;
            x--;
        }
        int rowLocToStop = rowLoc - (GomokuModel.SQUARES_IN_LINE_FOR_WIN);
        int columnLocToStop = columnLoc + (GomokuModel.SQUARES_IN_LINE_FOR_WIN);
        int winCount = 0;
        while (y > rowLocToStop && y < GomokuModel.DEFAULT_NUM_ROWS &&
                x < columnLocToStop && x < GomokuModel.DEFAULT_NUM_COLS &&
                y >= 0) {
            if (gomokuBoard[y][x] == currentPlayer) {
                winCount++;
            } else winCount = 0;
            y--;
            x++;
            if (winCount == GomokuModel.SQUARES_IN_LINE_FOR_WIN) {
                System.out.println(currentPlayer + " wins!!!!");
                break;
            }
        }

    }
    private void winDetectNegativeDiagonal(int rowLoc, int columnLoc,
                                           char currentPlayer) {
        int y = rowLoc;
        int x = columnLoc;
        while (y > 0 && y < (rowLoc +
                (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1)) && x > 0 && x >
                (columnLoc - (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1))) {
            y++;
            x++;
        }
        int rowLocToStop = rowLoc - (GomokuModel.SQUARES_IN_LINE_FOR_WIN);
        int columnLocToStop = columnLoc - (GomokuModel.SQUARES_IN_LINE_FOR_WIN);
        int winCount = 0;
        while (y > rowLocToStop && y < GomokuModel.DEFAULT_NUM_ROWS &&
                x > columnLocToStop && x < GomokuModel.DEFAULT_NUM_COLS &&
                x >= 0 && y >= 0) {
            if (gomokuBoard[y][x] == currentPlayer) {
                winCount++;
            } else winCount = 0;
            y--;
            x--;
            if (winCount == GomokuModel.SQUARES_IN_LINE_FOR_WIN) {
                System.out.println(currentPlayer + " wins!!!!");
                break;
            }
        }

    }



}
