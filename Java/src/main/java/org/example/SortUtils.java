package org.example;

import java.util.Collections;
import java.util.List;

// Реализуйте быструю сортировку
public class SortUtils {

    static <T extends Comparable<T>> void quickSort(List<T> array) {
        int low = 0;
        int high = array.size() - 1;

        int pivotIndex = partition(array, low, high);

        quickSort(array, low, pivotIndex - 1);
        quickSort(array, pivotIndex + 1, high);
    }

    static <T extends Comparable<T>> void quickSort(
            List<T> array,
            int low,
            int high
    ) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);

            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private static <T extends Comparable<T>> int partition(
            List<T> array,
            int low,
            int high
    ) {
        T pivot = array.get(high);

        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array.get(j).compareTo(pivot) < 0) {
                ++i;
                Collections.swap(array, i, j);
            }
        }
        Collections.swap(array, i + 1, high);

        return i + 1;
    }
}
