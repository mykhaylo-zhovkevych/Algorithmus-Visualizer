package executor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import executor.model.UserChoiceRequest;
import executor.service.BinäreSucheService;
import executor.service.BubbleSortService;
import executor.service.InsertionSortService;
import executor.service.LineareSucheService;
import executor.service.MergeSortService;
import executor.service.QuickSortService;
import executor.service.TernäreSucheService;


@RestController
@RequestMapping("/api")
public class AlgorithmSortController {

    @Autowired
    private BubbleSortService bubbleSortService;

    @Autowired
    private InsertionSortService insertionSortService;

    @Autowired
    private MergeSortService mergeSortService;

    @Autowired
    private QuickSortService quickSortService;


    @Autowired
    private LineareSucheService lineareSucheService; 

    @Autowired
    private BinäreSucheService binäreSucheService; 

    @Autowired
    private TernäreSucheService ternäreSucheService;

    @PostMapping("/userchoice")
    public Object handleUserChoice(@RequestBody UserChoiceRequest request) {

    String algorithm = request.getAlgorithm();
    System.out.println(algorithm);
    List<Integer> unsortedArray = request.getUnsortedArray();
    Integer target = request.getTarget();

    if (algorithm == null || algorithm.isEmpty()) {
        throw new IllegalArgumentException("Der Algorithmus muss angegeben werden.");
    }

   /*  if (algorithm.contains("Sort") && (unsortedArray == null || unsortedArray.isEmpty())) {
        throw new IllegalArgumentException("Die Eingabeliste für Sortieralgorithmen darf nicht leer sein.");
    }

    if (algorithm.contains("Suche") && target == null) {
        throw new IllegalArgumentException("Ein Zielwert muss für Suchalgorithmen angegeben werden.");
    } */

    switch (algorithm) {
        case "Bubble Sort":
            return bubbleSortService.generateBubbleSortSteps(unsortedArray);
        case "Insertion Sort":
            return insertionSortService.generateInsertionSortSteps(unsortedArray);
        case "Merge Sort":
            return mergeSortService.generateMergeSortSteps(unsortedArray);
        case "Quick Sort":
            return quickSortService.generateQuickSortSteps(unsortedArray);
        case "Lineare Suche":
            return Map.of(
                "initialArray", unsortedArray,
                "steps", lineareSucheService.generateLinearSearchSteps(unsortedArray, target)
            );
        case "Binäre Suche":
            return Map.of(
                "initialArray", unsortedArray,
                "steps", binäreSucheService.generateBinarySearchSteps(unsortedArray, target)
            );
        case "Ternäre Suche":
            return Map.of(
                "initialArray", unsortedArray,
                "steps", ternäreSucheService.generateTernarySearchSteps(unsortedArray, target)
            );
        default:
            throw new IllegalArgumentException("Unbekannter Algorithmus: " + algorithm);
        }
    }

    @GetMapping("/bubblesort")
    public List<List<Integer>> getBubbleSortSteps() {
        List<Integer> unsortedArray = List.of(8, 9, 1, 2, 3, 32, 3, 6, 67, 34, 12, 4, 25, 18, 10);
        return bubbleSortService.generateBubbleSortSteps(unsortedArray);
    }

    @GetMapping("/insertionsort")
    public List<List<Integer>> getInsertionSortSteps() {
        List<Integer> unsortedArray = List.of(8, 9, 1, 2, 3, 32, 3, 6, 67, 34, 12, 4, 25, 18, 10);
        return insertionSortService.generateInsertionSortSteps(unsortedArray);
    }

    @GetMapping("/mergesort")
    public List<List<Integer>> getMergeSortSteps() {
        List<Integer> unsortedArray = List.of(5, 2, 4, 8, 1, 9, 7, 3, 6);
        return mergeSortService.generateMergeSortSteps(unsortedArray);
    }

    @GetMapping("/quicksort")
    public List<List<Integer>> getQuickSortSteps() {
        List<Integer> unsortedArray = new ArrayList<>(List.of(8, 9, 1, 2, 3, 32, 3, 6, 67, 34, 12, 4, 25, 18, 10));
        return quickSortService.generateQuickSortSteps(unsortedArray);
    }

    @GetMapping("/linearsuche")
    public Map<String, Object> getLinearSucheSteps() {
        List<Integer> array = List.of(5, 1, 9, 3, 8, 12, 23, 4, 6, 2, 232, 7, 10);
        int target = 7;

        Map<String, Object> response = Map.of(
            "initialArray", array,
            "steps", lineareSucheService.generateLinearSearchSteps(array, target)
        );

        return response;

        /* {
        "initialArray": [5, 1, 9, 3, 8, 12, 23, 4, 6, 2, 232, 7, 10],
        "steps": [
            {
            "currentIndex": 0,
            "checkedElements": [5],
            "found": false
            },
            {
            "currentIndex": 1,
            "checkedElements": [5, 1],
            "found": false
            },
            ...
            {
            "currentIndex": 11,
            "checkedElements": [5, 1, 9, 3, 8, 12, 23, 4, 6, 2, 232, 7],
            "found": true
            }
        ]
        } */
    }

    @GetMapping("/binaeresuche")
    public Map<String, Object> getBinaereSucheSteps() {
        List<Integer> array = List.of(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 23, 52, 63, 72, 85, 91, 102, 115, 
        123, 135, 147, 158, 169, 174, 185, 192, 203, 215, 226, 237, 248, 259, 
        270, 281, 292, 303, 314, 325, 336, 347, 358, 369, 380, 391, 402, 413
        );
        int target = 391;
        Map<String, Object> response = Map.of(
            "initialArray", array,
            "steps", binäreSucheService.generateBinarySearchSteps(array, target)
        );
        return response;
    }

    @GetMapping("/ternaeresuche")
    public Map<String, Object> getTernaereSucheSteps() {
        List<Integer> array = List.of(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 23, 52, 63, 72, 85, 91, 102, 115, 
        123, 135, 147, 158, 169, 174, 185, 192, 203, 215, 226, 237, 248, 259, 
        270, 281, 292, 303, 314, 325, 336, 347, 358, 369, 380, 391, 402, 413
        );
        int target = 391;
        Map<String, Object> response = Map.of(
            "initialArray", array,
            "steps", ternäreSucheService.generateTernarySearchSteps(array, target)
        );
        return response;
    }
}