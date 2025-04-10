package pac;

import java.util.Arrays;
import java.util.Scanner;

public class ImplementQueue {
    static Scanner scannerObject = new Scanner(System.in);
    static int queueSize;
    static int front = -1;
    static int rear = -1;
    static int queueArray[];

    public static void main(String[] args) {

        // get size of queue
        queueSize = getSizeOfQueue();       
        // initializing arrayqueue of size provided by the user
        queueArray = new int[queueSize];
        // initailizing array with positive infinity value
        Arrays.fill(queueArray, Integer.MAX_VALUE);

        boolean isValid = true;
        while (isValid) {
            try {
                System.out.println("Choose which operation you want to perform\n"
                        + "1.Add an element to queue\n" +
                        "2.Remove an elemt from queue\n"
                        + "3.Check whether queue is empty or not\n"
                        + "4.Check wheher queue is full or  not\n"
                        + "5.Exit");
                int input = scannerObject.nextInt();
                switch (input) {
                    // case 1 for enqueue operation
                    case 1:
                        boolean isAdded = enqueue();
                        if (isAdded)
                            System.out.println("Element added successfullly");
                        else
                            System.out.println("Queue is full\n" +
                                    "Item cannot be added");
                        break;
                    // case 2 for dequeue operation
                    case 2:
                        boolean isRemoved = dequeue();
                        if (isRemoved)
                            System.out.println("Element deleted successfullly");
                        else
                            System.out.println("Queue is empty\n" +
                                    "Item cannot be deleted");
                        break;
                    // case 3 for checking queue is empty or not
                    case 3:
                        boolean isQueueEmpty = isEmpty();
                        if (isQueueEmpty)
                            System.out.println("Queue is empty");
                        else
                            System.out.println("Queue is not empty");
                        break;
                    // case 4 for checking queue is full or not
                    case 4:
                        boolean isQueueFull = isFull();
                        if (isQueueFull)
                            System.out.println("Queue is full");
                        else
                            System.out.println("Queue is not full");
                        break;
                    // case 5 for terminating the program
                    case 5:
                        System.out.println("Program terminated successfully");
                        isValid = false;
                        break;
                    // default case for handling edge cases
                    default:
                        System.out.println("Please select valid option");
                }

            } catch (Exception e) {
                if (e.getMessage() == null)
                    System.out.println(e);
                else
                    System.out.println(e.getMessage());
                scannerObject.nextLine();
            }
        }
    }

    /**
     * get size of queue
     * 
     * @return size of queue
     */
    static public int getSizeOfQueue() {
        System.out.println("Enter size of queue");
        if (scannerObject.hasNextInt()) {
            int sizeQueue = scannerObject.nextInt();
            if (sizeQueue > 0) {
                return sizeQueue;
            } else {
                System.out.println("Enter valid size");
                return getSizeOfQueue();
            }
        } else {
            System.out.println("Enter valid input");
            scannerObject.next();
            return getSizeOfQueue();
        }
    }

    /**
     * add an element to queue
     * 
     * @return boolean value->whether an element is added or not
     */
    static boolean enqueue() {
        if (isFull()) {
            
            return false;

        } else {
            if (rear == queueArray.length - 1)
                rear = 0;
            else
                rear++;
            System.out.println("Enter value in queue");
            queueArray[rear] = scannerObject.nextInt();
            return true;
        }
    }

    /**
     * check whether queue is full or not
     * 
     * @return boolean value->queue is full or not
     */
    static boolean isFull() {
        if (front < 0 && rear == queueArray.length - 1)
            return true;
        else
            return (rear + 1) % queueArray.length == front;

    }

    /**
     * delete an element from queue
     * 
     * @return boolean value->whether an element is deleted or not
     */
    static boolean dequeue() {
        if (isEmpty()) {
           
            return false;
        } else {
            if (front == -1)
                front++;
            queueArray[front] = Integer.MAX_VALUE;
            if (front == queueArray.length - 1)
                front = 0;
            else
                front++;
            return true;
        }

    }

    /**
     * check whether queue is empty or not
     * 
     * @return boolean value->queue is empty or not
     */
    static public boolean isEmpty() {
        if (rear < 0)
            return true;
        else
            return rear + 1 == front;
    }
}
