package com.example.demo;

import java.util.function.Function;

public class SortAlgorithms {

    public static int[] bubbleSort(int[] arr) {
        int[] copy = arr.clone();
        for (int i = 0; i < copy.length - 1; i++) {
            for (int j = 0; j < copy.length - i - 1; j++) {
                if (copy[j] > copy[j + 1]) {
                    int temp = copy[j];
                    copy[j] = copy[j + 1];
                    copy[j + 1] = temp;
                }
            }
        }
        return copy;
    }

    public static int[] insertionSort(int[] arr) {
        int[] copy = arr.clone();
        for (int i = 1; i < copy.length; i++) {
            int key = copy[i];
            int j = i - 1;
            while (j >= 0 && copy[j] > key) {
                copy[j + 1] = copy[j];
                j--;
            }
            copy[j + 1] = key;
        }
        return copy;
    }

    public static int[] mergeSort(int[] arr) {
        int[] copy = arr.clone();
        mergeSortRecursive(copy);
        return copy;
    }

    private static void mergeSortRecursive(int[] arr) {
        if (arr.length > 1) {
            int mid = arr.length / 2;
            int[] left = new int[mid];
            int[] right = new int[arr.length - mid];

            System.arraycopy(arr, 0, left, 0, mid);
            System.arraycopy(arr, mid, right, 0, arr.length - mid);

            mergeSortRecursive(left);
            mergeSortRecursive(right);

            int i = 0, j = 0, k = 0;
            while (i < left.length && j < right.length) {
                arr[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
            }
            while (i < left.length) arr[k++] = left[i++];
            while (j < right.length) arr[k++] = right[j++];
        }
    }

    public static int[] quickSort(int[] arr) {
        int[] copy = arr.clone();
        quickSortRecursive(copy, 0, copy.length - 1);
        return copy;
    }

    private static void quickSortRecursive(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            quickSortRecursive(arr, low, pivot - 1);
            quickSortRecursive(arr, pivot + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                int temp = arr[++i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[++i];
        arr[i] = arr[high];
        arr[high] = temp;
        return i;
    }

    public static long measureTime(Function<int[], int[]> sortFunction, int[] arr) {
        long start = System.nanoTime();
        sortFunction.apply(arr);
        return System.nanoTime() - start;
    }
}
