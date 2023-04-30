package g56531.qwirkle.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Player of the game
 */
public class Player {
    private String name;

    // Hand of the player
    private List<Tile> tiles;

    public Player(String name) {

        this.name = name;
        Tile[] arrayTile = Bag.getInstance().getRandomTiles(6);
        tiles = new ArrayList<>();
        for (var tile: arrayTile) {
            this.tiles.add(tile);
        }
    }

    public String getName() {
        return name;
    }

    /**
     *
     * @return an uneditable list of the player's hand
     */
    public List getHands(){
        return Collections.unmodifiableList(tiles);
    }

    /**
     * refills the player's hand to be 6 tiles again
     */
    public void refill(){
        int nbTileDraw = 6 - tiles.size();
        Tile[] arrayTile = Bag.getInstance().getRandomTiles(nbTileDraw);
        for (var tile: arrayTile) {
            this.tiles.add(tile);
        }
    }


    /**
     * remove tiles input as player hand parameter
     * @param ts
     */
    public void remove(Tile... ts){
        for (int i = 0; i < ts.length; i++){
            for(int x = 0; x < tiles.size(); x++){
                if(ts[i].hashCode() == tiles.get(x).hashCode()){
                    tiles.remove(x);
                }
            }
        }
    }

}
