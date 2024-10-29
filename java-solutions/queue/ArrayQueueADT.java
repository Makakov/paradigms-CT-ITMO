package queue;

import java.util.Objects;
import java.util.function.Predicate;

// Model: arr[1]...arr[size]
// Inv: n >= 0 && forall i = head..n: a[i] != null

public class ArrayQueueADT {
    private Object[] elements = new Object[0];
    private int size = 0;
    private int head = 0;

    // enqueue – добавить элемент в очередь;
    //element – первый элемент в очереди;
    //dequeue – удалить и вернуть первый элемент в очереди;
    //size – текущий размер очереди;
    //isEmpty – является ли очередь пустой;
    //clear – удалить все элементы из очереди.

    // Pre: element != null && queue != null
    // Post: immutable(n) && n' = n && arr[n'] = element
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, queue.size + 1);
        queue.elements[(queue.head + queue.size) % queue.elements.length] = element;
        queue.size++;
    }

    // Pre: n > 0 && queue != null
    // Post: R == arr[1] && R != null && for i in range(2, n): i' = i - 1
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object element = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        queue.size--;
        return element;
    }

    // Pre: n > 0 && queue != null
    // Post: R == queue.elements[queue.head] && queue.head' = queue.head
    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.head];
    }

    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity > queue.elements.length) {
            Object[] arr = new Object[capacity * 2];
            if (queue.head + queue.size <= queue.elements.length) {
                System.arraycopy(queue.elements, queue.head, arr, 0, queue.size);
                queue.elements = arr;
                queue.head = 0;
                return;
            }
            System.arraycopy(queue.elements, queue.head, arr, 0, queue.size - queue.head);
            System.arraycopy(queue.elements, 0, arr, queue.size - queue.head, queue.head);
            queue.elements = arr;
            queue.head = 0;
        }
    }

    // Pre: queue != null
    // Post: R == n && immutable(n) && n' = n
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // Pre: queue != null
    // Post: R == true if n == 0, else R == false && immutable(n) && n' = n
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // Pre: queue != null
    // Post: arr' = new Object[0] && n' = 0
    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[0];
        queue.size = 0;
        queue.head = 0;
    }

    // Pre: predicate != null && queue != null
    // Post: R == count(predicate.test(arr[i])) && n' = n
    public static int countIf(ArrayQueueADT queue, Predicate<Object> predicate) {
        int cnt = 0;
        for (int i = 0; i < queue.size; i++) {
            if (predicate.test(queue.elements[(i + queue.head) % queue.elements.length])) {
                cnt++;
            }
        }
        return cnt;
    }
}
