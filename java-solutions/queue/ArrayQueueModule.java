package queue;

import java.util.Objects;
import java.util.function.Predicate;

// Model: arr[1]...arr[n]
// Inv: n > 0 && forall i = head..n: a[i] != null

public class ArrayQueueModule {
    private static Object[] elements = new Object[0];
    private static int head = 0;
    private static int size = 0;

    // enqueue – добавить элемент в очередь;
    //element – первый элемент в очереди;
    //dequeue – удалить и вернуть первый элемент в очереди;
    //size – текущий размер очереди;
    //isEmpty – является ли очередь пустой;
    //clear – удалить все элементы из очереди.

    // Pre: element != null
    // Post: immutable(n) && n' = n + 1 && arr[n'] = element
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size + 1);
        elements[(head + size) % elements.length] = element;
        size++;
    }

    // Pre: n > 0
    // Post: R == arr[1] && R != null && for i in range(2, n): i' = i - 1
    public static Object dequeue() {
        assert size > 0;
        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return element;
    }

    // Pre: n > 0
    // Post: R == arr[1] && immutable(n)
    public static Object element() {
        assert size > 0;
        return elements[head];
    }

    private static void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            Object[] arr = new Object[capacity * 2];
            if (head + size <= elements.length) {
                System.arraycopy(elements, head, arr, 0, size);
                elements = arr;
                head = 0;
                return;
            }
            System.arraycopy(elements, head, arr, 0, size - head);
            System.arraycopy(elements, 0, arr, size - head, head);
            elements = arr;
            head = 0;
        }
    }

    // Pre: true
    // Post: R == n && immutable(n) && n' = n
    public static int size() {
        return size;
    }

    // Pre: true
    // Post: R == true if n == 0, else R == false && immutable(n) && n' = n
    public static boolean isEmpty() {
        return size == 0;
    }

    // Pre: true
    // Post: arr' = new Object[0] && n' = 0
    public static void clear() {
        elements = new Object[0];
        size = 0;
        head = 0;
    }

    // Pre: predicate != null
    // Post: R == count(predicate.test(arr[i])) && n' = n
    public static int countIf(Predicate<Object> predicate) {
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            if (predicate.test(elements[(i + head) % elements.length])) {
                cnt++;
            }
        }
        return cnt;
    }
}
