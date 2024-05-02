package cs1302.p3;

import cs1302.gen.UrgencyQueue;
import cs1302.gen.Node;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/**
 * The abstract class BaseLinkedUQ implements the interface UQ and depends on the Node
 * class inside the package cs1302.gen.
 */
public abstract class BaseLinkedUrgencyQueue<Type> implements UrgencyQueue<Type> {

    int size;

    Node<Type> head;

    /**
     * Construct a {@code BaseLinkedUrgencyQueue}. This constructor is never
     * intended to be called directly via {@code new}; instead, it should only
     * be called in child class constructors using {@code super()}.
     */
    public BaseLinkedUrgencyQueue() {
        size = 0;
        head = null;
    } // BaseLinkedUrgencyQueue

    /**
     * Returns the number of items in urgency queue.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Retrieves the most urgent item in the urgency queue.
     * @throws IllegalStateException if no items are in queue.
     * @return the most urgent item in queue.
     */
    @Override
    public Type peek() {
        if (size() == 0) {
            throw new IllegalStateException("There are no items in queue.");
        }
        return head.getItem();
    }

    /**
     * Returns a string representation of this urgency queue.
     *
     * @return string representation of this urgency queue
     */
    @Override
    public String toString() {
        String start = "[";
        Node<Type> headPrint = head;

        while (headPrint != null) {
            start = start + headPrint.getItem();
            if (headPrint.getNext() != null) {
                start += ", ";
            }
            headPrint = headPrint.getNext();
        }
        start += "]";
        return start;

    }

    /**
     * Retrieves and removes the most urgent item in urgency queue.
     *
     * @throws IllegalStateException if no items in queue.
     * @return the most urgent item in queue.
     */
    @Override
    public Type dequeue() {
        if (size() == 0) {
            throw new IllegalStateException("There are no items in the queue.");
        }

        Node<Type> newHead = head;
        head = head.getNext();
        size--;
        return newHead.getItem();

    }

    /**
     * Removes the most urgent item in queue, and does the given action on the removed item.
     * @param action the action to be performed on the removed item
     * @throws NullPointerException if action is null
     * @throws IllegalStateException if there are no items in queue
     */
    @Override
    public void dequeue(Consumer<Type> action) {
        if (action == null) {
            throw new NullPointerException("Action is null.");
        }

        if (size() == 0) {
            throw new IllegalStateException("There are no items in the queue.");
        }

        Node<Type> current = head;
        head = head.getNext();
        action.accept(current.getItem());
        size--;

    }

    /**
     * Removes a number of urgent items specified in the paramater and does the given action on each
     * removed item.
     * @param num the number of urgent items to remove.
     * @param action the action to be performed on the removed items.
     * @throws NullPointerException if action is null.
     * @throws IllegalArgumentException if num is negative.
     * @throws IllegalStateException if num is greater than size.
     */
    @Override
    public void dequeueMany(int num, Consumer<Type> action) {
        if (action == null) {
            throw new NullPointerException("The action is null.");
        }
        if (num < 0) {
            throw new IllegalArgumentException("The number is negative.");
        }
        if (num > size()) {
            throw new IllegalStateException("The number is greater than the size.");
        }


        for (int i = 0; i < num && size != 0; i++) {
            dequeue(action);
        }
    }

    /**
     * Removes all items in urgency queue.
     *
     */
    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Enqueues the items contained in the specified Iterable into queue.
     * @param <SubType> the type of items to be added.
     * @param items the items to add to this queue.
     * @return true if queue is changed; false otherwise.
     * @throws NullPointerException if items is null.
     * @throws IllegalArgumentException if any items in Iterable are null.
     */
    @Override
    public <SubType extends Type> boolean enqueueAll(Iterable<SubType> items) {
        if (items == null) {
            throw new NullPointerException("Items are null.");
        }
        for (SubType item : items) {
            if (item == null) {
                throw new IllegalArgumentException("An item in Iterable is null.");
            }
        }

        for (SubType item : items) {
            enqueue(item);
        }

        return items.iterator().hasNext();
    }

    /**
     * Returns an array in order of urgency. Paramter accepts a reference to any method
     * that takes the size of the desired array and makes an array of that desired size
     * @param generator a function which produces a new array of desired type and provided size
     * @return an array containing all of the items in queue in order of urgnecy
     * @throws NullPointerException if generator is null
     *
     */
    @Override
    public Type[] toArray(IntFunction<Type[]> generator) {
        if (generator == null) {
            throw new NullPointerException("The generator is null.");
        }

        Type[] array = generator.apply(size());
        int index = 0;
        Node<Type> current = head;

        while (current != null) {
            array[index] = current.getItem();
            index++;
            current = current.getNext();
        }

        return array;
    }
} // BaseLinkedUrgencyQueue<Type>
