import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 */
public class HorseMove {

    /**
     * Element, where horse stand on this move
     */
    private DescGraphElement standElement;
    /**
     * List of elements where horse can go
     */
    private List<DescGraphElement> unvisitedElements;

    public HorseMove(DescGraphElement standElement) {
        this.standElement = standElement;
        unvisitedElements = standElement.getFreeNeighboursLinks();
        unvisitedElements.sort(
                Comparator.comparingInt(DescGraphElement::getWeight)
        );
        standElement.setVisited(true);
    }

    public boolean hasNextMove(){
        return !unvisitedElements.isEmpty();
    }

    /**
     * Returns cell, where horse can go with minimal
     * weight and removes it from the list
     * @return free cell to go with minimal weight
     *          returns null if there is no cells left
     */
    public DescGraphElement getNextMove(){
        DescGraphElement next = unvisitedElements.get(0);
        unvisitedElements.remove(0);
        return next;
    }

    /**
     * Rolls back this move.
     * Unsets visiting of stand cell
     */
    public void rollBack(){
        standElement.setVisited(false);
    }

    public DescGraphElement getStandElement(){
        return standElement;
    }

}
