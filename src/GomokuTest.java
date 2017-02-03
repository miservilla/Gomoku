import cs251.lab3.GomokuGUI;

/**
 * @author Michael Servilla
 * @version date 2017-02-01
 */
public class GomokuTest {

    public static void main(String[] args) {
        Gomoku game = new Gomoku();

        if (args.length > 0) {
            game.setComputerPlayer(args[0]);
        }
        GomokuGUI.showGUI(game);
    }
}
