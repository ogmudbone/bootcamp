package com.anotheria.bootcamp.magicsquares;

/**
 * Represents square,
 * that may be filled with numbers.
 */
public class Square {

    private int[][] numbers;

    public Square(int size){

        numbers = new int[size][];

        for(int i = 0;i < size;i++)
            numbers[i] = new int[size];

    }

    /**
     * Returns square size. (number of elements in row or column)
     * @return square size
     */
    public int size(){
        return numbers.length;
    }

    /**
     * Indicates is this square size is even.
     * @return true  - even
     *         false - odd
     */
    public boolean isEvenSize(){
        return size() % 2 == 0;
    }

    /**
     * Sets cell in square.
     * @param i row index (starts from 0)
     * @param j column index (starts from 0)
     * @param number number to set
     */
    public void set(int i, int j, int number){
        numbers[i][j] = number;
    }

    /**
     * Return number stored in cell
     * @param i row index (starts from 0)
     * @param j column index (starts from 0)
     * @return number stored in cell
     */
    public int get(int i, int j){
        return numbers[i][j];
    }

    /**
     * Sets cell in square.
     * @param coords coordinates of cell
     * @param number  number to set
     */
    public void set(Coords coords, int number){
        numbers[coords.getI()][coords.getJ()] = number;
    }

    /**
     * Return number stored in cell
     * @param coords coordinates of cell
     * @return number stored in cell
     */
    public int get(Coords coords){
        return numbers[coords.getI()][coords.getJ()];
    }

    /**
     * Makes copy of this object
     * @return copy of this object
     */
    public Square copy(){

        Square copy = new Square(this.size());

        for(int i = 0;i < this.size();i++)
            for(int j = 0;j < this.size();j++)
                copy.set( i, j, this.get(i, j) );

        return copy;

    }

}
