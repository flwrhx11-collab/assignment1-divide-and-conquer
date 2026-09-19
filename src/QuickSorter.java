import java.util.Random;

public class QuickSorter {
    private static final Random RANDOM = new Random();

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(int[] arr, int lo, int hi) {
        while (lo < hi) {
            int pivotIndex = lo + RANDOM.nextInt(hi - lo + 1);
            swap(arr, pivotIndex, hi);

            int p = partition(arr, lo, hi);

            if (p - lo < hi - p) {
                sort(arr, lo, p - 1);
                lo = p + 1;
            } else {
                sort(arr, p + 1, hi);
                hi = p - 1;
            }
        }
    }

    private static int partition(int[] arr, int lo, int hi) {
        int pivot = arr[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, hi);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}