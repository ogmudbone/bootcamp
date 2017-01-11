import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents chess desc cell in graph.
 *
 */
public class DescGraphElement {

    /**
     * Special weights rule speed up algorithm disallowing
     * to go on near end cells in the beginning of tour
     */
    private static Map<CellCoords, Integer> specialWeights = new HashMap<>();

    static {
        specialWeights.put(new CellCoords(1, 2), 100);
        specialWeights.put(new CellCoords(2, 1), 100);
        specialWeights.put(new CellCoords(0, 4), 50);
        specialWeights.put(new CellCoords(2, 4), 50);
        specialWeights.put(new CellCoords(3, 1), 50);
        specialWeights.put(new CellCoords(3, 4), 50);
    }

    /**
     * Neighbours of that cell, where horse can go
     */
    private List<DescGraphElement> neighbours;
    private boolean isVisited;
    private CellCoords coords;

    public DescGraphElement(CellCoords coords){
        neighbours = new LinkedList<>();
        this.coords = coords;
    }

    public CellCoords getCoords() {
        return coords;
    }

    public void setVisited(boolean visited){
        this.isVisited = visited;
    }

    /**
     * Returns unvisited cells, where horse can go from that cell
     * @return list of unvisited cells
     */
    public List<DescGraphElement> getFreeNeighboursLinks() {
        return neighbours.stream()
                .filter(element -> !element.isVisited)
                .collect(Collectors.toList());
    }

    /**
     * Links this cell with other meaning that
     * horse can go from this to other and otherwise
     * @param other cell to link
     */
    public void link(DescGraphElement other){
        this.neighbours.add(other);
        other.neighbours.add(this);
    }

    /**
     * Check, does this cell has link to other
     * @param other cell to check link
     * @return true - this cell has link with other
     *         false - no
     */
    public boolean hasNeighbour(DescGraphElement other){
        return neighbours.contains(other);
    }

    /**
     * Weight of cell represents how many moves can horse do
     * from it.
     * Less moves is better.
     * In some special cases weight of cell sets manually.
     * @return weight of cell
     */
    public int getWeight() {
        if(specialWeights.containsKey(this.coords))
            return specialWeights.get(this.coords);
        else
            return getFreeNeighboursLinks().size();
    }

}