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
     *
     * @param row
     * @param col
     * @return tile in this position
     */
    public Tile get(int row, int col) {
        return tile[row][col];
    }

    /**
     * Place the first tile in the middle of the board if the move is valid
     * @param d direction in which we want to play
     * @param line tiles we want to play
     */
    public void firstAdd(Direction d, Tile... line) {

        if (!this.isEmpty) {
            throw new QwirkleException("Le tableau n'est pas vide");
        }
        int sameColors = 0;
        int sameShapes = 0;
        for(int i = 0; i < line.length -1; i++){
           int x = i + 1;
           while (x < line.length){
               if(line[i].color() == line[x].color()){
                   sameColors++;
               }
               if(line[i].shape() == line[x].shape()){
                   sameShapes++;
               }
               ++x;
           }
        }

        if (sameShapes  > 0 && sameColors > 0) {
            throw new QwirkleException("Les tuiles doivent soit être de couleur " +
                    "différente et de même symbole ou inversement");
        }

        int row = 45;
        int col = 45;
        tile[row][col] = line[0];
        for( int i = 1; i < line.length; i++){
            row += d.getDeltaRow();
            col += d.getDeltaCol();
            tile[row][col] = line[i];
        }
        this.isEmpty = false;

    }

    /**
     * Places a single tile if the move to the requested position is possible
     * @param row line on which we want to place the tile
     * @param col column on which we want to place the tile
     * @param playTile tile we want to play
     */
    public void add(int row, int col, Tile playTile){
        boolean validMove = true;
        int initalRow = row;
        int initialCol = col;

        if(row < 0 || row > tile.length){
            throw new QwirkleException("La ligne entrée pour jouer est hors plateau");
        } else if (col < 0 || col > tile.length) {
            throw new QwirkleException("La colonne entrée pour jouer est hors plateau");
        }else if(get(row,col) != null){
            throw new QwirkleException("Déja une tuile à la position entrée");
        } else if (this.isEmpty) {
            throw new QwirkleException("Pac encore de premier coupe jouer");
        }

        Color colorPlayTile = playTile.color();
        Shape shapePlayTile = playTile.shape();
        int caseNull = 0;
        for(int i = 0; i < 4; i++){
            switch (i){
                case 0:
                    row -= 1;
                    break;
                case 1:
                    row += 1;
                    break;
                case 2:
                    col -= 1;
                    break;
                case 3:
                    col += 1;
                    break;
            }

            if (!(get(row, col) == null)){
                if(colorPlayTile != tile[row][col].color()){
                    if(shapePlayTile != tile[row][col].shape()){
                        validMove = false;
                    }
                }else if(shapePlayTile != tile[row][col].shape()){
                    if(colorPlayTile != tile[row][col].color()){
                        validMove = false;
                    }
                }
            } else {
                caseNull ++;
            }
            col = initialCol;
            row = initalRow;

        }

        if(validMove && caseNull < 4){
            tile[row][col] = playTile;
        }

    }
}
