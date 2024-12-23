package executor.model;

import java.util.List;

public class UserChoiceRequest  {

    private String algorithm;
    private List<Integer> unsortedArray;

    /* public AlgorithmStep(List<Integer> unsortedArray) {
        this.unsortedArray = unsortedArray;
    } */

    // Getter und Setter
    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public List<Integer> getUnsortedArray() {
        return unsortedArray;
    }

    public void setUnsortedArray(List<Integer> unsortedArray) {
        this.unsortedArray = unsortedArray;
    }
}