package executor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class QuickSortService {

    public static List<List<Integer>> generateQuickSortSteps(List<Integer> array) {
        List<List<Integer>> steps = new ArrayList<>();
        quickSort(array, 0, array.size() - 1, steps);
        return steps;
    }

    private static void quickSort(List<Integer> array, int low, int high, List<List<Integer>> steps) {
        // Wenn der Bereich des Arrays mehr als ein Element enthält, wird partitioniert und weiter sortiert
        if (low < high) {
      
            // Partitionierung des Arrays und Ermittlung des Pivot-Index
            int pi = partition(array, low, high, steps);

            // Rekursiver Aufruf der quickSort-Methode für den linken Teil des Arrays (vor dem Pivot)
            quickSort(array, low, pi - 1, steps);
            // Rekursiver Aufruf der quickSort-Methode für den rechten Teil des Arrays (nach dem Pivot)
            quickSort(array, pi + 1, high, steps);
        }
    }

    private static int partition(List<Integer> array, int low, int high, List<List<Integer>> steps) {
        int pivot = array.get(high);
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            // Wenn das aktuelle Element kleiner als der Pivot ist, tausche es mit dem Element an Index i
            if (array.get(j) < pivot) {
                i++;
                swap(array, i, j);
                if (!steps.contains(new ArrayList<>(array))) {
                    steps.add(new ArrayList<>(array));
                }
            }
        }

        swap(array, i + 1, high);
        if (!steps.contains(new ArrayList<>(array))) {
            steps.add(new ArrayList<>(array));
        }
        return i + 1;
    }

    private static void swap(List<Integer> array, int i, int j) {
        int temp = array.get(i);  // Speichere das Element an Index i
        array.set(i, array.get(j));  // Setze das Element an Index j an die Stelle von Index i
        array.set(j, temp);  // Setze das Element an Index i an die Stelle von Index j
    }
}