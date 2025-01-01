package executor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class BinäreSucheService {

    /**
     * Führt eine binäre Suche rekursiv durch und protokolliert jeden Schritt
     *
     * @param sortedArray Die sortierte Liste, die durchsucht wird
     * @param target      Das gesuchte Ziel
     * @return Eine Liste von Maps, die die Schritte der Suche darstellen
     */
    public List<Map<String, Object>> generateBinarySearchSteps(List<Integer> sortedArray, int target) {
        return binarySearch(sortedArray, target, 0, sortedArray.size() - 1, new ArrayList<>());
    }

    private List<Map<String, Object>> binarySearch(
            List<Integer> sortedArray, int target, int left, int right, List<Map<String, Object>> steps) {

        // Basisfall: Der Basisfall für die Rekursion: Wenn left grösser als right ist, bedeutet dies, dass der Suchbereich ungültig ist (d.h., das Ziel wurde nicht gefunden), und die Methode gibt die Liste der bisherigen Schritte zurück
        if (left > right) {
            return steps;
        }

        // Berechnet den mittleren Index
        int mid = left + (right - left) / 2;
        boolean found = sortedArray.get(mid) == target;

        // Protokolliert den aktuellen Schritt und fügt ihn der Liste der Schritte hinzu
        steps.add(Map.of(
                "left", left,
                "right", right,
                "mid", mid,
                "checkedElement", sortedArray.get(mid),
                "found", found
        ));

        // Wenn das Element gefunden wurde, wird der Rekursionsprozess beendet
        if (found) {
            return steps;
        }

        // Rekursiver Aufruf: Je nach Wert des mittleren Elements, suchen wir links oder rechts weiter
        if (sortedArray.get(mid) < target) {
            return binarySearch(sortedArray, target, mid + 1, right, steps); // Suche im rechten Teil
        } else {
            return binarySearch(sortedArray, target, left, mid - 1, steps); // Suche im linken Teil
        }
    }
}
