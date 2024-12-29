package executor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class LineareSucheService {

    /**
     * Führt eine lineare Suche durch und protokolliert jeden Schritt.
     *
     * @param array  Die Liste, die durchsucht wird.
     * @param target Das gesuchte Ziel.
     * @return Eine Liste von Maps, die die Schritte der Suche darstellen.
     */
    public List<Map<String, Object>> generateLinearSearchSteps(List<Integer> array, int target) {
        List<Map<String, Object>> steps = new ArrayList<>();
        boolean found = false;

        for (int i = 0; i < array.size(); i++) {
            Map<String, Object> step = new HashMap<>();
            List<Integer> checkedElements = new ArrayList<>(array.subList(0, i + 1));

            step.put("currentIndex", i);
            step.put("checkedElements", checkedElements);
            step.put("found", array.get(i) == target);

            steps.add(step);

            if (array.get(i) == target) {
                found = true;
                break; // Ziel gefunden, Suche beenden
            }
        }

        // Falls Ziel nicht gefunden wurde, abschliessenden Schritt hinzufügen
        if (!found) {
            Map<String, Object> finalStep = new HashMap<>();
            finalStep.put("currentIndex", -1);
            finalStep.put("checkedElements", array);
            finalStep.put("found", false);
            steps.add(finalStep);
        }

        return steps;
    }
}