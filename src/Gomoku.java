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
        int numCols = GomokuModel.DEFAULT_NUM_COLS;
        return numCols;
    }

    @Override
    public int getNumRows() {
        int numRows = GomokuModel.DEFAULT_NUM_ROWS;
        return numRows;
    }

    @Override
    public int getNumInLineForWin() {
        int numInLineForWin = GomokuModel.SQUARES_IN_LINE_FOR_WIN;
        return numInLineForWin;
    }

    @Override
    public Outcome playAtLocation(int i, int i1) {
        System.out.println(i + " " + i1);
        while (gomokuBoard[i][i1] == Square.EMPTY.toChar()) {
            if (setPlayer % 2 == 0) {
                gomokuBoard[i][i1] = Square.RING.toChar();
            } else {
                gomokuBoard[i][i1] = Square.CROSS.toChar();
            }
            currentPlayer = gomokuBoard[i][i1];
            setPlayer++;
        }
        //Need to check for win here!!
        winDetectHorizontal(i, i1, currentPlayer);
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
    public void winDetectHorizontal(int yloc, int xloc, char currentPlayer) {
        int x = xloc;
        while (x > 0 && x > (xloc -
                (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1))) {
            x--;
        }
        System.out.println(x);
        int locToStop = xloc + (GomokuModel.SQUARES_IN_LINE_FOR_WIN);
        int winCount = 0;
        while (x < locToStop && x < GomokuModel.DEFAULT_NUM_COLS) {
            if (gomokuBoard[yloc][x] == currentPlayer) {
                winCount++;
            }
            x++;
            if (winCount == GomokuModel.SQUARES_IN_LINE_FOR_WIN) {
                System.out.println(currentPlayer + " wins!!!!");
                break;
            }
        }


    }


}
