/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

/**
 *
 * @author prateek.kesarwani
 */
public class Position {

    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Time Complexity: O(1)
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position && this.x == ((Position) obj).x && this.y == ((Position) obj).y) {
            return true;
        }
        return false;
    }
}
