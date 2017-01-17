/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.util.Scanner;

/**
 *
 * @author prateek.kesarwani
 */
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
