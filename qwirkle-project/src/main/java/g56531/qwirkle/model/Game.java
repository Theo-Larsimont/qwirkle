package g56531.qwirkle.model;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {
    private Grid grid;
    private Player[] players;
    private int currentPlayer;

    /**
     * The constructor initializes the players table, the game grid and initializes the player
     * current at 0
     * @param playersName
     */
    public Game(List<String> playersName) {
        players = new Player[playersName.size()];
        int i = 0;
        for (var player: playersName) {
            players[i] = new Player(player);
            i++;
        }
        this.grid = new Grid();
        this.currentPlayer = 0;

    }

    public Player getPlayers() {
        return players[currentPlayer];
    }
    public Player[] getPlayer(){
        return players;
    }
    public int getCurrentPlayer(){
        return currentPlayer;
    }
    public String getCurrentPlayerName(){
        return players[currentPlayer].getName();
    }

    public List<Tile> getCurrentPlayerHands(){
        return players[currentPlayer].getHands();
    }

    public GridView getGrid() {
        return new GridView(grid);
    }

    /**
     * allows the player to pass his turn
     */
    public void pass(){

        if(currentPlayer == players.length -1){
            currentPlayer = 0;
        }else {
            currentPlayer++;
        }
    }


    /**
     * who tries to play the trick current player
     * The tiles are drawn from the player's hand and correspond, in order, to the
     * clues provided;
     * @param d direction in which the player wants to lay his tiles
     * @param is the indexes corresponding to the positions of the tiles
     *           (from the getCurrentPlayerHands table) that the player wants to play
     */
    public void first(Direction d, int... is){
        List<Tile> handCurrentPlayer = getCurrentPlayerHands();
        Tile[] tiles = new Tile[is.length];
        for(int i = 0; i < is.length; i++){
            tiles[i] = handCurrentPlayer.get(is[i]);
        }
        grid.firstAdd(d, tiles);
        getPlayers().remove(tiles);
        getPlayers().refill();
    }

    /**
     * allows the player to play a tile at the location of their choice
     * @param row line where the player wants to place his tile
     * @param col column where the player wants to place his tile
     * @param index index corresponding to the position of the tile
     *              (from thegetCurrentPlayerHands array) that the player wants to play
     */
    public void play(int row, int col, int index){
        grid.add(row, col, getCurrentPlayerHands().get(index));
        getPlayers().remove(getCurrentPlayerHands().get(index));
        getPlayers().refill();
    }

    /**
     * allows the player to place multiple tiles by giving a starting position µ
     * and a direction in which to place their tiles
     * @param row line where the player wants to place first tile
     * @param col column where the player wants to place first tile
     * @param d direction in which the player wants to lay his tiles
     * @param indexes he indexes corresponding to the positions of the tiles
     *                 (from the getCurrentPlayerHands table) that the player wants to play
     */
    public void play (int row, int col, Direction d, int... indexes){
        List<Tile> handCurrentPlayer = getCurrentPlayerHands();
        Tile[] tiles = new Tile[indexes.length];
        for(int i = 0; i < indexes.length; i++){
            tiles[i] = handCurrentPlayer.get(indexes[i]);
        }

        grid.add(row, col, d, tiles);
        getPlayers().remove(tiles);
        getPlayers().refill();
    }

    /**
     * allows the player to place multiple tiles without them following each other
     * @param is array of integers where:
     *           - 1 integer represents line where the player wants to place his tile
     *           - 2 int represents column where the player wants to place his tile
     *           - 3 int represents ndex corresponding to the position of the tile
     *                    (from thegetCurrentPlayerHands array) that the player wants to play
     *           and so on ( so the array should automatically be a multiple of three)
     */
    public void play(int... is){
        TileAtPosition[] tiles = new TileAtPosition[is.length/3];
        int row = 0;
        int col = 1;
        int tileIndex = 2;
        Tile tile = new Tile(null, null);
        for(int i = 0; i < is.length/3; i++){
            tile = getCurrentPlayerHands().get(is[tileIndex]);
            tiles[i] = new TileAtPosition(is[row], is[col], tile);
            row+= 3;
            col+= 3;
            tileIndex+= 3;
        }
        grid.add(tiles);
    }
}
