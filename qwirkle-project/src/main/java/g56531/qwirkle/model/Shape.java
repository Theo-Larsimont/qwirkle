package g56531.qwirkle.model;

/**
 * Shape represent shape of tile
 */
public enum Shape {
    CROSS("X"),
    SQUARE("[]"),
    ROUND("O"),
    STAR("*"),
    PLUS("+"),
    DIAMOND("<>");

    private String stringShape;

    Shape(String stringShape) {
        this.stringShape = stringShape;
    }

    @Override
    public String toString() {
        return stringShape;
    }
}
