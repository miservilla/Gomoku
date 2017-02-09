import cs251.lab3.GomokuGUI;
import cs251.lab3.GomokuModel;

import java.util.Random;
import java.util.Scanner;


/**Program to play Gomoku, eiter with partner or against computer.
 * Class 251
 * @author Michael Servilla
 * @version date 2017-02-01
 */
public class Gomoku implements GomokuModel{
    private static String answer;

    /**
     *
     * @param args Not used for the program, scanner used instead for input.
     */
    public static void main(String[] args) {
        Gomoku game = new Gomoku();

        //Question to play computer or not.
        Scanner ask = new Scanner(System.in);
        System.out.println("Do you want to play the computer? " +
                "Y or N");
        answer = ask.next();
        if (answer.toUpperCase().equals("Y")) {
            game.setComputerPlayer("Y");
        }
        GomokuGUI.showGUI(game);
    }

    private char[][] gomokuBoard = new char[getNumRows()][getNumCols()];
    private int setPlayer = 0;
    private char currentPlayer;
    Random rand = new Random();

    /**
     *Accessor to obtain default number of columns (board width).
     * @return Returns default column value.
     */
    @Override
    public int getNumCols() {
        return GomokuModel.DEFAULT_NUM_COLS;
    }

    /**
     *Accessor to obtain default number of rows (board height).
     * @return Returns default row value.
     */
    @Override
    public int getNumRows() {
        return GomokuModel.DEFAULT_NUM_ROWS;
    }

    /**
     *Accessor to obtain default number of similar squares in a row to win the
     * game.
     * @return Returns default values of similar squares in a row to win.
     */
    @Override
    public int getNumInLineForWin() {
        return GomokuModel.SQUARES_IN_LINE_FOR_WIN;
    }

    /**
     *Method to obtain click event location and to return outcome (WIN, DRAW, or
     * GAME NOT OVER).
     * @param row Row location of event.
     * @param column Column location of event.
     * @return Returns outcome of particular event.
     */
    @Override
    public Outcome playAtLocation(int row, int column) {
        if (answer.toUpperCase().equals("Y")) {
                                currentPlayer = Square.RING.toChar();
            while (gomokuBoard[row][column] != Square.EMPTY.toChar()) {
                return Outcome.GAME_NOT_OVER;
            }
            gomokuBoard[row][column] = currentPlayer;
            Outcome outcomeResult = winDetection(row, column, currentPlayer);
            if (outcomeResult.equals(Outcome.RING_WINS)) {
                return outcomeResult;
            }
            outcomeResult = copyCatComputer(row, column);
            if (outcomeResult.equals(Outcome.CROSS_WINS)) {
                return outcomeResult;
            }
            return outcomeResult;
        } else {
            getCurrentPlayer(row, column);
            Outcome outcomeResult = winDetection(row, column, currentPlayer);
            return outcomeResult;
        }
    }

    /**
     *Method to determine current player (Ring or Cross) when not playing
     *  against the computer.
     * @param row Row location of square for particular player.
     * @param column Column location of square for particular player.
     * @return Returns the location of square that is denoted with either Ring
     * or cross.
     */
    private char getCurrentPlayer(int row, int column) {
        while (gomokuBoard[row][column] == Square.EMPTY.toChar()) {
            if (setPlayer % 2 == 0) {
                gomokuBoard[row][column] = Square.RING.toChar();
            } else {
                gomokuBoard[row][column] = Square.CROSS.toChar();
            }
            currentPlayer = gomokuBoard[row][column];
            setPlayer++;
        }
        return gomokuBoard[row][column];
    }

    /**
     *Method to fill board with empty squares (dashes).
     */
    @Override
    public void startNewGame() {
        for (int row = 0; row < getNumRows(); row++) {
            for (int column = 0; column < getNumCols(); column++) {
                gomokuBoard[row][column] = Square.EMPTY.toChar();
            }
        }
    }

    /**
     * Method to create string representation of board.
     * @return Returns single string array with \n used to denote next row.
     */
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

    /**
     *
     * @param s
     */
    @Override
    public void setComputerPlayer(String s) {
        if (s.toUpperCase().equals("Y")) {
//            int row = rand.nextInt(getNumRows());
//            int column = rand.nextInt(getNumCols());
//            while (gomokuBoard[row][column] == Square.EMPTY.toChar())
//            gomokuBoard[row][column]
//                    = Square.CROSS.toChar();
        }
    }

    /**
     * Method for computer player to choose random moves.
     * @return Returns random location with cross.
     */
    private char retardedComputer() {
        int row;
        int column;
        do {
            row = rand.nextInt(getNumRows());
            column = rand.nextInt(getNumCols());
        } while (gomokuBoard[row][column] != Square.EMPTY.toChar());

        gomokuBoard[row][column] = Square.CROSS.toChar();
        return gomokuBoard[row][column];
    }

    /**
     *Method for slightly more advanced computer player using location of
     * opponents click to determine its move.
     * @param row Row information from previous opponents click event.
     * @param column Column information from previous opponents click event.
     * @return Returns outcome of its move by calling win detection.
     */
    private Outcome copyCatComputer(int row, int column) {
        int y = row;
        int y1 = row;
        int y2 = row;
        int x = column;
        int x1 = column;
        int x2 = column;
        if (x != 29 && gomokuBoard[y][++x1] == Square.EMPTY.toChar()) {
            column++;
        } else if (x != 0 && gomokuBoard[y][--x2] == Square.EMPTY.toChar()) {
            column--;
        } else if (y != 29 && gomokuBoard[++y1][x] == Square.EMPTY.toChar()) {
            row++;
        } else if (y != 0 && gomokuBoard[--y2][x] == Square.EMPTY.toChar()) {
            row--;
        } else {
            do {
                row = rand.nextInt(getNumRows());
                column = rand.nextInt(getNumCols());
            } while (gomokuBoard[row][column] != Square.EMPTY.toChar());
        }
        gomokuBoard[row][column] = Square.CROSS.toChar();
        currentPlayer = Square.CROSS.toChar();
        return winDetection(row, column, currentPlayer);
    }

    /**
     *
     * @param row
     * @param column
     * @return
     */
    private char getHumanMove(int row, int column) {
        while (gomokuBoard[row][column] == Square.EMPTY.toChar()) {
        }
        return gomokuBoard[row][column];
    }

    /**
     * Method to determine if a given move has resulted in a win. This method
     * call 4 additional methods for win detection in each of the given
     * directions. Will also check for draw on each move.
     * @param row Row information from current click event.
     * @param column Column information from current click event.
     * @param currentPlayer Current player associated with click event.
     * @return Returns outcome WIN if win detected, otherwise GAME NOT OVER or
     * DRAW.
     */
    private Outcome winDetection(int row, int column, char currentPlayer) {
        Outcome outcomeResult = winDetectHorizontal(row, column, currentPlayer);
        if (outcomeResult != Outcome.GAME_NOT_OVER) {
            setPlayer++;
            return outcomeResult;
        }
        outcomeResult = winDetectVertical(row, column, currentPlayer);
        if (outcomeResult != Outcome.GAME_NOT_OVER) {
            setPlayer++;
            return outcomeResult;
        }
        outcomeResult = winDetectPositiveDiagonal(row, column, currentPlayer);
        if (outcomeResult != Outcome.GAME_NOT_OVER) {
            setPlayer++;
            return outcomeResult;
        }
        outcomeResult = winDetectNegativeDiagonal(row, column, currentPlayer);
        if (outcomeResult != Outcome.GAME_NOT_OVER) {
            setPlayer++;
            return outcomeResult;
        }
        if (!getBoardString().contains("-")) { //Check for draw.
            return Outcome.DRAW;
        }
        return Outcome.GAME_NOT_OVER;
    }

    /**
     * Win detection algorithm for horizontal check. Checks back 4 locations
     * from current click event location, and then increments through total of 2
     * times default number - 1.
     * @param rowLoc Row information from current click event.
     * @param columnLoc Column information from current click event.
     * @param currentPlayer Current player associated with click event.
     * @return Returns outcome WIN if win detected with associated player, ring
     * or cross, otherwise GAME NOT OVER.
     */
    private Outcome winDetectHorizontal(int rowLoc, int columnLoc,
                                        char currentPlayer) {
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
                if (currentPlayer == Square.CROSS.toChar()) {
                    return Outcome.CROSS_WINS;
                } else return Outcome.RING_WINS;
            }
        }
        return Outcome.GAME_NOT_OVER;
    }

    /**
     * Win detection algorithm for verticle check. Checks back 4 locations
     * from current click event location, and then increments through total of 2
     * times default number - 1.
     * @param rowLoc Row information from current click event.
     * @param columnLoc Column information from current click event.
     * @param currentPlayer Current player associated with click event.
     * @return Returns outcome WIN if win detected with associated player, ring
     * or cross, otherwise GAME NOT OVER.
     */
    private Outcome winDetectVertical(int rowLoc, int columnLoc,
                                      char currentPlayer) {
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
                if (currentPlayer == Square.CROSS.toChar()) {
                    return Outcome.CROSS_WINS;
                } else return Outcome.RING_WINS;
            }
        }
        return Outcome.GAME_NOT_OVER;
    }

    /**
     * Win detection algorithm for positive diagonal check. Checks back 4
     * locations from current click event location, and then increments through
     * total of 2 times default number - 1.
     * @param rowLoc Row information from current click event.
     * @param columnLoc Column information from current click event.
     * @param currentPlayer Current player associated with click event.
     * @return Returns outcome WIN if win detected with associated player, ring
     * or cross, otherwise GAME NOT OVER.
     */
    private Outcome winDetectPositiveDiagonal(int rowLoc, int columnLoc,
                                           char currentPlayer) {
        int y = rowLoc;
        int x = columnLoc;
        while ((y < (rowLoc + (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1))) &&
                (y < (getNumRows() - 1)) &&
                x > (columnLoc - (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1)) &&
                (x > 0)) {
            y++;
            x--;
        }
        int rowLocToStop = rowLoc - (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1);
        int columnLocToStop = columnLoc +
                (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1);
        int winCount = 0;
        while (y >= rowLocToStop && x <= columnLocToStop && x >= 0 &&
                y >= 0 && x < getNumCols()) {
            if (gomokuBoard[y][x] == currentPlayer) {
                winCount++;
            } else winCount = 0;
            y--;
            x++;
            if (winCount == GomokuModel.SQUARES_IN_LINE_FOR_WIN) {
                if (currentPlayer == Square.CROSS.toChar()) {
                    return Outcome.CROSS_WINS;
                } else return Outcome.RING_WINS;
            }
        }
        return Outcome.GAME_NOT_OVER;
    }

    /**
     * Win detection algorithm for negative diagonal check. Checks back 4
     * locations from current click event location, and then increments through
     * total of 2 times default number - 1.
     * @param rowLoc Row information from current click event.
     * @param columnLoc Column information from current click event.
     * @param currentPlayer Current player associated with click event.
     * @return Returns outcome WIN if win detected with associated player, ring
     * or cross, otherwise GAME NOT OVER.
     */
    private Outcome winDetectNegativeDiagonal(int rowLoc, int columnLoc,
                                           char currentPlayer) {
        int y = rowLoc;
        int x = columnLoc;
        while ((y < (rowLoc + (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1))) &&
                (y < (getNumRows() - 1)) &&
        x < (columnLoc + (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1)) &&
                (x < (getNumCols() - 1))) {
            y++;
            x++;
        }
        int rowLocToStop = rowLoc - (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1);
        int columnLocToStop = columnLoc -
                (GomokuModel.SQUARES_IN_LINE_FOR_WIN - 1);
        int winCount = 0;
        while (y >= rowLocToStop && x >= columnLocToStop && x >= 0 && y >= 0) {
            if (gomokuBoard[y][x] == currentPlayer) {
                winCount++;
            } else winCount = 0;
            y--;
            x--;
            if (winCount == GomokuModel.SQUARES_IN_LINE_FOR_WIN) {
                if (currentPlayer == Square.CROSS.toChar()) {
                    return Outcome.CROSS_WINS;
                } else return Outcome.RING_WINS;
            }
        }
        return Outcome.GAME_NOT_OVER;
    }
}
