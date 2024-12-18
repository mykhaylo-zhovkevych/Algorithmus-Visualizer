package executor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import executor.service.BubbleSortService;

@RestController
@RequestMapping("/api")
public class AlgorithmSortController {

    @Autowired
    private BubbleSortService bubbleSortService;

    @GetMapping("/bubblesort")
    public List<List<Integer>> getBubbleSortSteps() {
   
        List<Integer> unsortedArray = List.of(8, 9, 1, 2, 3, 32, 3, 6, 67, 34, 12, 4, 25, 18, 10);

        return bubbleSortService.generateBubbleSortSteps(unsortedArray);
    }

    // selection sort


    // merge sort, optional quick sort 

}