package snakegame;

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
     * Time Complexity: O(max of Board length or width) + O(Snake Length). So,
     * would have linear complexity
     *
     * Space Complexity: O(Snake Length), needed to maintain circular queue
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
