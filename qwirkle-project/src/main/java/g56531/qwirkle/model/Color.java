package g56531.qwirkle.model;

/**
 * Color represent color of tile
 */
public enum Color {
    BLUE("\u001B[34m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    ORANGE("\033[38;5;208m\n"),
    YELLOW("\u001B[33m"),
    PURPLE("\u001B[35m");

    private String codeColor;

    Color(String codeColor) {
        this.codeColor = codeColor;
    }

    @Override
    public String toString() {
        return codeColor;
    }
}
