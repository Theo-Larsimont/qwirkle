package g56531.qwirkle.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Bag represent bag of 108 tile
 */
public class Bag {
    private static Bag instance;
    private List<Tile> tiles;

    /**
     * Build the bag with 108 tile
     */
    private Bag() {
        tiles = new ArrayList<>();
        Color[] colors = Color.values();
        Shape[] shapes = Shape.values();

            for(int x = 0; x < colors.length; x++){
                Color color = colors[x];
                for (int y = 0; y < shapes.length; y++){
                    Shape shape = shapes[y];
                    // copy of tile
                    tiles.add(new Tile(color, shape));
                    tiles.add(new Tile(color, shape));
                    tiles.add(new Tile(color, shape));
                }
            }
    }

    /**
     * gives n random tile from tile bag
     * if bag size is empty give anything
     * if there are no more tiles in the bag give the rest of the bag
     * @param n number tile given
     * @return a array with all random tile
     */
    public Tile[] getRandomTiles(int n){
        Tile[] drawTile = new Tile[n];
        if (getsize() == 0){
            return null;
        } else if (getsize() < n) {
            for(int i = 0; i < getsize(); i++){
                drawTile[i] = tiles.get(i);
                tiles.remove(i);
            }
        } else{
            for(int i = 0; i < n; i++){
                Random random = new Random();
                int indexTile = random.nextInt(getsize() - 1);
                drawTile[i] = tiles.get(indexTile);
                tiles.remove(indexTile);
            }
        }
        return drawTile;
    }

    /**
     * Get the instance of Bag (Singleton pattern)
     */
    public static Bag getInstance(){
        if(instance == null){
            instance = new Bag();
        }
        return instance;
    }

    /**
     * Give the size of the bag
     * @return size of the bag
     */
    public int getsize(){
        return this.tiles.size();
    }

}

