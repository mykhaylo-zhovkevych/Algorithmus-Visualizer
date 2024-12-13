import { Pane } from 'https://cdn.skypack.dev/tweakpane@4.0.4';

const container = document.getElementById('page-container');
const config = {
    speed: 0.50,
    backdrop: true,
    theme: 'light',
};


// Container für die Panes
const paneContainer = document.createElement('div');
paneContainer.classList.add('pane-container');
document.body.appendChild(paneContainer);


const ctrl = new Pane({
    title: 'Config App',
    expanded: true,
});

// Erstelle das zweite Fenster
const secondWindow = new Pane({
    title: 'Config Algorithm',
    expanded: false,
});

// Erstelle das dritte Fenster
const thirdWindow = new Pane({
    title: 'Config ...',
    expanded: false,
});


// Füge die Pane-Elemente zum Container hinzu
paneContainer.appendChild(ctrl.element);
paneContainer.appendChild(secondWindow.element);
paneContainer.appendChild(thirdWindow.element);


const update = () => {
    document.documentElement.dataset.theme = config.theme
    document.documentElement.dataset.backdrop = config.backdrop
    document.documentElement.style.setProperty('--speed', config.speed)
    
};

const sync = (event) => {
    if (
        !document.startViewTransition ||
        event.target.controller.view.labelElement.innerText !== 'Theme'
    )
        return update();
    document.startViewTransition(() => update());
};

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


ctrl.on('change', sync);
update();

const pop = document.querySelector('[popover]');
if (pop) {
    pop.addEventListener('toggle', async (event) => {
        if (event.newState === 'open') {
            await Promise.allSettled(pop.getAnimations().map((a) => a.finished));
            pop.querySelector('[type=search]').focus();
        }
    });
}


// Event-Listener für Hash-Änderungen
window.addEventListener('hashchange', handleNavigation);
window.addEventListener('load', handleNavigation);

/* const themeToggler = document.querySelector('.theme-toggler');
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

// Dynamisches Laden externer HTML-Dateien mit Animation
function loadPageContent(page) {
  const tl = gsap.timeline();

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
        initArticleExpansion(); // Funktion zur Initialisierung der Expansion von Artikeln
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
        container.innerHTML = '<h2>404 - Seite nicht gefunden</h2>';
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
function scrollToCenter(article) {
  const container = document.querySelector(".main ul");
  
  if (container && article) {
    const containerWidth = container.offsetWidth;
    console.log(containerWidth);
    const articleLeft = article.offsetLeft;
    console.log(articleLeft);
    const articleWidth = article.offsetWidth;
    console.log(articleWidth);

    // Berechnung: Zentriere das Artikel-Element
    const scrollPosition = articleLeft - (containerWidth / 14 ) ;

    // Smooth Scrolling zur berechneten Position
    container.scrollTo({
      left: scrollPosition,
      behavior: "smooth",
    });
  }
}

// Initialisierung der Artikel-Interaktion
function initArticleExpansion() {
  const articles = document.querySelectorAll("article");

  articles.forEach((article) => {
    article.addEventListener("click", () => {
      // Vergrößern/Verkleinern des Artikels
      const isExpanded = article.style.height === "90vh";

      gsap.to(article, {
        duration: 0.5,
        height: isExpanded ? "clamp(200px, 50vmin, 400px)" : "90vh", // Toggle height
        width: isExpanded ? "clamp(300px, 50vmin, 600px)" : "90vw",  // Toggle width
        ease: "power2.in",
        onComplete: () => {
          // Scrollen, sobald die Animation abgeschlossen ist
          if (!isExpanded) scrollToCenter(article);
        },
      });
    });
  });
}


// Routing-Logik
function handleNavigation() {
  const hash = window.location.hash.slice(1); // Entferne das "#"
  const page = hash || 'home'; // Standard ist 'home'
  console.log(`Navigiere zu Seite: ${page}`); // Debug-Ausgabe
  loadPageContent(page);
}
