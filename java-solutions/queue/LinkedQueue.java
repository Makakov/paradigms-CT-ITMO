package queue;

import java.util.List;
import java.util.function.Function;

public class LinkedQueue extends AbstractQueue {
    private Node tail = null;
    private Node head = null;

    @Override
    public Queue flatMap(Function<Object, List<Object>> function) {
        LinkedQueue queue = new LinkedQueue();
        Node temp = head;
        for (int i = 0; i < size; i++) {
            addToList(temp.value, queue, function);
            temp = temp.next;
        }
        return queue;
    }

    private static class Node {
        private final Object value;
        private Node next;

        public Node(Object value, Node next) {
            assert value != null;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    protected void remove() {
        head = head.next;
        if (head == null) {
            tail = null;
        }
    }

    @Override
    protected void pushImpl(Object element) {
        if (tail == null) {
            head = tail = new Node(element, null);
            return;
        }
        Node temp = new Node(element, null);
        tail.next = temp;
        tail = temp;
    }

    @Override
    protected Object peek() {
        return head.value;
    }

    @Override
    protected void initialize() {
        head = null;
        tail = null;
    }
}
