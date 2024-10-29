package search;

public class BinarySearch {

    private static int iterativeBinarySearch(int[] arr, int n) {
        // Pre: const *arr && left = -1 && right = arr.length
        // {∀ i ∈ [0; arr.length - 2] : arr[i] >= arr[i + 1]}
        int left = -1;
        int right = arr.length;
        // Invariant: idx in [left; right] && -1 <= left <= right - 1 < arr.length && arr[right] <= n <= arr[left]
        while (left != right - 1) {
            // ∀ i < left : arr[i] > n
            // left != right - 1
            int mid = (left + right) / 2;
            // mid > left:
            // left + 1 < right --> 2 * left + 1 < right + left
            // (2 * left + 1) / 2 < (left + right) / 2, also left < (2 * left + 1) / 2
            // --> left < (left + right) / 2 --> left < mid

            // mid < right:
            // left + 2 <= right
            // --> left + right < right + right
            // --> (left + right) / 2 < right --> mid < right
            if (arr[mid] > n) {
                // arr[mid] > n
                left = mid;
                // left' = mid, right' = right
            } else if (arr[mid] <= n) {
                // arr[mid] <= n
                // min idx in the [left; mid]
                // arr[left'] > n && arr[right'] <= n
                right = mid;
                // Post idx' = mid, right' = mid, left' = left
            }
        }
        // Post: R == min idx in [0; arr.length] : arr[idx] <= n && left == right - 1
        // if idx != -1 --> ∀ j: 0 <= j < idx arr[j] > n
        // Else if idx == -1 --> ∀ i arr[i] > n
        return right;
    }

    // Pre: const *arr && -1 <= left <= right - 1 < arr.length && arr[right] <= n <= arr[left]
    public static int recursiveBinarySearch(int[] arr, int n, int left, int right) {
        // Invariant const *arr && arr[right] <= n <= arr[left] && idx from [left; right]
        if (left != right - 1) {
            // left != right - 1
            int mid = (left + right) / 2;
            // mid > left:
            // left + 1 < right --> 2 * left + 1 < right + left
            // (2 * left + 1) / 2 < (left + right) / 2, also left < (2 * left + 1) / 2
            // --> left < (left + right) / 2 --> left < mid

            // mid < right:
            // left + 2 <= right
            // --> left + right < right + right
            // --> (left + right) / 2 < right --> mid < right
            if (arr[mid] > n) {
                // arr[mid] > n
                return recursiveBinarySearch(arr, n, mid, right);
                // left' = mid and right' = right
                // So, we remove all arr[i]: arr[i] > n, where i from [left; mid - 1]
            } else if (arr[mid] <= n) {
                // arr[mid] <= n
                int idx = recursiveBinarySearch(arr, n, left, mid);
                // Post: if idx == -1 it means that in [left; mid - 1]
                // there is no such element that arr[i] <= n
                // So, we return mid, because it is the min idx
                return idx != -1 ? idx : mid;
            }
        }
        // Post: left >= right - 1
        return -1;
        // Post: if ∃ arr[i] : arr[i] <= n && i - min => return idx
        // Post: else return -1 (arr[-1] = -∞)
    }

    // Pre: const *arr && {∀ i ∈ [0; arr.length - 2] : arr[i] >= arr[i + 1]}
    private static int recursion(int[] arr, int n) {
        return recursiveBinarySearch(arr, n, -1, arr.length);
        // Post: R == min idx in [0; arr.length] arr[idx] <= n
    }

    public static void main(String[] args) {
        // Pre: args.length >= 1
        // Pre: {∀ i ∈ [0; arr.length - 2] : arr[i] >= arr[i + 1]}
        // Pre: {∀ i ∈ [0; args.length - 1] INT_MIN <= Integer.parseInt(args[i]) <= INT_MAX}
        // Also, arr[-1] = +∞ and arr[arr.length] == -∞
        int n = Integer.parseInt(args[0]);
        int[] arr = new int[args.length - 1];
        int sum = 0;
        for (int i = 1; i < args.length; i++) {
            arr[i - 1] = Integer.parseInt(args[i]);
            sum += Integer.parseInt(args[i]);
        }
        // Pre: arr' = arr, n' = n
        int index;
        if (sum % 2 == 0) {
            index = recursion(arr, n);
        } else {
            index = iterativeBinarySearch(arr, n);
        }
        if (index == -1) {
            System.out.println(arr.length);
        } else {
            System.out.println(index);
        }
    }
    // Post: We found such idx with iterative/recursive algorithm
    // Post: our program returns such idx that arr[i] <= x, if there is no
    // such idx, it returns -1, it means that
    // ∀ i arr[i] > x, and we sout arr.length --> arr[arr.length] == -∞
}
