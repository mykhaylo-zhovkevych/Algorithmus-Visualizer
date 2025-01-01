package executor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service
public class LineareSucheService {

    /**
     * Führt eine lineare Suche durch und protokolliert jeden Schritt
     *
     * @param array  Die Liste, die durchsucht wird
     * @param target Das gesuchte Ziel
     * @return Eine Liste von Maps, die die Schritte der Suche darstellen
     */
    public List<Map<String, Object>> generateLinearSearchSteps(List<Integer> array, int target) {
        // Erstelle eine Liste, um die Schritte zu sammeln
        List<Map<String, Object>> steps = new ArrayList<>();
    
        // Dies ist ein funktionaler Ansatz, da der Stream die Verarbeitung der Daten kapselt, anstatt sie in einer Schleife zu bearbeiten
        IntStream.range(0, array.size())
        /* 
        mapToObj(i -> {...}) mappt den Index i jedes Elements des Streams zu einem Map<String, Object>
        Diese Map enthält Informationen über den aktuellen Index, die bereits überprüften Elemente und ob das Ziel (target) gefunden wurde
        */
            .mapToObj(i -> {
                // Prüfen, ob das aktuelle Element das Ziel ist
                boolean found = array.get(i) == target; 
    
                Map<String, Object> step = Map.of(
                    "currentIndex", i,
                    "checkedElements", new ArrayList<>(array.subList(0, i + 1)),
                    "found", found
                );
                System.out.println("step: " + step);
                return step;
            })
            /* Die Verwendung von steps.add(step) innerhalb von takeWhile ist ein imperativer Ansatz, da steps (eine externe Liste) in einer mutierenden Weise verändert wird
            In funktionaler Programmierung sollten Daten nicht durch Seiteneffekte (wie das Hinzufügen zu einer Liste) verändert werden */
            .takeWhile(step -> {
                boolean isFound = (boolean) step.get("found");
                steps.add(step); 
                return !isFound; 
            })
            .count();
    
        // Gibt die Liste mit allen Schritten zurück
        return steps; 
    }
}