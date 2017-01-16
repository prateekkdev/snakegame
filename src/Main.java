/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        playSnakeGame();
    }

    public static void playSnakeGame() {

        Scanner scanner = new Scanner(System.in);

        int totalTestCases = scanner.nextInt();

        class TestCase {

            int N;
            int M;
            int x;
            int y;
            int l;

            String direction;
        }

        TestCase[] testCaseArr = new TestCase[totalTestCases];
        for (int index = 0; index < totalTestCases; index++) {

            TestCase tCase = new TestCase();

            tCase.N = scanner.nextInt();
            tCase.M = scanner.nextInt();

            // We are working with 0 based.
            tCase.x = scanner.nextInt() - 1;

            // We are working with 0 based.
            tCase.y = scanner.nextInt() - 1;

            tCase.l = scanner.nextInt();

            // Could have done with String itself, but for better understanding.
            tCase.direction = scanner.next();

            testCaseArr[index] = tCase;
        }

        for (int index = 0; index < totalTestCases; index++) {
            TestCase tCase = testCaseArr[index];
            char[] directions = tCase.direction.toCharArray();
            BoardPosition tail = new BoardPosition(tCase.x, tCase.y);
            SnakeGame snakeGame = new SnakeGame(tCase.N, tCase.M,
                    tail, tCase.l);
            snakeGame.buildSnakePath(tail, directions);

            // Last direction will continue till collision
            snakeGame.moveUntilCollision(directions[directions.length - 1]);
        }
    }

    // Just a utility function to get direction enum, for better understanding.
    private static Direction[] getDirectionFromString(String direction) {

        Direction[] arr = new Direction[direction.length()];

        for (int index = 0; index < direction.length(); index++) {
            char d = direction.charAt(index);
            arr[index] = d == 'L' ? Direction.LEFT : d == 'R' ? Direction.RIGHT : d == 'U' ? Direction.UP : Direction.DOWN;
        }
        return arr;
    }

    enum Direction {
        LEFT, RIGHT, UP, DOWN;
    }
}

/**
 *
 * @author prateek.kesarwani
 */
class BoardPosition {

    public int x;
    public int y;

    public BoardPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

/**
 *
 * @author prateek.kesarwani
 */
class SnakeGame {

    int boardSizeX;
    int boardSizeY;
    int snakeLength;
    BoardPosition snakeHead;

    Queue<BoardPosition> snakePath;

    public SnakeGame(int boardSizeX, int boardSizeY, BoardPosition tail, int snakeLength) {
        this.boardSizeX = boardSizeX;
        this.boardSizeY = boardSizeY;
        this.snakeLength = snakeLength;

        // This snakeHead is before L - 1 steps.
        // this.snakeHead = new BoardPosition(tail.getX(), tail.getY());
        snakePath = new LinkedList<>();
    }

    public void buildSnakePath(BoardPosition tailPos, char[] directions) {

        BoardPosition pos = null;
        BoardPosition prev = tailPos;
        snakePath.add(prev);
        for (int index = 0; index < directions.length; index++) {
            pos = getNextPosition(prev, directions[index]);
            snakePath.add(pos);
            prev = pos;
        }

        // Head becomes the front of queue(ie top position)
        snakeHead = pos;
    }

    public void moveUntilCollision(char lastDirection) {

        for (int countMoves = 0; true; countMoves++) {

            BoardPosition next = getNextPosition(snakeHead, lastDirection);

            if (isWallCollision(next)) {
                // Print Wall
                System.out.println("WALL " + countMoves);
                return;
            }

            snakePath.remove();

            if (isSelfCollision(next)) {
                // Print Body
                System.out.println("BODY " + countMoves);
                return;
            }

            // If no collision, then move snake and continue
            snakePath.add(next);
            snakeHead = next;
        }
    }

    private BoardPosition getNextPosition(BoardPosition pos, char direction) {
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
        return new BoardPosition(x, y);
    }

    private boolean isWallCollision(BoardPosition pos) {
        if (pos.x >= 0 && pos.y >= 0 && pos.x <= boardSizeX && pos.y <= boardSizeY) {
            return false;
        }
        return true;
    }

    private boolean isSelfCollision(BoardPosition pos) {
        if (snakePath.contains(pos)) {
            return true;
        } else {
            return false;
        }
    }
}
