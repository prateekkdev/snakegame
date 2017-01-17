
import java.util.Scanner;

public class Main {

    /**
     * Play SnakeGame for each test case.
     *
     * @param args
     */
    public static void main(String[] args) {
        TestCase[] testCases = TestCase.readTestsConsole();
        for (TestCase tCase : testCases) {
            playSnakeGame(tCase);
        }
    }

    /**
     * Creating the SnakeGame with valid position Snake position of length l in
     * NxM board and with previous l-1 directions given along with tail
     * position.
     *
     * Time Complexity: O(max of Board length or width) + O(Snake Length) So,
     * would have linear complexity
     *
     * Space Complexity: O(Snake Length) - for Circular Queue
     *
     */
    private static void playSnakeGame(TestCase tCase) {
        char[] directions = tCase.direction.toCharArray();
        Position tail = new Position(tCase.x, tCase.y);

        // Create a snakegame with valid snake position after some steps.
        SnakeGame snakeGame = new SnakeGame(tCase.N, tCase.M,
                tail, tCase.l, directions);

        // Last direction will continue till collision with wall or body.
        snakeGame.moveUntilCollision(directions[directions.length - 1]);
    }
}

class SnakeGame {

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

class TestCase {

    int N;
    int M;
    int x;
    int y;
    int l;

    String direction;

    /**
     * Helper method
     */
    public static TestCase[] readTestsConsole() {
        Scanner scanner = new Scanner(System.in);

        int totalTestCases = scanner.nextInt();

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
        return testCaseArr;
    }

    /**
     * Helper method
     */
    public static TestCase[] readTestsHardcoded() {
        TestCase tCase = new TestCase();
        tCase.N = 6;
        tCase.M = 6;
        tCase.direction = "RRRRRDDDLLLU";
        tCase.l = 13;
        tCase.x = 0;
        tCase.y = 5;

        TestCase[] testCases = new TestCase[1];
        testCases[0] = tCase;
        return testCases;
    }
}

class Position {

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

class CircularQueue<E> {

    private final E[] circularQueueAr;
    private final int maxSize;   //Maximum Size of Circular Queue

    private int rear;//elements will be added/queued at rear.
    private int front;   //elements will be removed/dequeued from front      
    private int number; //number of elements currently in Priority Queue

    /**
     * Constructor
     */
    public CircularQueue(int maxSize) {
        this.maxSize = maxSize;
        circularQueueAr = (E[]) new Object[this.maxSize];
        number = 0; //Initially number of elements in Circular Queue are 0.
        front = 0;
        rear = 0;
    }

    /**
     * Adds element in Circular Queue(at rear)
     *
     * Time Complexity: O(1)
     */
    public void enqueue(E item) throws QueueFullException {
        if (isFull()) {
            throw new QueueFullException("Circular Queue is full");
        } else {
            circularQueueAr[rear] = item;
            rear = (rear + 1) % circularQueueAr.length;
            number++; // increase number of elements in Circular queue
        }
    }

    /**
     * Removes element from Circular Queue(from front)
     *
     * Time Complexity: O(1)
     */
    public E dequeue() throws QueueEmptyException {
        E deQueuedElement;
        if (isEmpty()) {
            throw new QueueEmptyException("Circular Queue is empty");
        } else {
            deQueuedElement = circularQueueAr[front];
            circularQueueAr[front] = null;
            front = (front + 1) % circularQueueAr.length;
            number--; // Reduce number of elements from Circular queue
        }
        return deQueuedElement;
    }

    /**
     * Return true if Circular Queue is full.
     */
    public boolean isFull() {
        return (number == circularQueueAr.length);
    }

    /**
     * Return true if Circular Queue is empty.
     */
    public boolean isEmpty() {
        return (number == 0);
    }

    /**
     * Time complexity: O(n), n is size of Circular Queue
     *
     * @param obj
     * @return
     */
    public boolean contains(E obj) {
        // We are nullifying objects during dequeue, so could check directly
        for (int index = 0; index < circularQueueAr.length; index++) {
            E current = circularQueueAr[index];
            if (current != null && current.equals(obj)) {
                return true;
            }
        }
        return false;
    }
}

class QueueFullException extends RuntimeException {

    public QueueFullException(String msg) {
        super(msg);
    }
}

class QueueEmptyException extends RuntimeException {

    public QueueEmptyException(String msg) {
        super(msg);
    }
}
