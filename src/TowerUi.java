import logic.Disk;
import logic.RodPos;
import logic.Tower;

import java.util.Scanner;

/**
 * A text-based user interface for the logic.Tower game.
 * A main-method running this user interface might look like this:
 * ... main(...) {
 *    TowerUi ui = new TowerUi();
 *     ui.run();
 *     System.out.println("Bye bye!");
 * }
 */
public class TowerUi {

    private final Tower tower;
    private final Scanner scan;
    private boolean isRunning;

    public TowerUi() {
        tower = new Tower(4);
        scan = new Scanner(System.in);
        isRunning = true;
    }

    /**
     * The main game loop for the logic.Tower of Hanoi game.
     * This method displays the game introduction and rules, starts new game sessions,
     * and allows interaction by executing user commands until the game is solved or quit.
     */
    public void run() {
        System.out.println("Welcome to the Towers of Hanoi game!");
        printRules();
        while (isRunning) {
            tower.initNewGame();
            System.out.println("New game!");
            printCommands();
            printBoard();
            while (isRunning && !tower.isSolved()) {
                readAndExecuteCommand();
            }
            if (tower.isSolved()) {
                System.out.println(congrats);
            }
        }
    }

    /**
     *  Read input from the Scanner and execute the corresponding command.
     *  The input is converted to lower case. Only the first letter is
     *  evaluated.
     */
    void readAndExecuteCommand() {
        System.out.print("Enter command: ");
        String str = scan.nextLine();
        if (str.isEmpty()) {
            printIllegalCommand(str);
            return;
        }
        String cmdStr = str.trim().toLowerCase();
        if (cmdStr.length() == 1) {
            char cmdChar = cmdStr.charAt(0);
            switch (cmdChar) {
                case 'h' -> printCommands();
                case 'q' -> isRunning = false;
                default -> printIllegalCommand(cmdStr);
            }
        } else if (cmdStr.length() == 2) {
            RodPos fromPos = getRodPos(cmdStr.charAt(0));
            RodPos toPos = getRodPos(cmdStr.charAt(1));
            if (fromPos != null && toPos != null) {
                if (tower.isLegalMove(fromPos, toPos)) {
                    tower.makeMove(fromPos, toPos);
                    printBoard();
                } else {
                    System.out.println("Illegal move.");
                }
            }
            else {
                printIllegalCommand(cmdStr);
            }
        } else {
            printIllegalCommand(cmdStr);
        }
    }

    /**
     * Print the current state of logic.Tower in a user-friendly (-ish) way.
     */
    void printBoard() {
        int noOfDisks = tower.getNoOfDisks();
        Disk[] left = tower.getDisks(RodPos.LEFT);
        Disk[] middle = tower.getDisks(RodPos.MIDDLE);
        Disk[] right = tower.getDisks(RodPos.RIGHT);

        for (int i = noOfDisks - 1; i >= 0; i--) {
            String row = "";
            row += left.length <= i ? "  " : " " + left[i].diameter();
            row += middle.length <= i ? "  " : " " + middle[i].diameter();
            row += right.length <= i ? "  " : " " + right[i].diameter();
            System.out.println(row);
        }
        System.out.println(" = = = ");
        System.out.println("moves= " + tower.getMoves());
    }

    void printRules() {
        System.out.println(rules);
    }

    void printCommands() {
        System.out.println(commands);
    }

    void printIllegalCommand(String cmdStr) {
        System.out.println("Sorry, i do not understand \"" + cmdStr + "\"");
    }

    private RodPos getRodPos(char rodChar) {
        return switch (rodChar) {
            case 'l' -> RodPos.LEFT;
            case 'm' -> RodPos.MIDDLE;
            case 'r' -> RodPos.RIGHT;
            default -> null;
        };
    }

    private static final String rules =
            """
                    The objective of the game is to move the disks from the left
                    rod to the right rod. Only a disk at the top of a rod may be
                    moved. A disk can only be moved to an empty rod or to a rod
                    where the top disk is larger than the disk to move.
                     - - -""";
    private static final String commands =
            """
                    Commands:
                       lm//lr/ml/... : move a disk, e.g. "lr" moves from the left to right rod (if allowed)
                       h(elp)
                       q(uit)
                     - - -""";
    private static final String congrats =
            """
                    |------------------|
                    | Congratulations! |
                    |------------------|
                    """;
}
