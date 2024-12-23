package executor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import executor.model.UserChoiceRequest;
import executor.service.BubbleSortService;

@RestController
@RequestMapping("/api")
public class AlgorithmSortController {

    @Autowired
    private BubbleSortService bubbleSortService;

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

    // selection sort

    // merge sort, optional quick sort
}