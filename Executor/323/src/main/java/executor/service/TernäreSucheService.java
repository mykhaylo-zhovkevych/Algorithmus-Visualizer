package executor.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class TernäreSucheService {

    /**
     * Führt eine ternäre Suche durch und protokolliert jeden Schritt im funktionalen Stil
     *
     * @param sortedArray Die sortierte Liste, die durchsucht wird
     * @param target      Das gesuchte Ziel
     * @return Eine Liste von Maps, die die Schritte der Suche darstellen
     */
    public List<Map<String, Object>> generateTernarySearchSteps(List<Integer> sortedArray, int target) {
        return ternarySearch(sortedArray, target, 0, sortedArray.size() - 1);
    }

    private List<Map<String, Object>> ternarySearch(
        List<Integer> sortedArray,
        int target,
        int left,
        int right) {

    // Basisfall: Wenn die Grenzen ungültig sind (left > right), gibt es keine weiteren Schritte deswegen eine leere Liste zurück
    if (left > right) {
        return List.of(); // Leere Liste
    }

    // mid1 befindet sich 1/3 der Strecke von left zu right
    int mid1 = left + (right - left) / 3;
    // mid2 befindet sich 2/3 der Strecke von left zu right
    int mid2 = right - (right - left) / 3;

    boolean foundMid1 = sortedArray.get(mid1) == target;
    boolean foundMid2 = sortedArray.get(mid2) == target;

    // Protokollierung des aktuellen Schritts
    Map<String, Object> step = Map.of(
        "left", left,
        "right", right,
        "mid1", mid1,
        "mid2", mid2,
        "checkedElementMid1", sortedArray.get(mid1),
        "checkedElementMid2", sortedArray.get(mid2),
        "found", foundMid1 || foundMid2,
        "target", target
    );

    // Wenn das Ziel gefunden wurde, beende die Rekursion mit den bisherigen Schritten
    if (foundMid1 || foundMid2) {
        return List.of(step);
    }

    // Rekursiver Aufruf: Bestimme den nächsten Bereich basierend auf dem Zielwert
    if (target < sortedArray.get(mid1)) {
        // Ziel liegt im linken Drittel
        // Diese Methode wird verwendet, um den aktuellen Schritt mit den Ergebnissen der nächsten Rekursion zu kombinieren
        return Stream.concat(
            Stream.of(step),
            ternarySearch(sortedArray, target, left, mid1 - 1).stream()
        ).toList();
    } else if (target > sortedArray.get(mid2)) {
        // Ziel liegt im rechten Drittel
        return Stream.concat(
            Stream.of(step),
            ternarySearch(sortedArray, target, mid2 + 1, right).stream()
        ).toList();
    } else {
        // Ziel liegt im mittleren Drittel
        return Stream.concat(
            Stream.of(step),
            ternarySearch(sortedArray, target, mid1 + 1, mid2 - 1).stream()
        ).toList();
    }
  }
}