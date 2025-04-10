# Dokumentation

![Screenshot 2025-01-06 111636](https://github.com/user-attachments/assets/08e97c84-f8e1-4888-ab7e-1388683c829c)
![Screenshot 2025-01-06 111653](https://github.com/user-attachments/assets/d397e02e-00ea-4622-abb2-ff2b33ea12eb)
![Screenshot 2025-01-06 111727](https://github.com/user-attachments/assets/5e46c0c0-b934-44f2-81e5-871c226aac0a)
![Screenshot 2025-01-06 111741](https://github.com/user-attachments/assets/90b8a0df-3d8f-43a9-8baf-7bbeda97b97c)
![Screenshot 2025-01-06 111754](https://github.com/user-attachments/assets/487648ea-9640-4485-b483-d585a3b717fa)![Screenshot 2025-01-06 111805](https://github.com/user-attachments/assets/c697cc71-8743-4fc2-a5c1-baaea832ddc4)





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
