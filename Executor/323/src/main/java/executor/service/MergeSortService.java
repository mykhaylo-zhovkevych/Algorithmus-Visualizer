package executor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MergeSortService {

    public static List<List<Integer>> generateMergeSortSteps(List<Integer> array) {
        // System.out.println("Starting mergeSortWithSteps with array: " + array);

        // Sortierung starten und Schritte sammeln
        List<List<Integer>> steps = mergeSort(array).getSteps();
        // System.out.println("Final sorted array with steps: " + steps);
        return steps;
    }

    // Datentyp für das Ergebnis von MergeSort: sortierte Liste und Schritte
    private static class MergeResult {
        private final List<Integer> sortedList;
        private final List<List<Integer>> steps;

        MergeResult(List<Integer> sortedList, List<List<Integer>> steps) {
            this.sortedList = sortedList;
            this.steps = steps;
        }

        public List<Integer> getSortedList() {
            return sortedList;
        }

        public List<List<Integer>> getSteps() {
            return steps;
        }
    }

    // Funktionaler MergeSort, der ein Ergebnis mit sortierter Liste und Schritten zurückgibt
    private static MergeResult mergeSort(List<Integer> array) {
        
        // Basisfall: Der Basisfall in diesem Code prüft, ob die Liste aus einem einzigen Element besteht oder leer ist. Wenn die Bedingung array.size() <= 1 wahr ist, dann wird die Liste nicht weiter geteilt, sondern als bereits sortiert betrachtet
        // Der Basisfall wird für Listen mit einer Länge von 1 oder weniger ausgeführt
        if (array.size() <= 1) {
            // System.out.println("Base case reached with array: " + array);

            List<List<Integer>> initialStep = List.of(new ArrayList<>(array));
            // System.out.println(initialStep);
            return new MergeResult(array, initialStep);
        }

        // Teile die Liste in zwei Hälften
        int mid = array.size() / 2;
        List<Integer> left = array.subList(0, mid);
        List<Integer> right = array.subList(mid, array.size());
        // System.out.println("Split into left: " + left + " and right: " + right);

        // Rekursive Aufrufe: Sortiere beide Hälften und sammle Schritte
        MergeResult leftResult = mergeSort(left);
        MergeResult rightResult = mergeSort(right);

        // Mische die beiden sortierten Hälften
        MergeResult mergedResult = merge(leftResult.getSortedList(), rightResult.getSortedList());

        // Schritte kombinieren: Links, Rechts und Merge-Schritt
        List<List<Integer>> allSteps = new ArrayList<>();
        // Die Schritte, die beim Sortieren der linken Teilliste gesammelt wurden
        allSteps.addAll(leftResult.getSteps());
        // Die Schritte, die beim Sortieren der rechten Teilliste gesammelt wurden
        allSteps.addAll(rightResult.getSteps());
        // Die Schritte, die beim Zusammenführen der beiden Teillisten entstehen
        allSteps.addAll(mergedResult.getSteps());

        return new MergeResult(mergedResult.getSortedList(), allSteps);
    }

    // Diese Methode nutzt dynamische programmierung und sortiert nicht die Liste sonder die sub Elemente von Linke und dann Rechte Listen
    private static MergeResult merge(List<Integer> left, List<Integer> right) {

        // System.out.println("Merging left: " + left + " and right: " + right);
        List<Integer> merged = new ArrayList<>();
        int i = 0, j = 0;

        // Elemente aus beiden Listen in sortierter Reihenfolge zusammenführen
        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                merged.add(left.get(i));
                i++;
            } else {
                merged.add(right.get(j));
                j++;
            }
        }

        // Restliche Elemente aus der linken Liste anhängen
        while (i < left.size()) {
            merged.add(left.get(i));
            i++;
        }

        // Restliche Elemente aus der rechten Liste anhängen
        while (j < right.size()) {
            merged.add(right.get(j));
            j++;
        }

        // Schritt hinzufügen
        List<List<Integer>> steps = List.of(new ArrayList<>(merged));
        // System.out.println("Step after merging: " + merged);
        return new MergeResult(merged, steps);
    }
}