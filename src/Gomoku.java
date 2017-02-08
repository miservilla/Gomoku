import cs251.lab3.GomokuGUI;
import cs251.lab3.GomokuModel;

import java.util.Random;
import java.util.Scanner;


/**
 * @author Michael Servilla
 * @version date 2017-02-01
 */
public class Gomoku implements GomokuModel{
    private static String answer;

    /**
     *
     * @param args
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
     *
     * @return
     */
    @Override
    public int getNumCols() {
        return GomokuModel.DEFAULT_NUM_COLS;
    }

    /**
     *
     * @return
     */
    @Override
    public int getNumRows() {
        return GomokuModel.DEFAULT_NUM_ROWS;
    }

    /**
     *
     * @return
     */
    @Override
    public int getNumInLineForWin() {
        return GomokuModel.SQUARES_IN_LINE_FOR_WIN;
    }

    /**
     *
     * @param row
     * @param column
     * @return
     */
    @Override
    public Outcome playAtLocation(int row, int column) {
        if (answer.toUpperCase().equals("Y")) {
                                currentPlayer = Square.RING.toChar();
            while (gomokuBoard[row][column] != Square.EMPTY.toChar()) {
                return Outcome.GAME_NOT_OVER;
            }
            gomokuBoard[row][column] = currentPlayer;
            Outcome a = winDetection(row, column, currentPlayer);
            if (a.equals(Outcome.RING_WINS)) {
                return a;
            }
            a = copyCatComputer(row, column);
            if (a.equals(Outcome.CROSS_WINS)) {
                return a;
            }
            return a;
        } else {
            getCurrentPlayer(row, column);
            Outcome a = winDetection(row, column, currentPlayer);
            return a;
        }
    }

    /**
     *
     * @param row
     * @param column
     * @return
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
     *
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
     *
     * @return
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
     *
     * @return
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
     *
     * @param row
     * @param column
     * @return
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
     *
     * @param row
     * @param column
     * @param currentPlayer
     * @return
     */
    private Outcome winDetection(int row, int column, char currentPlayer) {
        Outcome a = winDetectHorizontal(row, column, currentPlayer);
        if (a != Outcome.GAME_NOT_OVER) {
            setPlayer++;
            return a;
        }
        a = winDetectVertical(row, column, currentPlayer);
        if (a != Outcome.GAME_NOT_OVER) {
            setPlayer++;
            return a;
        }
        a = winDetectPositiveDiagonal(row, column, currentPlayer);
        if (a != Outcome.GAME_NOT_OVER) {
            setPlayer++;
            return a;
        }
        a = winDetectNegativeDiagonal(row, column, currentPlayer);
        if (a != Outcome.GAME_NOT_OVER) {
            setPlayer++;
            return a;
        }
        if (!getBoardString().contains("-")) { //Check for draw.
            return Outcome.DRAW;
        }
        return Outcome.GAME_NOT_OVER;
    }

    /**
     *
     * @param rowLoc
     * @param columnLoc
     * @param currentPlayer
     * @return
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
     *
     * @param rowLoc
     * @param columnLoc
     * @param currentPlayer
     * @return
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
     *
     * @param rowLoc
     * @param columnLoc
     * @param currentPlayer
     * @return
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
     *
     * @param rowLoc
     * @param columnLoc
     * @param currentPlayer
     * @return
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
