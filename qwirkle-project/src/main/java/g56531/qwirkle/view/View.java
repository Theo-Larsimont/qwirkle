package g56531.qwirkle.view;


import g56531.qwirkle.model.GridView;
import g56531.qwirkle.model.Player;

/**
 * All view elment of the game
 */
public class View {

    /**
     * display the board of the game
     *
     * @param grid
     */
    public static void display(GridView grid) {
        int rowMax = 45;
        int colMax = 45;
        int rowMin = 45;
        int colMin = 45;
        boolean tileFind = false;

        for (int row = 0; !tileFind; row++) {
            for (int col = 0; col < 91; col++) {
                if (grid.get(row, col) != null) {
                    tileFind = true;
                    rowMin = row;
                    break;
                }
            }
        }
        tileFind = false;
        for (int row = 90; !tileFind; row--) {
            for (int col = 90; col >= 0; col--) {
                if (grid.get(row, col) != null) {
                    tileFind = true;
                    rowMax = row;
                    break;
                }
            }
        }
        tileFind = false;

        for (int col = 0; !tileFind; col++) {
            for (int row = 0; row < 91; row++) {
                if (grid.get(row, col) != null) {
                    tileFind = true;
                    colMin = col;
                    break;
                }
            }
        }
        tileFind = false;
        for (int col = 90; !tileFind; col--) {
            for (int row = 0; row < 91; row++) {
                if (grid.get(row, col) != null) {
                    tileFind = true;
                    colMax = col;
                    break;
                }
            }
        }

        for (int i = rowMin; i <= rowMax; i++) {
            System.out.print(i + "\t");
            for (int x = colMin; x <= colMax; x++) {
                if (grid.get(i, x) != null) {
                    System.out.print(grid.get(i, x).toString() + "\t");
                } else {
                    System.out.print(" \t");
                }
            }
            System.out.println();
        }
        System.out.print("\t");
        for (int i = colMin; i <= colMax; i++) {
            System.out.print(i + "\t");
        }
    }

    /**
     * Display the name of the player
     *
     * @param player
     */
    public static void display(Player player) {
        System.out.println(player.getName() + ": " + player.getHands());
    }

    /**
     * display a message  error
     *
     * @param message
     */
    public static void displayError(String message) {
        System.out.println("\u001B[31m" + message + "\u001B[0m");
    }

    /**
     * display game controls
     */
    public static void displayHelp() {
        System.out.println(""" 
                Q W I R K L E
                Qwirkle command:
                    - play 1 tile : o <row> <col> <i>
                    - play line: l <row> <col> <direction> <i1> [<i2>]
                    - play plic-ploc : m <row1> <col1> <i1> [<row2> <col2> <i2>]
                    - play first : f <i1> [<i2>]
                    - pass : p
                    - quit : q
                        i : index in list of tiles
                        d : direction in l (left), r (right), u (up), d(down)
                """);
    }
}
