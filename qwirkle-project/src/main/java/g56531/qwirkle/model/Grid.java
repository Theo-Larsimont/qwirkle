package g56531.qwirkle.model;
/**
 * Grid represent the grid of the game
 */
public class Grid {
    private Tile[][] tile;
    private boolean isEmpty;

    /**
     * builds a board of size 91x91
     */
    public Grid() {
        this.tile = new Tile[91][91];
        this.isEmpty = true;
    }

    /**
     * flip a tile to a given position
     * @param row
     * @param col
     * @return tile in this position
     */
    public Tile get(int row, int col) {
        return tile[row][col];
    }


    // ************************* Méthode ADD **********************************

    /**
     * Place the first tile in the middle of the board if the move is valid
     *
     * @param d    direction in which we want to play
     * @param line tiles we want to play
     */
    public int firstAdd(Direction d, Tile... line) {
        int score = 0;
        if (!this.isEmpty) {
            throw new QwirkleException("Le tableau n'est pas vide");
        }

        if (!tilesPlayIsCompatible(line)) {
            throw new QwirkleException("Les tuiles doivent soit être de couleur " +
                    "différente et de même symbole ou inversement");
        }

        int row = 45;
        int col = 45;
        tile[row][col] = line[0];
        for (int i = 1; i < line.length; i++) {
            row += d.getDeltaRow();
            col += d.getDeltaCol();
            tile[row][col] = line[i];
        }
        this.isEmpty = false;
        return score = tile.length;
    }

    /**
     * Places a single tile if the move to the requested position is possible
     *
     * @param row      line on which we want to place the tile
     * @param col      column on which we want to place the tile
     * @param playTile tile we want to play
     */
    public int add(int row, int col, Tile playTile) {

        if (row < 0 || row > tile.length) {
            throw new QwirkleException("La ligne entrée pour jouer est hors plateau");
        } else if (col < 0 || col > tile.length) {
            throw new QwirkleException("La colonne entrée pour jouer est hors plateau");
        } else if (get(row, col) != null) {
            throw new QwirkleException("Déja une tuile à la position entrée");
        } else if (this.isEmpty) {
            throw new QwirkleException("Pas encore de premier coupe jouer");
        }

        if (compatibleWithTileBoard(row, col, playTile)
        && !checkDouble(row, col, playTile)) {
            tile[row][col] = playTile;
        }else {
            throw new QwirkleException("Les tuiles ne sont pas compatible");
        }
        return scoreCount(row, col, playTile);
    }

    /**
     * Add multiple tiles in a given direction if the move is valid
     *
     * @param row  line where movement begins
     * @param col  column where movement begins
     * @param d    direction to add
     * @param line tiles to play
     */
    public int add(int row, int col, Direction d, Tile... line) {

        boolean validMove = false;
        boolean doubleTile = false;
        int playMove = 0;
        int initialrow = row;
        int initialCol = col;
        for (var tilePlay : line) {
            validMove = compatibleWithTileBoard(row, col, tilePlay);
            doubleTile = checkDouble(row, col, tilePlay);
            if (validMove && !doubleTile) {
                tile[row][col] = tilePlay;
                playMove++;
                row += d.getDeltaRow();
                col += d.getDeltaCol();
            } else {
                row = initialrow;
                col = initialCol;
                for (int i = 0; i < playMove; i++) {
                    tile[row][col] = null;
                    row += d.getDeltaRow();
                    col += d.getDeltaCol();
                }
                throw new QwirkleException("Les tuiles ne sont pas compatible");
            }
        }
    }

    /**
     * Add one or more tiles to a specific position
     * @param line tile to add
     */
    public int add(TileAtPosition... line) {
        boolean validMove = false;
        boolean doubleTile = false;
        int nbMovePlay = 0;
        int row = 0;
        int col = 0;
        Tile tilePlay;
        for (var tilePlayAtPosition : line) {
            row = tilePlayAtPosition.row();
            col = tilePlayAtPosition.col();
            tilePlay = tilePlayAtPosition.tile();
            validMove = compatibleWithTileBoard(row, col, tilePlay);
            doubleTile = checkDouble(row, col, tilePlay);

            if (!validMove || doubleTile) {
                validMove = false;
                break;
            }

            tile[row][col] = tilePlay;
            nbMovePlay++;
        }
        if(!validMove){
            if(nbMovePlay > 0){
                for (int i = 0; i < nbMovePlay; i++) {
                    row = line[i].row();
                    col = line[i].col();
                    tile[row][col] = null;
                }
            }
            throw new QwirkleException("Les tuiles ne sont pas compatible");
        }
    }

    // ********************** Méthode Utile Privée  ***************************

    /**
     * check if all the tiles you want to play are compatible with each other
     *
     * @param line all tile we want play
     * @return true if they are compatible
     */
    private boolean tilesPlayIsCompatible(Tile[] line) {
        int sameColors = 0;
        int sameShapes = 0;
        boolean tileCompatible = true;
        for (int i = 0; i < line.length - 1; i++) {
            int x = i + 1;
            while (x < line.length) {
                if (line[i].color() == line[x].color()) {
                    if(line[i].shape() == line[x].shape()){
                        tileCompatible = false;
                    }
                }
                if (line[i].color() != line[x].color()) {
                    if (line[i].shape() != line[x].shape()) {
                        tileCompatible = false;
                    }
                }
                ++x;
            }
        }

        if (sameShapes > 0 && sameColors > 0) {
            tileCompatible = false;
        }
        return tileCompatible;
    }


    /**
     * check if a tile is compatible with a position with the tiles already
     * present on the board
     * @param row
     * @param col
     * @param line tile to check
     * @return
     */
    private boolean compatibleWithTileBoard(int row, int col, Tile line) {
        int initialRow = row;
        int initialCol = col;
        int caseNull = 0;
        Color colorPlayTile = line.color();
        Shape shapePlayTile = line.shape();
        boolean validMove = true;
        for (var d : Direction.values()) {
            boolean endLineTile = false;
            int lap = 0;
            while (!endLineTile){
                row += d.getDeltaRow();
                col += d.getDeltaCol();

                if (get(row, col) != null) {
                    lap++;
                    if (colorPlayTile != tile[row][col].color()) {
                        if (shapePlayTile != tile[row][col].shape()) {
                            validMove = false;
                        }
                    } else if (shapePlayTile != tile[row][col].shape()) {
                        if (colorPlayTile != tile[row][col].color()) {
                            validMove = false;
                        }
                    } else if (shapePlayTile == tile[row][col].shape() &&
                            colorPlayTile == tile[row][col].color()) {
                        validMove = false;
                    }
                } else if(lap == 0){
                    endLineTile = true;
                    caseNull++;
                } else {
                    endLineTile = true;
                }
            }
            row = initialRow;
            col = initialCol;
        }
        if (caseNull >= 4) {
            validMove = false;
        }
        return validMove;
    }

    /**
     * check if there is already the same tile in a row or column
     * @param row line of the tuile to chec
     * @param col column of thr tile to check
     * @param tilePlay tile to check
     * @return true if there  are double
     */
    private boolean checkDouble(int row, int col, Tile tilePlay) {
        boolean doubletile = false;
        int initialRow = row;
        int initialCol = col;
        for (var d : Direction.values()) {
            row += d.getDeltaRow();
            col += d.getDeltaCol();
            while (tile[row][col] != null){

                if(tile[row][col].color() == tilePlay.color()
                && tile[row][col].shape() == tilePlay.shape()){
                    doubletile = true;
                    break;
                }
                row += d.getDeltaRow();
                col += d.getDeltaCol();
            }
            if (doubletile){
                break;
            }
            row = initialRow;
            col = initialCol;
        }
        return doubletile;

    }

    private int scoreCount(int row, int col, Tile tileplay){
        int initialRow = row;
        int initialCol = col;
        int score = 0;
        boolean lineEmptyCol = true;
        boolean lineEmptyRow = true;

        for (var d : Direction.values()) {
            row += d.getDeltaRow();
            col += d.getDeltaCol();
            if(d.equals(Direction.RIGHT) || d.equals(Direction.LEFT)){
                while (tile[row][col] != null){
                    score++;
                    lineEmptyRow = false;
                    row += d.getDeltaRow();
                    col += d.getDeltaCol();
                }
                if (!lineEmptyRow){
                    score++;
                }
            }else {
                while (tile[row][col] != null){
                    score++;
                    lineEmptyCol = false;
                    row += d.getDeltaRow();
                    col += d.getDeltaCol();
                }
                if (!lineEmptyCol){
                    score++;
                }
            }

            row = initialRow;
            col = initialCol;
        }
        return score;
    }

    private int scoreCount(int row, int col, Direction d, Tile... line){
        int score = 0;
        int initialRow = row;
        int initialCol = col;
        boolean lineCheck;
        
            row = initialRow;
            col = initialCol;

        return score;
    }
}
