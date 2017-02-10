import cs251.lab3.GomokuGUI;
import cs251.lab3.GomokuModel;

import java.util.Random;
import java.util.Scanner;

/**
 * Program to play Gomoku, either with partner or against computer.
 * Class 251
 * @author Michael Servilla
 * @version date 2017-02-01
 */
public class Gomoku implements GomokuModel{
    /**
     * Main method to start the game.
     * @param args Not used for the program, scanner used instead for input.
     */
    public static void main(String[] args) {
        Gomoku game = new Gomoku();

        //Question to play computer or not.
        Scanner ask = new Scanner(System.in);
        System.out.println("Pick your play, choose a number 1, 2, or 3.");
        System.out.println("1. Play against another human.");
        System.out.println("2. Play against random move computer.");
        System.out.println("3. Play against a little annoying, " +
                "copy cat, computer.");
        String  stringNumber = ask.next();

        //Input error correction.
        while (!TryParseIn(stringNumber)) {
            System.out.println("Pick your play, choose a number 1, 2, or 3.");
            stringNumber = ask.next();
        }
        GomokuGUI.showGUI(game);
    }

    private char[][] gomokuBoard = new char[getNumRows()][getNumCols()];
    private int setPlayer = 0;
    private char currentPlayer;
    private Random rand = new Random();
    private static int answer;

    /**
     * Method to take string input and parse to integer. Will throw exception if
     * not able to parse to integer, or return false if value is not one of the
     * available choices.
     * @param number String value entered from the command line.
     * @return Returns either true if value can be parsed to a number and is one
     * of the available choices, or false if not true.
     */
    private static boolean TryParseIn(String number) {
        try {
            answer = Integer.parseInt(number);
            while (answer != 1 && answer != 2 && answer != 3) {
                System.out.println("Value is not a valid choice!");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Value can't be parsed as a number!");
            return false;
        }
    }
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
        return  GomokuModel.DEFAULT_NUM_ROWS;
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
        switch (answer) {
            case 1:
                getCurrentPlayer(row, column); //To play against another human.
                return winDetection(row, column, currentPlayer);
            case 2:
                while (gomokuBoard[row][column] != Square.EMPTY.toChar()) {
                    return Outcome.GAME_NOT_OVER;
                }
                Outcome outcomeResults = humanPlayer(row, column);
                if (outcomeResults.equals(Outcome.RING_WINS)) {
                    return outcomeResults;
                }
                outcomeResults = randomComputer();
                if (outcomeResults.equals(Outcome.CROSS_WINS)) {
                    return outcomeResults;
                }
                return outcomeResults;
            case 3:
                while (gomokuBoard[row][column] != Square.EMPTY.toChar()) {
                    return Outcome.GAME_NOT_OVER;
                }
                outcomeResults = humanPlayer(row, column);
                if (outcomeResults.equals(Outcome.RING_WINS)) {
                    return outcomeResults;
                }
                outcomeResults = copyCatComputer(row, column);
                if (outcomeResults.equals(Outcome.CROSS_WINS)) {
                    return outcomeResults;
                }
                return outcomeResults;
        }
        return Outcome.GAME_NOT_OVER;
    }
    /**
     *Method to determine current player (Ring or Cross) when not playing
     *  against the computer. Alternates between ring or cross.
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
     *Method to get human player location when playing against the computer and
     * then check for win.
     * @param row Row location of square for human player.
     * @param column Column location of square for human player.
     * @return Returns the result of the human player play.
     */
    private Outcome humanPlayer(int row, int column) {
        currentPlayer = Square.RING.toChar();
        gomokuBoard[row][column] = currentPlayer;
        Outcome outcomeResult = winDetection(row, column, currentPlayer);
        if (outcomeResult.equals(Outcome.RING_WINS)) {
            return outcomeResult;
        } else if (outcomeResult.equals(Outcome.DRAW)) {
            return outcomeResult;
        }
        return Outcome.GAME_NOT_OVER;
    }
    /**
     *
     * @param s
     */
    @Override
    public void setComputerPlayer(String s) {
    }
    /**
     * Method for computer player to choose random moves.
     * @return Returns random location with cross.
     */
    private Outcome randomComputer() {
        if (!getBoardString().contains("-")) { //Check for draw.
            return Outcome.DRAW;
        }
        int row;
        int column;
        do {
            row = rand.nextInt(getNumRows());
            column = rand.nextInt(getNumCols());
        } while (gomokuBoard[row][column] != Square.EMPTY.toChar());

        gomokuBoard[row][column] = Square.CROSS.toChar();
        currentPlayer = Square.CROSS.toChar();
        return winDetection(row, column, currentPlayer);
    }
    /**
     *Method for slightly more advanced computer player using location of
     * opponents click to determine its move.
     * @param row Row information from previous opponents click event.
     * @param column Column information from previous opponents click event.
     * @return Returns outcome of its move by calling win detection.
     */
    private Outcome copyCatComputer(int row, int column) {
        if (!getBoardString().contains("-")) { //Check for draw.
            return Outcome.DRAW;
        }
        int y = row;
        int y1 = row;
        int y2 = row;
        int x = column;
        int x1 = column;
        int x2 = column;
        if (x != getNumCols() - 1 && gomokuBoard[y][++x1] == Square.EMPTY.toChar()) {
            column++;
        } else if (x != 0 && gomokuBoard[y][--x2] == Square.EMPTY.toChar()) {
            column--;
        } else if (y != getNumRows() - 1 && gomokuBoard[++y1][x] == Square.EMPTY.toChar()) {
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
        //This section determines start point for iteration.
        int x = columnLoc;
        while (x > 0 && x > (columnLoc -
                (getNumInLineForWin() - 1))) {
            x--;
        }
        //This section determines end point for iteration.
        int locToStop = columnLoc + (getNumInLineForWin());
        int winCount = 0;
        while (x < locToStop && x < getNumCols()) {
            if (gomokuBoard[rowLoc][x] == currentPlayer) {
                winCount++;
            } else winCount = 0;
            x++;
            if (winCount == getNumInLineForWin()) {
                if (currentPlayer == Square.CROSS.toChar()) {
                    return Outcome.CROSS_WINS;
                } else return Outcome.RING_WINS;
            }
        }
        return Outcome.GAME_NOT_OVER;
    }
    /**
     * Win detection algorithm for vertical check. Checks back 4 locations
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
        //This section determines start point for iteration.
        int y = rowLoc;
        while (y > 0 && y > (rowLoc -
                (getNumInLineForWin() - 1))) {
            y--;
        }
        //This section determines end point for iteration.
        int locToStop = rowLoc + (getNumInLineForWin());
        int winCount = 0;
        while (y < locToStop && y < getNumRows()) {
            if (gomokuBoard[y][columnLoc] == currentPlayer) {
                winCount++;
            } else winCount = 0;
            y++;
            if (winCount == getNumInLineForWin()) {
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
        //This section determines start point for iteration.
        int y = rowLoc;
        int x = columnLoc;
        while ((y < (rowLoc + (getNumInLineForWin() - 1))) &&
                (y < (getNumRows() - 1)) &&
                x > (columnLoc - (getNumInLineForWin() - 1)) &&
                (x > 0)) {
            y++;
            x--;
        }
        //This section determines end point for iteration.
        int rowLocToStop = rowLoc - (getNumInLineForWin() - 1);
        int columnLocToStop = columnLoc +
                (getNumInLineForWin() - 1);
        int winCount = 0;
        while (y >= rowLocToStop && x <= columnLocToStop && x >= 0 &&
                y >= 0 && x < getNumCols()) {
            if (gomokuBoard[y][x] == currentPlayer) {
                winCount++;
            } else winCount = 0;
            y--;
            x++;
            if (winCount == getNumInLineForWin()) {
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
        //This section determines start point for iteration.
        int y = rowLoc;
        int x = columnLoc;
        while ((y < (rowLoc + (getNumInLineForWin() - 1))) &&
                (y < (getNumRows() - 1)) &&
        x < (columnLoc + (getNumInLineForWin() - 1)) &&
                (x < (getNumCols() - 1))) {
            y++;
            x++;
        }
        //This section determines end point for iteration.
        int rowLocToStop = rowLoc - (getNumInLineForWin() - 1);
        int columnLocToStop = columnLoc -
                (getNumInLineForWin() - 1);
        int winCount = 0;
        while (y >= rowLocToStop && x >= columnLocToStop && x >= 0 && y >= 0) {
            if (gomokuBoard[y][x] == currentPlayer) {
                winCount++;
            } else winCount = 0;
            y--;
            x--;
            if (winCount == getNumInLineForWin()) {
                if (currentPlayer == Square.CROSS.toChar()) {
                    return Outcome.CROSS_WINS;
                } else return Outcome.RING_WINS;
            }
        }
        return Outcome.GAME_NOT_OVER;
    }
}
