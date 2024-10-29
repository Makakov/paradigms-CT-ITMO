package queue;

public class MyADTQueueTest {

    public static void fill(ArrayQueueADT queue) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queue, i + 10);
        }
    }

    public static void stringFill(ArrayQueueADT queue) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queue, "a_" + Integer.toString(i));
        }
    }

    public static void dump(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(ArrayQueueADT.size(queue) + " " + ArrayQueueADT.element(queue) +
                    " " + ArrayQueueADT.dequeue(queue));
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT queue1 = new ArrayQueueADT();
        ArrayQueueADT queue2 = new ArrayQueueADT();
        fill(queue1);
        stringFill(queue2);
        System.out.println("First queue:\n");
        dump(queue1);
        System.out.println("\nSecond queue:\n");
        dump(queue2);
    }
}
