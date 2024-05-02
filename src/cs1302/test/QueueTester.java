package cs1302.test;

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.List;
import cs1302.gen.UrgencyQueue;
import cs1302.gen.Node;
import cs1302.p3.BaseLinkedUrgencyQueue;
import cs1302.p3.LinkedUrgencyQueue;
import cs1302.p3.CustomLinkedUrgencyQueue;
import cs1302.oracle.model.Person.Order;
import cs1302.oracle.model.Person;

/**
 * Tester class for the classes inside cs1302.p3.
 */
public class QueueTester {
    public static void main(String[] args) {

        LinkedUrgencyQueue<Integer> queue = new LinkedUrgencyQueue<Integer>();
        testSize();
        testEnqueue(queue);
        System.out.println();
        testDequeue(queue);
        //Custom Queue test.
        Comparator<Person> byAge = (Person a, Person b) -> {
            return Integer.compare(a.age(), b.age());
        };


        UrgencyQueue<Person> queue2 = new CustomLinkedUrgencyQueue<Person>(byAge);
        Person dude = new Person(25, "Dude");
        Person young = new Person(6, "Timmy");
        Person between = new Person(18, "Million");
        queue2.enqueue(dude);
        System.out.print("After adding a 25 year old, " + queue2.toString());
        System.out.println(" Size is: " + queue2.size());
        queue2.enqueue(young);
        System.out.print("After adding a 6 year old, " + queue2.toString());
        System.out.println(" Size: " + queue2.size());
        Person older = new Person(65, "Tim");
        queue2.enqueue(older);
        System.out.print("After adding a old dude, " + queue2.toString());
        System.out.println(" Size is: " + queue2.size());

        Predicate<Person> youngerThan23 = person -> person.age() < 23;
        UrgencyQueue<Person> youngerThan23Queue = queue2.filter(youngerThan23);
        System.out.println("After filering to only under 23, " + youngerThan23Queue.toString());
    }
    /**
     * Method for testing the size.
     */

    public static void testSize() {
        UrgencyQueue<Integer> queue = new LinkedUrgencyQueue<Integer>();
        // Testing size on an empty queue
        if (queue.size() == 0) {
            System.out.println("size: Test Passed");
        } else {
            System.out.println("size: Test Failed");
            System.exit(0);
        } // if
    } // testSize

    /**
     * Method for testing the enqueue() method.
     * @param queue the queue the method accepts.
     */
    public static void testEnqueue(LinkedUrgencyQueue<Integer> queue) {
        queue.enqueue(1);
        System.out.println("After enqueuing 1 " + queue.toString() + " Size is: " + queue.size());
        queue.enqueue(9);
        System.out.println("After enqeuing 9 " + queue.toString() + " Size is: " + queue.size());
        queue.enqueue(12);
        System.out.println("After enqueuing 12 " + queue.toString() + " Size is: " + queue.size());
        queue.enqueue(12);
        System.out.println("After enqueuing 12 " + queue.toString() + " Size is: " + queue.size());
        queue.enqueue(3);
        System.out.println("After enqueuing 3 " + queue.toString() + " Size is: " + queue.size());
        queue.enqueue(4);
        System.out.println("After enqueuing 4 " + queue.toString() + " Size is: " + queue.size());
        queue.enqueue(14);
        System.out.println("After enqueuing 14 " + queue.toString() + " Size is: " + queue.size());
        queue.enqueue(9);
        System.out.println("After enqueuing 9 " + queue.toString() + " Size is: " + queue.size());
        System.out.println("Full size is: " + queue.size());
        System.out.println("Full array is: " + queue.toString());
    }

    /**
     * Testing the dequeue() method.
     * @param queue the queue the method takes as a parameter
     */

    public static void testDequeue(LinkedUrgencyQueue<Integer> queue) {
        queue.dequeue();
        System.out.println("After dequeuing, " + queue.toString() + " Size is: " + queue.size());

    }


}
