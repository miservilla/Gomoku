import cs251.lab3.GomokuModel;

/**
 * @author Michael Servilla
 * @version date 2017-02-01
 */
public class Gomoku implements GomokuModel{
//    private String EMPTY = "-";
    private String[][] gomokuBoard = new String[getNumRows()][getNumCols()];


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
        return Outcome.GAME_NOT_OVER;
    }

    @Override
    public void startNewGame() {
        for (int row = 0; row < getNumRows(); row++) {
            for (int column = 0; column < getNumCols(); column++) {
                gomokuBoard[row][column] = "-";
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
