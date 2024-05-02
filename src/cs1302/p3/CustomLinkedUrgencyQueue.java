package cs1302.p3;

import cs1302.gen.Node;
import cs1302.gen.UrgencyQueue;
import java.util.Comparator;
import cs1302.oracle.OracleCustomLinkedUrgencyQueue;
import java.util.function.Predicate;

/**
 * A class for creating a CustomLinkedUrgencyQueue.
 */
public class CustomLinkedUrgencyQueue<Type> extends BaseLinkedUrgencyQueue<Type> {

    private Comparator<Type> comparator;

    /**
     * Construct a {@code CustomLinkedUrgencyQueue}.
     *
     * @param cmp a function that lets you determine the urgency order
     *     between two items
     * @throws NullPointerException if {@code cmp} is {@code null}
     */
    public CustomLinkedUrgencyQueue(Comparator<Type> cmp) {
        super();
        if (cmp == null) {
            throw new NullPointerException("The comparator function is null.");
        }
        comparator = cmp;
    } // CustomLinkedUrgencyQueue

    /**
     * Inserts item into urgency queue depending on its urgency. If it is more urgent,
     * the item is placed at the beginning of the list. Else, it is placed right after
     * any existing items with the same or greater level of urgency.
     * @param item the item to insert
     * @return true if urgency queue was modified as a result
     * @throws NullPointerException if item is null
     */
    public boolean enqueue(Type item) {
        if (item == null) {
            throw new NullPointerException("Item is null.");
        }

        Node<Type> newNode = new Node<Type>(item);
        Node<Type> now = head;
        boolean cond = false;
        if (head  == null || comparator.compare(head.getItem(), item) < 0) {
            newNode.setNext(head);
            head = newNode;
            size++;
            cond = true;
        } else if (comparator.compare(head.getItem(), item) >= 0) {
            while (now.hasNext() && comparator.compare(now.getNext().getItem(), item) >= 0) {
                now = now.getNext();
            }

            newNode.setNext(now.getNext());
            now.setNext(newNode);
            size++;
            cond = true;
            // Iterates through the queue to find the item with the least most urgency so that
            // the new node will be placed right after the found node from the iteration
        }

        return cond;
    }

    /**
     * Builds and returns a new urgency queue that contains the most num urgent items
     * dequeued from the original UrgencyQueue.
     * @param num the number of items to remove and return
     * @return a new urgency queue object containing the most urgent num items
     * @throws IllegalArgumentException if num is negative
     * @throws IllegalStateException if num is greater than the size of the queue.
     */
    public UrgencyQueue<Type> dequeueMany(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("The number is negative.");
        }
        if (num > size()) {
            throw new IllegalStateException("The number is greater than the size.");
        }

        CustomLinkedUrgencyQueue<Type> newQueue = new CustomLinkedUrgencyQueue<Type>(comparator);
        for (int i = 0; i < num; i++) {
            Type item = dequeue();
            newQueue.enqueue(item);
        }

        return newQueue;
    }

    /**
     * Builds and returns a new urgency queue consisting of the items that passed the
     * test specified by the given predicate. Predicate usually returns true or false.
     * @param cond the predicate used to test items of this queue.
     * @return a reference to the filtered queue.
     * @throws NullPointerException if predicate is null.
     */
    public UrgencyQueue<Type> filter(Predicate<Type> cond) {
        if (cond == null) {
            throw new NullPointerException("The predicate is null.");
        }
        UrgencyQueue<Type> newQueue = new CustomLinkedUrgencyQueue<Type>(comparator);
        Node<Type> current = head;
        while (current != null) {
            Type item = current.getItem();
            if (cond.test(item)) {
                newQueue.enqueue(item);
            }
            current = current.getNext();
        }

        return newQueue;
    }
} // CustomLinkedUrgencyQueue<Type>
