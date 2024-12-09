import { Pane } from 'https://cdn.skypack.dev/tweakpane@4.0.4';

const popover = document.querySelector('[popover]')

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
pop.addEventListener('toggle', async (event) => {
    if (event.newState === 'open') {
        await Promise.allSettled(pop.getAnimations().map((a) => a.finished));
        pop.querySelector('[type=search]').focus();
    }
});


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
