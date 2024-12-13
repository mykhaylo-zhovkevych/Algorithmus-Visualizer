# Dokumentation

Eine allgemeine Dokumentation für die App, die API und die Installationsanleitung.

## **Überblick**

Dieses Projekt besteht aus zwei Hauptkomponenten:  
1. **Benutzeroberfläche und Desktop-Anwendung:**  
   - Entwickelt mit JavaScript und Electron.
   - Zuständig für die Visualisierung und Interaktion der Benutzer mit der Anwendung.

2. **Backend und Algorithmus-Ausführung:**  
   - Implementiert in Java Stream für die algorithmische Verarbeitung.
   - Unterstützt Parallelisierung, indem zwei Algorithmen gleichzeitig ausgeführt werden können, um deren Ergebnisse direkt zu vergleichen.

### Ziel des Projekts:  
Das Projekt dient der Visualisierung von Algorithmen. Es konzentriert sich auf zwei Hauptkategorien:  
1. **Sortieralgorithmen**  
2. **Suchalgorithmen**  

Für jede Kategorie werden zwei Algorithmen implementiert. Das System ist jedoch flexibel, sodass spezifische Algorithmen in Zukunft hinzugefügt werden können.  

### Besonderheiten:  
- Vergleichende Visualisierung: Zwei Algorithmen können parallel gerendert werden, um Unterschiede und Leistung zu analysieren.
- Erweiterbarkeit: Die Architektur ist darauf ausgelegt, neue Algorithmen nahtlos zu integrieren.

---

## **Herausforderungen**

Während der Entwicklung sind folgende Herausforderungen zu berücksichtigen:  
- Wie erstelle ich die Architektur der App?

  ` Backend (Java):
   Führt die Berechnung aus (z. B. Sortieralgorithmen Schritt-für-Schritt).
   Speichert alle Schritte als JSON-Daten.
   Stellt die Daten über einen REST-Endpunkt bereit.`
  
  ` Frontend (JavaScript + D3.js):
   Fragt die Daten vom Backend ab.
   Visualisiert die Schritte mithilfe von D3.js 
   Steuert die Benutzerinteraktionen (Play, Pause, Schritt-für-Schritt-Animation).`
  
- Wie müssen Daten zwischen Backend und Fronted rendert werden?

  ` Das Backend ist verantwortlich für die Datenverarbeitung: 1. Algorithmus ausführen 2. REST-Endpunkt 3. Komprimierung der JSON-Daten 4. JSON-Daten Versendung. `
 
  ` Das Frontend ist für die Darstellung und Visualisierung zuständig: 1. JSON-Daten abrufen 2. Schritt-für-Schritt-Visualisierung mit D3.js erstellen 3. Steuerungsoberfläche einbauen. `
  

- Wie erledigt man Optimierung der Performance bei der parallelen Algorithmus-Ausführung?  
- Wie soll die Synchronisation und Visualisierung von einem und zwei Algorithmen in Echtzeit erfolgen? 

---

## **Projektidee**

Das Projekt entstand aus dem Wunsch, algorithmische Konzepte auf eine intuitive und visuelle Weise darzustellen. 
Die interaktive App ermöglicht es den Nutzern, die Funktionsweise von Algorithmen besser zu verstehen und sie direkt zu vergleichen.

---

## **Installationsanleitung**
