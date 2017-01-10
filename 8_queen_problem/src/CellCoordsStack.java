import java.util.*;

/**
 * Stack of chess cells with queens
 * with additional method to find free space to place new.
 */
public class CellCoordsStack extends Stack<CellCoords> {

    private List<Integer> freeCols;

    CellCoordsStack(){
        freeCols = new LinkedList<>(Arrays.asList(
                0, 1, 2, 3, 4, 5, 6, 7
        ));
    }

    public CellCoords push(CellCoords element){

        // Remove new occupied column
        freeCols.remove(
                freeCols.indexOf(element.getJ())
        );

        return super.push(element);

    }

    public synchronized CellCoords pop() {
        CellCoords poppedCoords = super.pop();

        // Beck occupied column
        freeCols.add(poppedCoords.getJ());
        // Sorting here makes algorithm faster, then in
        Collections.sort(freeCols);

        return poppedCoords;
    }

    CellCoordsStack copy(){

        CellCoordsStack copy = new CellCoordsStack();

        for (CellCoords thisCoords : this)
            copy.push(thisCoords);

        return copy;

    }

    List<Integer> getFreeCols(){
        return new ArrayList<>(freeCols);
    }

}