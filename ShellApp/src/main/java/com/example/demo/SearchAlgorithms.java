package com.example.demo;

import java.util.function.BiFunction;

public class SearchAlgorithms {

    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return mid;
            }
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static int ternarySearch(int[] arr, int target) {
        return ternarySearch(arr, target, 0, arr.length - 1);
    }

    private static int ternarySearch(int[] arr, int target, int left, int right) {
        if (right >= left) {
            int mid1 = left + (right - left) / 3;
            int mid2 = right - (right - left) / 3;

            if (arr[mid1] == target) return mid1;
            if (arr[mid2] == target) return mid2;

            if (target < arr[mid1]) {
                return ternarySearch(arr, target, left, mid1 - 1);
            } else if (target > arr[mid2]) {
                return ternarySearch(arr, target, mid2 + 1, right);
            } else {
                return ternarySearch(arr, target, mid1 + 1, mid2 - 1);
            }
        }
        return -1;
    }

    public static long measureTime(BiFunction<int[], Integer, Integer> searchFunction, int[] arr, int target) {
        long start = System.nanoTime();
        searchFunction.apply(arr, target);
        return System.nanoTime() - start;
    }
}
