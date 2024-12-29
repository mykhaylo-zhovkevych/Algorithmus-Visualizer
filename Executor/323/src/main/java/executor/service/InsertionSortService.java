package executor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service
public class InsertionSortService {
    
    public List<List<Integer>> generateInsertionSortSteps(List<Integer> array) {

        // Funktionale Erstellung eines Streams über die Indizes (1 bis array.size())
        return IntStream.range(1, array.size()) 
                .boxed()
                .reduce(
                    List.of(new ArrayList<>(array)), // Funktionale Initialisierung der Liste mit dem Ausgangszustand
    
                    (steps, i) -> { // Lambda-Funktion: Akkumulator für jeden Index
    
                        // Imperative Kopie der bisherigen Schritte
                        List<List<Integer>> updatedSteps = new ArrayList<>(steps); 
                        // Kopiere den letzten Zustand der Liste
                        List<Integer> currentArray = new ArrayList<>(updatedSteps.get(updatedSteps.size() - 1));
    
                        // Speichere den aktuellen Wert (Schlüssel) und starte die Suche
                        int values = currentArray.get(i); // Nicht FP: Verwendung von Variablen statt direkter Transformationsketten
                        int j = i - 1; // damin die Elemente vor dem Values durchsucht werden können
    
                        // Verschiebe Elemente, die grösser als der Schlüssel sind
                        /* Solange j nicht negativ ist (j >= 0) und das aktuelle Element currentArray.get(j) grösser ist als der Schlüsselwert values, wird der Schleifenblock ausgeführt. */
                        while (j >= 0 && currentArray.get(j) > values) { // Nicht FP: Verwendung einer Schleife

                            // Teil01 hier wird die Elemnet verschoben nach rechts dank j + 1 und currentArray.get(j) ist aktuelle Element
                            currentArray.set(j + 1, currentArray.get(j)); // Nicht FP: Mutation des Arrays
                            // Reduziert den Vergleichsindex, damit das nächste Element links betrachtet wird.
                            j--; // Nicht FP: Veränderung einer Variablen
    
                            // Zwischen Zustand: Speichert den Zustand nach jeder Verschiebung
                            updatedSteps.add(new ArrayList<>(currentArray)); // Nicht FP: Kopieren und Hinzufügen zu einer Liste
                        }
    
                        // Teil02 Hier nach Teil01 befindet sich die kleinste Element an der Position j 
                        currentArray.set(j + 1, values); // Nicht FP: Mutation
                        updatedSteps.add(new ArrayList<>(currentArray)); // Nicht FP: Kopieren und Hinzufügen zu einer Liste
    
                        // Gebe die aktualisierten Schritte zurück
                        return updatedSteps; // Rückgabe eines neuen Zustands
                    },
    
                    (a, b) -> a // Funktionale Kombination der Teilergebnisse (hier irrelevant, da nur ein Ergebnis verarbeitet wird)
                );
    }
    
}
