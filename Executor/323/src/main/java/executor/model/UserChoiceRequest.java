package executor.model;

import java.util.List;

public class UserChoiceRequest  {

    private String algorithm;
    private List<Integer> unsortedArray;
    private Integer target;
    
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

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }
}