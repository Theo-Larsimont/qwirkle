package g56531.qwirkle.model;

/**
 * represent tile of the game
 * @param color of the tile
 * @param shape of the tile
 */
public record Tile(Color color, Shape shape) {

    @Override
    public String toString() {
        return color +""+shape + "\u001B[0m";
    }
}
