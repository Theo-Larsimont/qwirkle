package g56531.qwirkle.view;


import g56531.qwirkle.model.GridView;
import g56531.qwirkle.model.Player;

/**
 * All view elment of the game
 */
public class View {

    /**
     * display the board of the game
     * @param grid
     */
    public static void display(GridView grid) {
        int rowMax = 45;
        int colMax = 45;
        int rowMin = 45;
        int colMin = 45;
        boolean minFind = false;
        boolean maxFind = false;
        for (int row = 0; !minFind; row++ ){
            for(int col = 0; col < 91; col++){
                if (grid.get(row,col) != null){
                    minFind = true;
                    rowMin = row;
                    colMin = col;
                    break;
                }
            }
        }

        for (int row = 90; !maxFind; row-- ){
            for(int col = 90; col >= 0; col--){
                if (grid.get(row,col) != null){
                    maxFind = true;
                    rowMax = row;
                    colMax = col;
                    break;
                }
            }
        }

        for (int i = rowMin; i <= rowMax; i++) {
            System.out.print(i +"\t");
            for(int x = colMin; x <= colMax; x++){
                if(grid.get(i,x) != null){
                    System.out.print(grid.get(i,x).toString() + "\t");
                }
            }
            System.out.println();
        }
        System.out.print("\t");
        for(int i = colMin; i <= colMax; i++){
            System.out.print(i+"\t");
        }
    }

    /**
     * Display the name of the player
     * @param player
     */
    public static void display(Player player){
        System.out.println(player.getName());
    }

    /**
     * display a message  error
     * @param message
     */
    public static void displayError(String message){
        System.out.println(
        message);
    }

    /**
     * display game controls
     */
    public static void displayHelp(){
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
