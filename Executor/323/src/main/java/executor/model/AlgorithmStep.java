package executor.model;

import java.util.List;

public class AlgorithmStep {
    private List<Integer> values;

    public AlgorithmStep(List<Integer> values) {
        this.values = values;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }
}