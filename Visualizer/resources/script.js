import { Pane } from 'https://cdn.skypack.dev/tweakpane@4.0.4';

// Die Root-Page holder 
const container = document.getElementById('page-container');

const config = {
    speed: 0.50,
    algoSpeed: 1.20,
    backdrop: true,
    theme: 'system',
};


const paneContainer = document.createElement('div');
paneContainer.classList.add('pane-container');
document.body.appendChild(paneContainer);

// Objketoretiert Paradigm
// Hier wird jede Pane repräsentiert eine separates Window
const ctrl = new Pane({
    title: 'Config App',
    expanded: true,
});

const secondWindow = new Pane({
    title: 'Config Algorithm',
    expanded: false,
});

/* const thirdWindow = new Pane({
    title: 'Config ...',
    expanded: false,
}); */

// Dynamische Hinzufügen von Elementen in den DOM 
paneContainer.appendChild(ctrl.element);
paneContainer.appendChild(secondWindow.element);
/* paneContainer.appendChild(thirdWindow.element); */

// FP reine Funktion, die den globalen Zustand der App synchronisiert
// Ziel von diese Funktion ist die User-App mit code config zu synchronisiert
const update = () => {
    document.documentElement.dataset.theme = config.theme
    document.documentElement.dataset.backdrop = config.backdrop
    document.documentElement.style.setProperty('--speed', config.speed);
    document.documentElement.style.setProperty('--algo-speed', config.algoSpeed);
    
};

// Event-gesteuerte Paradigma
// Kein Teil von FP, aber nutzte die Konzepte die: reihen Funktionen und Callbacks 
// Ziel ist, dass wenn User wählt andere Themen es wird updated
const sync = (event) => {
    if (
        !document.startViewTransition ||
        event.target.controller.view.labelElement.innerText !== 'Theme'
    )
        return update();
                                // Callback
  document.startViewTransition(() => update());
};

// Objektorientiertes Paradigma
// verbindet Properties des Objekts "config" mit UI Elementen 
ctrl.addBinding(config, 'speed', {
    label: 'Speed(s)',
    min: 0.2,
    max: 2,
    step: 0.01,
});

ctrl.addBinding(config, 'backdrop', {
    label: 'Backdrop',
});

ctrl.addBinding(config, 'theme', {
    label: 'Theme',
    options: {
        System: 'system',
        Light: 'light',
        Dark: 'dark',
    },
});

secondWindow.addBinding(config, 'algoSpeed', {
  label: 'Algorithm Speed (s)',
  min: 0.1,
  max: 2,
  step: 0.01,
});

// Fügt einen Button hinzu, um eine spezifische URL (mit Hash) zu laden
secondWindow.addButton({
  title: 'Reload Page',
}).on('click', () => {
  const targetHash = '#algorithms';
  if (window.location.hash !== targetHash) {
      window.location.hash = targetHash;
  } else {
      // Wenn der Hash bereits gesetzt ist, manuell das Event triggern
      window.dispatchEvent(new HashChangeEvent('hashchange'));
  }
});


// Sorgt dafür dass jede änderung zuert synchronisiert wird und dann updated
ctrl.on('change', sync);
update();

const pop = document.querySelector('[popover]');
if (pop) {
  // Fügt die pop zu toggle Event hinzu. Das toggle Event wird ausgelöst, wenn sich der Zustand des Popovers ändert 
    pop.addEventListener('toggle', async (event) => {
        if (event.newState === 'open') {
            await Promise.allSettled(pop.getAnimations().map((a) => a.finished));
            pop.querySelector('[type=search]').focus();
        }
    });
}

// Das ist ein Even-Listener für das Hashchange Event, der wird ausgelöst, wenn sich der URL (# Teil der URL) ändert.
window.addEventListener('hashchange', handleNavigation);
// Diese Event ist ausgelöst, wenn ganze page loaded
window.addEventListener('load', handleNavigation);

/* 
const themeToggler = document.querySelector('.theme-toggler');
themeToggler.addEventListener('click', () => {
    const options = ['system', 'light', 'dark'];
    const index = options.indexOf(config.theme);
    const newTheme = options.at(index + 1 > options.length - 1 ? 0 : index + 1);
    config.theme = newTheme;
    ctrl.refresh();
    sync({
        target: {
            controller: {
                view: {
                    labelElement: {
                        innerText: 'Theme',
                    },
                },
            },
        },
    });
});
*/

// FP Funktion zum Laden des Seiteninhalts indem sie eine Promise zurückgibt
// Die Funktion nutzt fetch-methode, um asynchron Daten zu holen und verarbeitet deise Daten, ohne den Zustand auuserhalb der Funktion zu verändern(bis auf das DOM, was als Seiteneffekt angesehen wird)
function loadPageContent(page) {
  const tl = gsap.timeline();

  // Animation für Transaktion zwischen Containers 
  tl.to(container, {
    duration: 0.8,
    opacity: 0,
    scale: 0.9,
    ease: 'power2.in',
  });

  tl.add(() => {
    fetch(`pages/${page}.html`)
      .then((response) => {
        if (!response.ok) {
          throw new Error(`Seite ${page} nicht gefunden.`);
        }
        return response.text();
      })
      .then((html) => {
        container.innerHTML = html;
        initArticleExpansion();
        gsap.fromTo(
          container,
          { opacity: 0, scale: 0.95, x: 50 },
          {
            duration: 0.8,
            opacity: 1,
            scale: 1,
            x: 0,
            ease: 'power2.out',
          }
        );
      })
      .catch(() => {
        container.innerHTML = '<h1>404 - Seite nicht gefunden</h1>';
        gsap.fromTo(
          container,
          { opacity: 0, scale: 0.95, y: -50 },
          {
            duration: 0.8,
            opacity: 1,
            scale: 1,
            y: 0,
            ease: 'power2.out',
          }
        );
        article.scrollIntoView({
          behavior: "smooth", 
        });
      });
  });
}

// Funktion zum Scrollen des Artikels in die Mitte des Containers
function scrollToCenter(article) {
  const container = document.querySelector(".main ul");
  
  if (container && article) {
    const containerWidth = container.offsetWidth;
    const articleLeft = article.offsetLeft;
    const articleWidth = article.offsetWidth;
    console.log(articleWidth);

    const scrollPosition = articleLeft - (containerWidth / 16 ) ;

    container.scrollTo({
      left: scrollPosition,
      behavior: "smooth",
    });
  }
}

// Array mit den URLs der Artikel
const articleURLs = [
  { id: 0, url: 'http://localhost:8080/api/userchoice' },
  { id: 1, url: 'http://localhost:8080/api/bubblesort' }, 
  { id: 2, url: 'http://localhost:8080/api/quicksort' },
  { id: 3, url: 'http://localhost:8080/api/mergesort' },
  { id: 4, url: 'http://localhost:8080/api/insertionsort' },
  { id: 5, url: 'http://localhost:8080/api/selectionsort' }
];

function initArticleExpansion() {
  const articles = document.querySelectorAll("article");

  // Füge einen Klick-Listener für jeden Artikel hinzu
  articles.forEach((article, index) => {
    article.addEventListener("click", async () => {
      const articleId = articleURLs[index]?.id; 
      //console.log(articleId);
      const url = articleURLs[index]?.url; 
      if (!url) {
        console.error(`Keine URL für Artikel ${index} gefunden`);
        return;
      }
      await handleArticleExpansion(article, url, articleId); 
    });
  });
}

async function handleArticleExpansion(article, url, articleId) {

  const isExpanded = article.style.height === "90vh";

  const isInputOrButtonClicked = event.target.closest('button, input, .circle'); 
  console.log(isInputOrButtonClicked);
  if (isInputOrButtonClicked) {
     // Beende die Funktion, wenn ein Button oder Input geklickt wurde
    return; 
  }

  if (articleId === 0) {
    gsap.to(article, {
      duration: 0.5,
      height: isExpanded ? "clamp(200px, 50vmin, 400px)" : "90vh",
      width: isExpanded ? "clamp(300px, 50vmin, 600px)" : "90vw",
      ease: "power2.in",
      onComplete: async () => {
        if (!isExpanded) {
          scrollToCenter(article);
          createInteractiveUI(article); 
        }
      }
    });
  } else {
    gsap.to(article, {
      duration: 0.5,
      height: isExpanded ? "clamp(200px, 50vmin, 400px)" : "90vh",
      width: isExpanded ? "clamp(300px, 50vmin, 600px)" : "90vw",
      ease: "power2.in",
      onComplete: async () => {
        if (!isExpanded) {
          scrollToCenter(article);
          await visualizer(article, url); 
        }
      }
    });
  }
}

// Funktion zum Senden der Benutzereingabe an den Server 
async function sendUserChoice(algorithm, array) {
  try {
    const response = await fetch('http://localhost:8080/api/userchoice', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        algorithm: algorithm,
        unsortedArray: array,
      }),
    });
  
    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Fehler: ${response.status} - ${errorText}`);
    }

    const data = await response.json();
    console.log('Daten erfolgreich gesendet:', data);
    return data;
  } catch (error) {
    console.error('Fehler beim Senden der Benutzerauswahl:', error);
    // Erneutes Werfen des Fehlers für die aufrufende Funktion
    throw error; 
  }
}

async function visualizeUserChoice(steps, article) {
  // Debugging statement
  // console.log('Visualizing sorting steps:', steps); 

  const interactiveUI = article.querySelector('.interactive-ui');
  if (!interactiveUI) {
    console.error('Interactive UI container not found.');
    return;
  }

  const existingContainer = interactiveUI.querySelector('.visualization-container');
  if (existingContainer) {
    existingContainer.remove();
  }

  const container = d3.select(interactiveUI)
    .append("div")
    .classed("visualization-container", true)
    .style("width", "100%")
    .style("height", "90%")
    .style("display", "flex")
    .style("align-items", "flex-end")
    .style("gap", "5px")
    .style("background-color", "#FFFFFF")
    .style("border-radius", "21px");

  // Debugging statement  
  // console.log('Visualization container created:', container.node()); 

  const bars = container.selectAll(".bar")
    .data(steps[0])
    .enter()
    .append("div")
    .classed("bar", true)
    .style("background-color", "#121212")
    .style("border", "1px solid black")
    .style("flex", "1")
    .style("height", d => `${(d / d3.max(steps[0])) * 90}%`);

  for (let i = 0; i < steps.length - 1; i++) {
    const currentStep = steps[i];
    const nextStep = steps[i + 1];

    const changedIndices = findChangedIndices(currentStep, nextStep);
    await highlightComparison(bars, changedIndices);
    updateBars(bars, nextStep);
    await delay(500);
  }
}

// Funktion zum Erstellen der interaktiven UI-Elemente
function createInteractiveUI(article) {
  if (!article) {
    console.error("Das Article-Element konnte nicht gefunden werden.");
    return;
  }

  // Verhindere doppelte UI-Erstellung
  if (article.querySelector(".interactive-ui")) {
    return; 
  }

  // Erstelle ein neues UI-Element und füge es dem Artikel hinzu
  const container = document.createElement("div");
  container.classList.add("interactive-ui");

  container.innerHTML = `
    <div class="container_circles">
      ${['Merge Sort', 'Quick Sort', 'Bubble Sort', 'Selection Sort', 'Quick Sort', 'Insertion Sort']
        .map((text, i) => `<div class="circle circle${i + 1}">${text}</div>`)
        .join('')}
    </div>
    <div class="preloader">
      <div></div>
    </div>
  `;

  article.appendChild(container); 

  const style = document.createElement("style");
  style.innerHTML = `
    :root {
        --bg: color-mix(in hsl, canvas 8%, canvasText);
        --bg02: color-mix(in hsl, canvas 45%, canvasText);
        --color: color-mix(in hsl, canvas 92%, canvasText);
    }

    .container_circles {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      height: 90%;
      background-color: #FFFFFF;
      border-radius: 21px;
    }

    .circle {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      display: flex;
      justify-content: center;
      align-items: center;
      color: white;
      font-size: 12px;
      overflow: hidden;
      cursor: pointer;
      text-align: center;
    }

    .interactive-ui {
          height: 100%;
    }

    .circle1 {
      background-color: #000000; /* Tiefschwarz */
      width: 200px;
      height: 200px;
      margin-top: -120px;
      margin-left: -120px;
    }

    .circle2 {
      background-color: #2a2a2a; /* Dunkles Grau */
      width: 170px;
      height: 170px;
      margin-top: -100px;
      margin-left: -100px;
    }

    .circle3 {
      background-color: #555555; /* Mittelgrau */
      width: 140px;
      height: 140px;
      margin-top: -80px;
      margin-left: -80px;
    }

    .circle4 {
      background-color: #808080; /* Neutrales Grau */
      width: 120px;
      height: 120px;
      margin-top: -65px;
      margin-left: -65px;
    }

    .circle5 {
      background-color: #aaaaaa; /* Helles Grau */
      width: 90px;
      height: 90px;
      margin-top: -50px;
      margin-left: -50px;
    }

    .circle6 {
      background-color: #d5d5d5; /* Sehr helles Grau */
      width: 80px;
      height: 80px;
      margin-top: -40px;
      margin-left: -40px;
    }

    .preloader {
      display: none;
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: rgba(0, 0, 0, 0.5);
      justify-content: center;
      align-items: center;
      z-index: 999;
    }

    .preloader div {
      width: 50px;
      height: 50px;
      border: 5px solid #fff;
      border-top: 5px solid #000000;
      border-radius: 50%;
      animation: spin 1s linear infinite;
    }

    @keyframes spin {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
    }

    .input-container input {
      padding: 5px !important;
      margin-right: 10px;
      border: 1px solid rgb(204, 204, 204) !important;
      border-radius: 5px !important;
      background: #ececec !important;
      color: var(--bg02) !important;
    }

    .input-container input::placeholder {
      color: var(--bg02) !important;
    }

    .input-container button {
      color: var(--bg02) !important;
      padding: 5px 10px !important;
      background-color: #ececec !important;
      border: 1px solid rgb(204, 204, 204) !important;
      border-radius: 5px !important;
      cursor: pointer !important;
    }

    .input-container {
      margin-top: 100px !important;
    }
  `;

  document.head.appendChild(style);
  const circles = document.querySelectorAll('.circle');
  const containerCircles = document.querySelector('.container_circles');
  const preloader = document.querySelector('.preloader');

  circles.forEach(circle => {
    circle.addEventListener('click', () => {
      // Debugging statement
      // console.log('Circle clicked:', circle); 
      document.querySelectorAll('.circle').forEach(c => c.classList.remove('selected'));
      circle.classList.add('selected');
      // Debugging statement
      // console.log('Selected class added:', circle.classList.contains('selected')); 

      if (!document.querySelector('.input-container')) {
        const inputContainer = document.createElement('div');
        inputContainer.className = 'input-container';
        inputContainer.style.marginTop = '50px';
        inputContainer.style.textAlign = 'center';
        inputContainer.style.opacity = '0';
        inputContainer.style.transform = 'translateY(-20px)';
        inputContainer.style.transition = 'opacity 0.5s ease, transform 0.5s ease';

        const inputField = document.createElement('input');
        inputField.type = 'text';
        inputField.placeholder = 'z.B: 12, 32, 43, 7, 93';

        const sendButton = document.createElement('button');
        sendButton.textContent = 'Send';
        sendButton.style.padding = '10px 20px';
        sendButton.style.backgroundColor = '#3498db';
        sendButton.style.color = '#fff';
        sendButton.style.border = 'none';
        sendButton.style.borderRadius = '5px';
        sendButton.style.cursor = 'pointer';

        sendButton.addEventListener('mouseenter', () => {
          sendButton.style.backgroundColor = '#2c7bbd';
        });

        sendButton.addEventListener('mouseleave', () => {
          sendButton.style.backgroundColor = '#3498db';
        });

        inputContainer.appendChild(inputField);
        inputContainer.appendChild(sendButton);
        containerCircles.appendChild(inputContainer);

        requestAnimationFrame(() => {
          inputContainer.style.opacity = '1';
          inputContainer.style.transform = 'translateY(0)';
        });

        sendButton.addEventListener('click', async () => {
          preloader.style.display = 'flex';
          containerCircles.style.display = 'none';
      
            const selectedCircle = document.querySelector('.circle.selected');
            if (!selectedCircle) {
              alert('Bitte wählen Sie einen Algorithmus aus.');
              preloader.style.display = 'none';
              containerCircles.style.display = 'flex';
              return;
            }
            const algorithm = selectedCircle.textContent;
            // Debugging statement
            // console.log('Selected algorithm:', algorithm); 

            const arrayInput = inputField.value;
            const array = arrayInput
              .split(',')
              .map(Number)
              .filter(num => !isNaN(num));
              // Debugging statement
              // console.log(array);

            if (array.length === 0) {
              alert('Bitte ein gültiges Array eingeben.');
              preloader.style.display = 'none';
              containerCircles.style.display = 'flex';
              return;
            }

            const steps = await sendUserChoice(algorithm, array);
            // Debugging statement	
            // console.log('Steps:', steps); 

            preloader.style.display = 'none';

            if (steps) {
              visualizeUserChoice(steps, article);
            }
        
        });
      }
    });
  });

  // Initialisiere die Animationen für die Kreise
  gsap.set(circles, { scale: 0, opacity: 0 });

  // Einführende Animation
  gsap.timeline()
    .to(circles, {
      scale: 1,
      opacity: 1,
      duration: 1,
      stagger: 0.2,
      ease: 'power1.inOut',
    });

  circles.forEach(circle => {
    // Hover-Animationen
    circle.addEventListener('mouseenter', () => {
      gsap.to(circle, { 
        scale: 1.5, 
        duration: 0.3, 
        ease: 'power2.out',
        // Verhindert Konflikte
        overwrite: true 
      });
    });

    circle.addEventListener('mouseleave', () => {
      gsap.to(circle, { 
        scale: 1, 
        duration: 0.3, 
        ease: 'power2.out',
        // Verhindert Konflikte
        overwrite: true 
      });
    });

    // Klick-Animationen
    circle.addEventListener('click', () => {
      gsap.to(circles, {
        x: () => Math.random() * 100 - 50,
        y: () => Math.random() * 100 - 50,
        scale: () => Math.random() * 1 + 0.5,
        duration: 1,
        ease: 'elastic.out(1, 0.5)',
      });

      // Rückkehr zur Ausgangsposition nach einer kurzen Verzögerung
      gsap.to(circles, {
        x: 0,
        y: 0,
        scale: 1,
        duration: 1,
        ease: 'power3.inOut',
        // Zeit bis zur Rückkehr
        delay: 1.5 
      });
    });
  });
}

async function visualizer(article, url) {
    // Verhindere doppelte UI-Erstellung
    if (article.querySelector(".visualization-container")) return;
  
    const container = d3.select(article)
      .append("div")
      .classed("visualization-container", true)
      .style("width", "100%")
      .style("height", "90%")
      .style("display", "flex")
      .style("align-items", "flex-end")
      .style("gap", "5px")
      .style("background-color", "#FFFFFF")
      .style("border-radius", "21px");
  
    const data = await fetchSortData(url); 
    if (!data) return;
  
    const steps = data;
  
    const bars = container.selectAll(".bar")
      // Bindet die Daten des ersten Sortierschritts (steps[0]) an diese Elemente
      .data(steps[0])
      // Erstellt neue div-Elemente für jeden Datenpunkt
      .enter()
      .append("div")
      .classed("bar", true)
      .style("background-color", "#121212")
      .style("border", "1px solid black")
      .style("flex", "1")
      .style("height", d => `${(d / d3.max(steps[0])) * 90}%`);
  
    for (let i = 0; i < steps.length - 1; i++) {
      const currentStep = steps[i];
      const nextStep = steps[i + 1];
  
      const changedIndices = findChangedIndices(currentStep, nextStep);
      await highlightComparison(bars, changedIndices);
      updateBars(bars, nextStep);
      await delay(500);
    }
  }

  // Hilfsfunktionen
  function findChangedIndices(currentStep, nextStep) {
    const indices = [];
    for (let i = 0; i < currentStep.length; i++) {
      if (currentStep[i] !== nextStep[i]) {
        indices.push(i);
      }
    }
    return indices;
  }

  async function highlightComparison(bars, indices) {
    return new Promise(resolve => {
      bars.style("background-color", (d, i) => {
        if (indices.includes(i)) return "#ececec"; 
        return "#121212";
      });
      setTimeout(resolve, 500); 
    });
  }

  function updateBars(bars, values) {
    bars.data(values)
      .style("height", d => `${(d / d3.max(values)) * 90}%`)
      .style("background-color", "#121212"); 
  }

  const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms * config.algoSpeed));


  async function fetchSortData(url) {
    try {
      const response = await fetch(url);
      if (!response.ok) throw new Error('Fehler beim Abrufen der Daten');
      const result = await response.json();
      return result;
    } catch (err) {
      console.error('Fehler:', err);
      return null;
    }
  }

function handleNavigation() {
  const hash = window.location.hash.slice(1); 
  const page = hash || 'home';
  console.log(`Navigiere zu Seite: ${page}`);
  loadPageContent(page);
}