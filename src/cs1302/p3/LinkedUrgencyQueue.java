package cs1302.p3;

import cs1302.gen.UrgencyQueue;
import cs1302.oracle.OracleLinkedUrgencyQueue;
import cs1302.gen.Node;
import java.util.function.Predicate;

/**
 * A class for creating a LinkedUrgencyQueue.
 */
public class LinkedUrgencyQueue<Type extends Comparable<Type>>
    extends BaseLinkedUrgencyQueue<Type> {

    /**
     * Construct a {@code LinkedUrgencyQueue}.
     */
    public LinkedUrgencyQueue() {
        super();
    } // LinkedUrgencyQueue

    /**
     * Inserts item into Linked Urgnecy Queue depending on its urgency.
     * @param item the item to insert
     * @return true if urgency queue was modified as a result of this call
     * @throws NullPointerException if item is null
     */
    @Override
    public boolean enqueue(Type item) {
        if (item == null) {
            throw new NullPointerException("The item paramater is null.");
        }

        Node<Type> newNode = new Node<Type>(item);
        Node<Type> current = head;
        boolean cond = false;

        // Checks if queue is empty or head is less urgent than item paramater.
        // The head will now point to the newNode and will be placed at the beginning.
        if (head  == null || head.getItem().compareTo(item) < 0) {
            newNode.setNext(head);
            head = newNode;
            size++;
            cond = true;
        } else if (head.getItem().compareTo(item) >= 0) {
            while (current.hasNext() && current.getNext().getItem().compareTo(item) >= 0) {
                current = current.getNext();
            }

            newNode.setNext(current.getNext());
            current.setNext(newNode);
            size++;
            cond = true;
            // Iterates through the queue to find the item with the least most urgency so that
            // the new node will be placed right after the found node from the iteration
        }

        return cond;
    }

    /**
     * Builds and returns a new urgency queue that contains the most urgency num items.
     * @param num the number of items to remove and return
     * @return a new urgency queue object containign the most urgent num items dequeued from queue
     * @throws IllegalArgumentException if paramater is negative
     * @throws IllegalStateException if the paramter is greater than size.
     */
    @Override
    public UrgencyQueue<Type> dequeueMany(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Paramater is negative.");
        }
        if (num > size()) {
            throw new IllegalStateException("Paramater is greater than size.");
        }

        UrgencyQueue<Type> queue = new LinkedUrgencyQueue<Type>();
        for (int i = 0; i < num; i++) {
            queue.enqueue(dequeue());
        }
        return queue;
    }

    /**
     * Builds and returns a new urgency queue consisting of items that pass the test
     * given by the predicate.
     * @param cond the predicate used to test items of this queue.
     * @return a reference to the filtered queue.
     * @throws NullPointerException if predicate is null.
     */
    @Override
    public UrgencyQueue<Type> filter(Predicate<Type> cond) {
        if (cond == null) {
            throw new NullPointerException("The predicate is null.");
        }
        UrgencyQueue<Type> queue = new LinkedUrgencyQueue<Type>();
        Node<Type> current = head;
        while (current != null) {
            if (cond.test(current.getItem())) {
                queue.enqueue(current.getItem());
            }
            current = current.getNext();
        }

        return queue;
    }
} // LinkedUrgencyQueue<Type extends Comparable<Type>>
