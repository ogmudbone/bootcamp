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

    /**
     * Copies horse moves. Copies links on stand cells
     * and creates new lists with unvisited cells with same links
     * @param stack stack to copy
     * @return new copied stack
     */
    public static Stack<HorseMove> copyMoveStack(Stack<HorseMove> stack){

        Stack<HorseMove> copy = new Stack<>();

        for (HorseMove move : stack) {
            HorseMove copyMove = new HorseMove(move.standElement);
            copyMove.unvisitedElements = new LinkedList<>(move.unvisitedElements);
            copy.push(copyMove);
        }

        return copy;

    }

}
