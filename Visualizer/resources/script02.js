const pages = {
    home: `
        <h2>Willkommen auf der Startseite</h2>
        <p>Dies ist der Hauptbereich der Webseite.</p>
    `,
    about: `
        <h2>Über uns</h2>
        <p>Hier erfahren Sie mehr über unser Unternehmen.</p>
    `,
    contact: `
        <h2>Kontakt</h2>
        <p>Kontaktieren Sie uns unter: <a href="mailto:info@example.com">info@example.com</a></p>
    `
};

function renderPage() {
    const hash = location.hash.slice(1);
    const container = document.getElementById('page-container');

    // Entferne die "active" Klasse für Animation
    container.classList.remove('active');

    // Lade den Inhalt mit einer kleinen Verzögerung für die Animation
    setTimeout(() => {
        container.innerHTML = pages[hash] || '<h2>404 - Seite nicht gefunden</h2>';
        container.classList.add('active');
    }, 500);
}

// Event Listener für Hash-Änderungen
window.addEventListener('hashchange', renderPage);
window.addEventListener('load', () => {
    if (!location.hash) {
        location.hash = '#home'; // Standardseite
    }
    renderPage();
});
