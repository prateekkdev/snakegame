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
public class SnakeGame {

    private final int boardSizeX;
    private final int boardSizeY;
    private Position snakeHead;

    /**
     * Using CircularQueue with array as we need FIFO behavior, and for this
     * enqueue and dequeue both operations are O(1)
     */
    private final CircularQueue<Position> snakePath;

    /**
     * Creates the SnakeGame and initializes the current state of Snake in the
     * board with its path.
     *
     * Time Complexity O(n), n is length of Snake.
     */
    public SnakeGame(int boardSizeX, int boardSizeY, Position tail, int snakeLength, char[] directions) {
        this.boardSizeX = boardSizeX;
        this.boardSizeY = boardSizeY;

        snakePath = new CircularQueue<>(snakeLength);
        buildSnakePath(tail, directions);
    }

    /**
     * Move the Snake until it collides with either Wall or Itself.
     *
     * Time Complexity: O(n), n is length or breadth of the board (bigger of N
     * or M)
     */
    public void moveUntilCollision(char lastDirection) {

        for (int countMoves = 0; true; countMoves++) {

            Position next = nextPostion(snakeHead, lastDirection);

            if (isWallCollision(next)) {
                // Print Wall
                System.out.println("WALL " + countMoves);
                return;
            }

            // Time Compleixty: O(1) - Its List
            snakePath.dequeue();

            if (isSelfCollision(next)) {
                // Print Body
                System.out.println("BODY " + countMoves);
                return;
            }

            // If no collision, then move snake and continue
            snakePath.enqueue(next);
            snakeHead = next;
        }
    }

    /**
     * Building the current Snake position of snake length using snake path for
     * previous L - 1 Steps taken by Snake.
     *
     * Time Complexity: O(n), n is length of Snake
     *
     * @param tailPos, currently tail is at this position. So before L -1 steps,
     * head was here. Utilizing the same property with directions to draw out
     * path.
     *
     * @param directions
     */
    private void buildSnakePath(Position tailPos, char[] directions) {

        Position pos = null;
        Position prev = tailPos;
        snakePath.enqueue(prev);
        for (int index = 0; index < directions.length; index++) {
            pos = nextPostion(prev, directions[index]);
            snakePath.enqueue(pos);
            prev = pos;
        }

        // Head becomes the front of queue(ie top position)
        snakeHead = pos;
    }

    /**
     * Get Next position for given direction and position.
     *
     * Time Complexity: O(1)
     *
     * @param pos
     * @param direction
     * @return
     */
    private Position nextPostion(Position pos, char direction) {
        int x = pos.x;
        int y = pos.y;

        switch (direction) {
            case 'L':
                x = x - 1;
                break;
            case 'R':
                x = x + 1;
                break;
            case 'U':
                y = y + 1;
                break;
            case 'D':
                y = y - 1;
                break;
        }
        return new Position(x, y);
    }

    /**
     * Time Complexity: O(1)
     *
     * @param pos
     * @return
     */
    private boolean isWallCollision(Position pos) {
        if (pos.x >= 0 && pos.y >= 0 && pos.x <= boardSizeX && pos.y <= boardSizeY) {
            return false;
        }
        return true;
    }

    /**
     * * Time Complexity: O(1)
     *
     * @param pos
     * @return
     */
    private boolean isSelfCollision(Position pos) {
        if (snakePath.contains(pos)) {
            return true;
        } else {
            return false;
        }
    }
}
