/**
 * Represents cell in chess desc.
 * Has i and j coordinates (meant as in matrix)
 */
class CellCoords {

    private int i;
    private int j;

    CellCoords(int i, int j){
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int hashCode(){
        return i * 8 + j;
    }

    public boolean equals(Object other) {

        return other instanceof CellCoords && this.i == ((CellCoords) other).i && this.j == ((CellCoords) other).j;

    }

}