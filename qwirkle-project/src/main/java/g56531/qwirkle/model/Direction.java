package g56531.qwirkle.model;

/**
 * direction of travel
 */
public enum Direction {
    LEFT(0,-1),
    RIGHT(0,1),
    DOWN(1,0),
    UP(-1,0);

    private int deltaRow;
    private int deltaCol;


    Direction(int row, int col) {
        this.deltaRow =row;
        this.deltaCol =col;

    }

    public int getDeltaRow() {
        return deltaRow;
    }

    public int getDeltaCol() {
        return deltaCol;
    }

    /**
     * Give the opposite direction
     * @return Opposite direction
     */
    public Direction opposite(){
        Direction oppDir;
        if(this == LEFT){
            oppDir = RIGHT;
        } else if (this == RIGHT) {
            oppDir = LEFT;
        }else if (this == UP){
            oppDir = DOWN;
        }else {
            oppDir = UP;
        }
        return oppDir;
    }
}
