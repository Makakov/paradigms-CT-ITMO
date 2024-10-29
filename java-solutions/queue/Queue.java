package queue;

import java.util.List;
import java.util.function.Function;

// Model: arr[1]...arr[n]
// Invariant: n >= 0 && forall i in [0; n - 1] arr[i] != null
// Let: immutableOrderOfElements
public interface Queue {
    // Pre: n > 0
    // Post: R == arr[1] && R != null && for i in range(2, n): i' = i - 1
    Object dequeue();

    // Pre: element != null
    // Post: immutable(n) && n' = n + 1 && arr[n'] = element
    void enqueue(Object element);

    // Pre: n > 0
    // Post: R == arr[1] && immutable(n)
    Object element();

    // Pre: true
    // Post: R == n && immutable(n) && n' = n
    int size();

    // Pre: true
    // Post: arr' = new Object[0] && n' = 0
    void clear();

    // Pre: true
    // Post: R == true if n == 0, else R == false && immutable(n) && n' = n
    boolean isEmpty();


    // На FLATMAP не смотрите, я сделал для себя)


    // Pre: function != null
    // Post: R == new Queue && immutable(queue)
    Queue flatMap(Function<Object, List<Object>> function);

    void addToList(Object element, Queue queue, Function<Object, List<Object>> function);

    // :NOTE: post не правильный

    // Pre: true
    // Post: forall i in 1...n arr[i] != arr[i + 1]
    void dedup();
}
