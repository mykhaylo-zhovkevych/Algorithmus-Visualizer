import { Pane } from 'https://cdn.skypack.dev/tweakpane@4.0.4';


const container = document.getElementById('page-container');
const config = {
    speed: 0.50,
    backdrop: true,
    theme: 'system',
};


const paneContainer = document.createElement('div');
paneContainer.classList.add('pane-container');
document.body.appendChild(paneContainer);


const ctrl = new Pane({
    title: 'Config App',
    expanded: true,
});

const secondWindow = new Pane({
    title: 'Config Algorithm',
    expanded: false,
});

const thirdWindow = new Pane({
    title: 'Config ...',
    expanded: false,
});


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
        container.innerHTML = '<p>404 - Seite nicht gefunden</p>';
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


function createInteractiveUI(article) {
  if (!article) {
    console.error("Das Article-Element konnte nicht gefunden werden.");
    return;
  }


  if (article.querySelector(".interactive-ui")) {
    return; 
  }


  const container = document.createElement("div");
  container.classList.add("interactive-ui");

  container.innerHTML = `
    <div class="container_circles">
    <div class="circle circle1">Circle 1</div>
    <div class="circle circle2">Circle 2</div>
    <div class="circle circle3">Circle 3</div>

  </div>

  <div class="preloader">
    <div></div>
  </div>
  `;

  article.appendChild(container); 


  const style = document.createElement("style");
  style.innerHTML = `
    .container_circles {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      gap: 20px;
      height: 90vh;
      background-color: #ececec;
      border-radius: 21px;
    }

    .circle {
      width: 50px;
      height: 50px;
      background-color: #3498db;
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

    .circle1 {
      background-color: #7752d5;
      width: 240px;
      height: 240px;
      margin-top: -120px;
      margin-left: -120px;
    }
    
    .circle2 {
      background-color: #8362d9;
      width: 170px;
      height: 170px;
      margin-top: -85px;
      margin-left: -85px;
    }
    
    .circle3 {
      background-color: #9f88d6;
      width: 100px;
      height: 100px;
      margin-top: -50px;
      margin-left: -50px;
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
  `;
  document.head.appendChild(style);
  const circles = document.querySelectorAll('.circle');
  const containerCircles = document.querySelector('.container_circles');
  const preloader = document.querySelector('.preloader');

  circles.forEach(circle => {
    circle.addEventListener('click', () => {
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
        inputField.placeholder = 'Enter your data';
        inputField.style.padding = '10px';
        inputField.style.marginRight = '10px';
        inputField.style.border = '1px solid #ccc';
        inputField.style.borderRadius = '5px';

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

        sendButton.addEventListener('click', () => {
          preloader.style.display = 'flex';
          setTimeout(() => {
            alert(`Data sent: ${inputField.value}`);
            preloader.style.display = 'none';
            inputField.value = '';
          }, 2000);
        });

      }
    });
  });


gsap.set(circles, { scale: 0 });
gsap.timeline()
 .to(circles, {
   scale: 1,
   opacity: 1,
   duration: 1,
   stagger: 0.2,
   ease: 'power1.inOut',
 });


circles.forEach(circle => {
 circle.addEventListener('mouseenter', () => {
   gsap.to(circle, { scale: 1.5, duration: 0.3 });
 });
 circle.addEventListener('mouseleave', () => {
   gsap.to(circle, { scale: 1, duration: 0.3 });
 });
});


circles.forEach(circle => {
 circle.addEventListener('click', () => {
   gsap.to(circles, {
     x: () => Math.random() * 100 - 50,
     y: () => Math.random() * 100 - 50,
     scale: () => Math.random() * 1 + 0.5,
     duration: 1,
     ease: 'elastic.out(1, 0.5)',
   });
 });
});      
}



  


  async function visualizer(article, url) {
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


  function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }


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
