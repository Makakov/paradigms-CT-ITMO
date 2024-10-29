package queue;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {

    private Object[] elements = new Object[0];

    private int head = 0;

    private void ensureCapacity(int capacity) {
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

    @Override
    protected void remove() {
        elements[head] = null;
        head = (head + 1) % elements.length;
    }

    @Override
    protected void pushImpl(Object element) {
        ensureCapacity(size + 1);
        elements[(head + size) % elements.length] = element;
    }

    @Override
    protected Object peek() {
        return elements[head];
    }

    @Override
    protected void initialize() {
        elements = new Object[0];
        head = 0;
    }

    // :NOTE: контракт для это функции пропал
    // зачем this?

    // Pre: predicate != null
    // Post: for i in (1, n) R == count(predicate.test(arr[i]))
    public int countIf(Predicate<Object> predicate) {
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            if (predicate.test(elements[(i + head) % elements.length])) {
                cnt++;
            }
        }
        return cnt;
    }

    @Override
    public Queue flatMap(Function<Object, List<Object>> function) {
        ArrayQueue queue = new ArrayQueue();
        for (int i = 0; i < size; i++) {
            addToList(elements[(i + head) % elements.length], queue, function);
        }
        return queue;
    }
}
