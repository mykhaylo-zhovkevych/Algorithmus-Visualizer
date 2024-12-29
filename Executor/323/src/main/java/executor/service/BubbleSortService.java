package executor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service
public class BubbleSortService {

    public List<List<Integer>> generateBubbleSortSteps(List<Integer> array) {
        // Funktionaler Bubble-Sort: Ziel ist es, alle Zustände des Sortierprozesses zu speichern, 
        // ohne die ursprüngliche Eingabeliste zu verändern.
    
        // Die äussere Schleife läuft so oft, wie wir benötigen, um die Liste vollständig zu sortieren
        // Beispiel: Für eine Liste mit 5 Zahlen läuft sie 4-mal
        // System.out.println("array.size(): " + array.size());
        return IntStream.range(0, array.size() - 1)
                // Wandelt die IntStream-Werte (primitive int) in Objekte vom Typ Integer um
                // Das ist notwendig, da die reduce-Methode Objekte wie Stream<Integer> erwartet und nicht mit primitiven Typen arbeiten kann
                .boxed() 
                // Die reduce-Methode führt eine schrittweise Verarbeitung durch, bei der ein Startwert definiert wird und nach und nach neue Ergebnisse erzeugt werden
                .reduce(
                    // Der Startwert ist ein Wert, mit dem der Reduktionsprozess beginnt
                    List.of(new ArrayList<>(array)), 
                    
                    // Die Akkumulationsfunktion wird bei jedem Element des Streams aufgerufen und verarbeitet das akkumulierte Ergebnis (also den bisherigen Zustand) und das aktuelle Element des Streams
                    (steps, i) -> { 
                        // `steps` enthält bisherige Zustände der Liste (eine Liste von Listen)
                        // `i` ist der aktuelle Index der äusseren Schleife, z.b: in der ersten Runde ist i = 0, in der nächsten i = 1 usw
    
                        // Hier wird eine Kopie der steps-Liste erstellt, um die Unveränderlichkeit der ursprünglichen Liste zu wahren
                        List<List<Integer>> updatedSteps = new ArrayList<>(steps);
                        // updatedSteps enthält die Liste der bisherigen Zustände, und es wird die neuen Zustände nach den Tauschoperationen hinzufügen
    
                        // Die innere Schleife prüft jedes benachbarte Paar von Elementen und führt einen Tausch durch, wenn das erste Element grösser als das zweite ist
                        IntStream.range(0, array.size() - i - 1) 
                                .forEach(j -> {
                                    // Der letzte Zustand der Liste
                                    // updatedSteps.size() - 1 gibt den Index des letzten Elements zurück
                                    // holt die Liste (den Zustand), die zuletzt hinzugefügt wurde.                
                                    List<Integer> currentArray = updatedSteps.get(updatedSteps.size() - 1);
                                    // System.out.println("currentArray: " + currentArray);
    
                                    // Prüfe, ob die aktuellen Elemente getauscht werden müssen
                                    if (currentArray.get(j) > currentArray.get(j + 1)) {
                                        // Erstelle eine neue Liste mit getauschten Elementen.
                                        List<Integer> swapped = swapElements(currentArray, j, j + 1);
    
                                        // Speichere die neue Liste als nächsten Zustand.
                                        updatedSteps.add(swapped);
                                    }
                                });
    
                        // Gib die aktualisierte Liste der Zustände zurück.
                        return updatedSteps;
                    },
    
                    // Kombinationsfunktion: Wird benötigt, wenn der Stream parallel ist (hier nicht der Fall)
                    (a, b) -> a // Kombiniere die Teilergebnisse (behält einfach das erste Ergebnis `a` bei)
                );
    }
    
    // Hilfsfunktion für das Tauschen von Elementen in einer Liste (unveränderlich)
    private List<Integer> swapElements(List<Integer> list, int i, int j) {
        // Erstelle eine Kopie der ursprünglichen Liste, um die Unveränderlichkeit zu gewährleisten
        List<Integer> swapped = new ArrayList<>(list);
    
        // Tausche die Elemente an den Indizes `i` und `j`
        Collections.swap(swapped, i, j);
    
        // Gib die neue Liste mit den getauschten Elementen zurück
        return swapped;
    }
}