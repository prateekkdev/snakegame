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
public class CircularQueue<E> {

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
