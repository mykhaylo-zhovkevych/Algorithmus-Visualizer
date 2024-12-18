package executor.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BubbleSortService {

    public List<List<Integer>> generateBubbleSortSteps(List<Integer> array) {
        List<List<Integer>> steps = new ArrayList<>();
        Integer[] arr = array.toArray(new Integer[0]);

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Elemente tauschen
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    // Schritt speichern
                    steps.add(Arrays.asList(arr.clone()));
                }
            }
        }
        return steps; 
    }
}