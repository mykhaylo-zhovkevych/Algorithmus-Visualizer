package com.example.demo;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShellCommands {

    // Dank Map wird die keys(String) mit values(Function<int[], int[]>) gespeichert
    // Die Function<int[], int[]> ist funktionales Interface und Es steht für eine Funktion, die einen Input T entgegennimmt und ein Ergebnis R zurückliefert
    // also Input int [] unsortieretes Array und Output int [] sortiertes Array
    private static final Map<String, Function<int[], int[]>> SORT_ALGORITHMS = Map.of(
            "bubble", SortAlgorithms::bubbleSort,
            "insertion", SortAlgorithms::insertionSort,
            "merge", SortAlgorithms::mergeSort,
            "quick", SortAlgorithms::quickSort
    );

    private static final Map<String, BiFunction<int[], Integer, Integer>> SEARCH_ALGORITHMS = Map.of(
            "linear", SearchAlgorithms::linearSearch,
            "binary", SearchAlgorithms::binarySearch,
            "ternary", SearchAlgorithms::ternarySearch
    );

    @ShellMethod(key = "benchmark", value = "Vergleicht zwei Algorithmen parallel.")
    public String benchmark(String algo1, String algo2) {
        int[] testData = generateRandomArray(100000);

        System.out.println("Die Data (erste 100 Werte): " + Arrays.toString(Arrays.copyOf(testData, 100)));

        if (SORT_ALGORITHMS.containsKey(algo1) && SORT_ALGORITHMS.containsKey(algo2)) {
            return compareSortingAlgorithms(algo1, algo2, testData);
        } else if (SEARCH_ALGORITHMS.containsKey(algo1) && SEARCH_ALGORITHMS.containsKey(algo2)) {
            return compareSearchAlgorithms(algo1, algo2, testData);
        } else {
            return "Fehler: Bitte zwei gueltige Sortier- oder Suchalgorithmen angeben.";
        }
    }

    private String compareSortingAlgorithms(String algo1, String algo2, int[] data) {
        // Führt eine Berechnung in einem separaten Thread aus
        CompletableFuture<Long> time1 = CompletableFuture.supplyAsync(() -> SortAlgorithms.measureTime(SORT_ALGORITHMS.get(algo1), data.clone()));
        CompletableFuture<Long> time2 = CompletableFuture.supplyAsync(() -> SortAlgorithms.measureTime(SORT_ALGORITHMS.get(algo2), data.clone()));

        double seconds1 = time1.join() / 1_000_000_000.0;
        double seconds2 = time2.join() / 1_000_000_000.0;

    return String.format("""
            Sortier-Benchmark:
            %s Sort: %d ns (%.6f s)
            %s Sort: %d ns (%.6f s)
            """, algo1, time1.join(), seconds1, algo2, time2.join(), seconds2);
    }

    private String compareSearchAlgorithms(String algo1, String algo2, int[] data) {
        Arrays.sort(data);
        int target = data[data.length / 2];

        CompletableFuture<Long> time1 = CompletableFuture.supplyAsync(() -> SearchAlgorithms.measureTime(SEARCH_ALGORITHMS.get(algo1), data, target));
        CompletableFuture<Long> time2 = CompletableFuture.supplyAsync(() -> SearchAlgorithms.measureTime(SEARCH_ALGORITHMS.get(algo2), data, target));

        double seconds1 = time1.join() / 1_000_000_000.0;
        double seconds2 = time2.join() / 1_000_000_000.0;

    return String.format("""
            Such-Benchmark:
            %s Suche: %d ns (%.6f s)
            %s Suche: %d ns (%.6f s)
            """, algo1, time1.join(), seconds1, algo2, time2.join(), seconds2);
    }

    private int[] generateRandomArray(int size) {
        // Wandelt das Array in einen IntStream um
        return Arrays.stream(new int[size])
                                    // Ersetzt jedes Element (i) im Stream mit einem neuen Zufallswert zwischen 0 und 9999
                                    .map(i -> (int) (Math.random() * 10000))
                                    .toArray();
    }
}