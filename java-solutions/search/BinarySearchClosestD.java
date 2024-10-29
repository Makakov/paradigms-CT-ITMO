package search;

public class BinarySearchClosestD {

    // Pre: {∀ i ∈ [0; arr.length - 2] : arr[i] >= arr[i + 1]}
    // const *arr and !arr.isEmpty() and n is int type and n != null
    private static int iterativeBinarySearch(int[] arr, int n) {
        if (arr.length == 1) {
            return arr[0];
            // Post: if arr.length == 1, it means that ∀ i != idx
            // Math.abs(arr[idx] - n) < Math.abs(arr[i] - n)
        }
        int right = arr.length - 1;
        int left = 0;
        // Invariant: const* arr and our n(or the candidate "the closest to n") is located
        // in arr[left + 1, right] or in arr[left - 1, right]
        while (right > left - 1) {
            int mid = (right + left) / 2;
            // mid > left:
            // left + 1 < right --> 2 * left + 1 < right + left
            // (2 * left + 1) / 2 < (left + right) / 2, also left < (2 * left + 1) / 2
            // --> left < (left + right) / 2 --> left < mid

            // mid < right:
            // left + 2 <= right
            // --> left + right < right + right
            // --> (left + right) / 2 < right --> mid < right
            if (arr[mid] > n) {
                left = mid + 1;
                // left' = mid + 1 && right' = right
            } else {
                right = mid - 1;
                // right' = mid - 1 && left' = left
            }
        }
        // left >= right + 1
        if (right == -1) {
            // {∀ i > 0 Math.abs(arr[left] - n) < Math.abs(arr[i] - n)}
            return arr[left];
            // Post: R == arr[left]
        }
        if (left == arr.length) {
            // {∀ i < arr.length - 1 Math.abs(arr[right] - n) < Math.abs(arr[i] - n)}
            return arr[right];
            // Post: R == arr[right]
        }
        return (Math.abs(arr[left] - n) < Math.abs(arr[right] - n)) ? arr[left] : arr[right];
        // Post: R == Math.abs(arr[left] - n) < Math.abs(arr[right] - n) ? arr[left] : arr[right]
    }

    // Pre: const *arr and !arr.isEmpty() and n is int type
    // and n != null
    private static int recursion(int[] arr, int n) {
        if (arr.length == 1) {
            return arr[0];
            // Post: if arr.length == 1, it means that there is only one such element
            // that is close to n
        }
        // Pre: const *arr and !arr.isEmpty()
        // {∀ i ∈ [0; arr.length - 2] : arr[i] >= arr[i + 1]}
        return recursiveBinarySearch(arr, n, 0, arr.length - 1);
        // Post: We found idx such arr[idx] is the closest number to n in an array
    }

    // Pre: const *arr && -1 <= left <= right - 1 < arr.length && arr[right] <= n <= arr[left]
    public static int recursiveBinarySearch(int[] arr, int n, int left, int right) {
        // Invariant: const* arr and our n(or the candidate "the closest to n") is located
        // in arr[left + 1, right] or in arr[left - 1, right]
        if (left - 1 < right) {
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
                return recursiveBinarySearch(arr, n, mid + 1, right);
                // left' = mid + 1 and right' = right
            } else {
                // arr[mid] <= n
                return recursiveBinarySearch(arr, n, left, mid - 1);
                // left' = left and right' = mid - 1
            }
        }
        // Post: left >= right + 1
        if (right == -1) {
            // {∀ i > 0 Math.abs(arr[left] - n) < Math.abs(arr[i] - n)}
            return arr[left];
            // Post R == arr[left]
        }
        if (left == arr.length) {
            // {∀ i < arr.length - 1 Math.abs(arr[right] - n) < Math.abs(arr[i] - n)}
            return arr[right];
            // Post: R == arr[right]
        }
        return (Math.abs(arr[left] - n) < Math.abs(arr[right] - n)) ? arr[left] : arr[right];
        // Post: R == Math.abs(arr[left] - n) < Math.abs(arr[right] - n) ? arr[left] : arr[right]
    }

    public static void main(String[] args) {
        // Pre: args.length >= 1
        // Pre: {∀ i ∈ [0; arr.length - 2] : arr[i] >= arr[i + 1]}
        // Pre: {∀ i ∈ [0; args.length - 1] INT_MIN <= Integer.parseInt(args[i]) <= INT_MAX}
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
        System.out.println(index);
    }
    // Post: We found such idx with iterative/recursive algorithm
}
