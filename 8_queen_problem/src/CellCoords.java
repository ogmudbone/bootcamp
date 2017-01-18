/**
 * Represents cell in chess desc occupied by queen
 * Has i and j coordinates (meant as in matrix)
 */
public class CellCoords {

    private int i;
    private int j;

    CellCoords(int i, int j){
        this.i = i;
        this.j = j;
    }

    int getI() {
        return i;
    }

    int getJ() {
        return j;
    }

    /**
     * Checks is other cell beats by this cell diagonally
     * @param otherCell cell to check beat
     * @return true  - if this cell beat other,
     *         false - if this cell not beat other
     */
    boolean checkDiagonalCollision(CellCoords otherCell){

        return  // if coords sums is equal, it means that both cells located in one secondary diagonal
                otherCell.getI() + otherCell.getJ() == this.getI() + this.getJ() ||
                // if coords substitutions is equal, it means that both cells located in one main diagonal
                otherCell.getI() - otherCell.getJ() == this.getI() - this.getJ();

    }

}