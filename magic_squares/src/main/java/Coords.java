
public class Coords {

    private int i;
    private int j;

    Coords(int i, int j){
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean equals(Object other) {
        return other instanceof Coords && this.i == ((Coords) other).i && this.j == ((Coords) other).j;
    }

}