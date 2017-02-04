import cs251.lab3.GomokuModel;


/**
 * @author Michael Servilla
 * @version date 2017-02-01
 */
public class Gomoku implements GomokuModel{
    private Character[][] gomokuBoard = new Character[getNumRows()][getNumCols()];
    private int setPlayer = 0;



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
        while (gomokuBoard[i][i1] == Square.EMPTY.toChar()) {
            if (setPlayer % 2 == 0) {
                gomokuBoard[i][i1] = Square.RING.toChar();
            } else {
                gomokuBoard[i][i1] = Square.CROSS.toChar();
            }
            setPlayer++;
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
}
