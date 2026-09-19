public class DeterministicSelector {

    public static int select(int[] arr, int k) {
        if (arr == null || arr.length == 0) throw new IllegalArgumentException("Array cannot be empty");
        return select(arr, 0, arr.length - 1, k);
    }

    private static int select(int[] arr, int lo, int hi, int k) {
        if (lo == hi) return arr[lo];

        int pivot = medianOfMedians(arr, lo, hi);

        int pivotIndex = partition(arr, lo, hi, pivot);

        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return select(arr, lo, pivotIndex - 1, k);
        } else {
            return select(arr, pivotIndex + 1, hi, k);
        }
    }

    private static int medianOfMedians(int[] arr, int lo, int hi) {
        int n = hi - lo + 1;
        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            int groupLo = lo + i * 5;
            int groupHi = Math.min(lo + i * 5 + 4, hi);
            medians[i] = findMedian(arr, groupLo, groupHi);
        }

        if (numGroups == 1) {
            return medians[0];
        }
        return select(medians, 0, numGroups - 1, numGroups / 2);
    }

    private static int findMedian(int[] arr, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= lo && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
        return arr[lo + (hi - lo) / 2];
    }

    private static int partition(int[] arr, int lo, int hi, int pivot) {
        int pivotIndex = lo;
        for (int i = lo; i <= hi; i++) {
            if (arr[i] == pivot) {
                pivotIndex = i;
                break;
            }
        }
        swap(arr, pivotIndex, hi);

        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, hi);
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}