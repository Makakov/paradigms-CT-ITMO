package queue;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractQueue implements Queue {

    protected int size = 0;

    protected abstract void remove();

    protected abstract void pushImpl(Object element);

    protected abstract Object peek();

    protected abstract void initialize();


    @Override
    public Object dequeue() {
        assert size > 0;
        Object element = peek();
        size--;
        remove();
        return element;
    }

    @Override
    public void enqueue(Object element) {
        Objects.requireNonNull(element);
        pushImpl(element);
        size++;
    }

    @Override
    public Object element() {
        assert size > 0;
        return peek();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        initialize();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void addToList(Object element, Queue queue, Function<Object, List<Object>> function) {
        List<Object> list = function.apply(element);
        for (Object obj : list) {
            queue.enqueue(obj);
        }
    }

    // Pre: true
    // Post: for i in 1...n - 1: arr[i] != arr[i + 1] && n > 0 &&
    // immutableOrderOfElements && let cnt = 0, for i in 1...n - 1:
    // if (arr[i] == arr[i + 1]) cnt++ --> size -= cnt
    @Override
    public void dedup() {
        Object curr = null;
        Object next;
        int tempSize = this.size();
        for (int i = 0; i < tempSize; i++) {
            next = this.dequeue();
            if (!Objects.equals(curr, next)) {
                this.enqueue(next);
                curr = next;
            }
        }
    }
}
