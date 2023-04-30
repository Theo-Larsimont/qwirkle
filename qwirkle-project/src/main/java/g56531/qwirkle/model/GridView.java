package g56531.qwirkle.model;

/**
 * grid view not editable
 */
public class GridView {
    private Grid grid;

    public GridView(Grid grid) {
        this.grid = grid;
    }

    public Tile get(int row, int col) {
        return grid.get(row, col);
    }

    public boolean isEmpty() {
        return isEmpty();
    }
}
