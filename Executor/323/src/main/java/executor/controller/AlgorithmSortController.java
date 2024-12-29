package executor.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import executor.model.UserChoiceRequest;
import executor.service.BubbleSortService;
import executor.service.InsertionSortService;
import executor.service.LineareSucheService;
import executor.service.MergeSortService;
import executor.service.QuickSortService;


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

    @PostMapping("/userchoice")
    public List<List<Integer>> handleUserChoice(@RequestBody UserChoiceRequest request) {
    // Debugging statement
    // System.out.println("Received request: " + request); 

        String algorithm = request.getAlgorithm();
        List<Integer> unsortedArray = request.getUnsortedArray();

        if (unsortedArray == null || unsortedArray.isEmpty()) {
            throw new IllegalArgumentException("Die Eingabeliste darf nicht leer sein.");
        }

        switch (algorithm) {
            case "Bubble Sort":
                return bubbleSortService.generateBubbleSortSteps(unsortedArray);
      
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
        List<Integer> unsortedArray = List.of(8, 9, 1, 2, 3, 32, 3, 6, 67, 34, 12, 4, 25, 18, 10);
        return mergeSortService.mergeSortWithSteps(unsortedArray);
    }

    @GetMapping("/quicksort")
    public List<List<Integer>> getQuickSortSteps() {
        List<Integer> unsortedArray = List.of(8, 9, 1, 2, 3, 32, 3, 6, 67, 34, 12, 4, 25, 18, 10);
        return quickSortService.generateQuickSortSteps(unsortedArray);
    }

    @GetMapping("/linearsuche")
    public Map<String, Object> getLinearSucheSteps() {
        // Lokale Array und Zielwert
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
}