import java.util.ArrayList;
import java.util.List;

/**
 * Represents horse moves on chess desc graph
 */
public class DescGraph {

    private DescGraphElement first;

    public DescGraph(){

        List<DescGraphElement> elementsList = new ArrayList<>(64);

        for(int i = 0;i < 8;i++)
            for(int j = 0;j < 8;j++){

                CellCoords currentCoords = new CellCoords(i, j);
                DescGraphElement currentElement = new DescGraphElement(currentCoords);
                elementsList.add(currentElement);

                if(i > 0 && j > 1)
                    currentElement.link(elementsList.get((i - 1) * 8 + j - 2));

                if(i > 1 && j > 0)
                    currentElement.link(elementsList.get((i - 2) * 8 + j - 1));

                if(i > 0 && j < 6)
                    currentElement.link(elementsList.get((i - 1) * 8 + j + 2));

                if(i > 1 && j < 7)
                    currentElement.link(elementsList.get((i - 2) * 8 + j + 1));

            }

        first = elementsList.get(0);

    }

    /**
     * @return a8 cell graph node
     */
    public DescGraphElement getFirst(){
        return first;
    }

}
