const { app, BrowserWindow } = require('electron');
const path = require('path');

// Function to create the main application window
function createWindow() {
  const win = new BrowserWindow({
    width: 1920,
    height: 1080,
    minWidth: 1280,  
    minHeight: 720,  
    frame: true,
    autoHideMenuBar: true, // Hide the system menu bar
    webPreferences: {
      nodeIntegration: true
    }
  });

  // Load the HTML file from the resources folder
  win.loadFile(path.join(__dirname, 'resources', 'index.html'));

  // Optionally, you can also add a listener to enforce min-size on window resize
  win.on('resize', () => {
    const [width, height] = win.getSize();
    if (width < 1280 || height < 720) {
      win.setSize(1280, 720);
    }
  });
}

// When the Electron app is ready, create the window
app.whenReady().then(createWindow);

// Close the app when all windows are closed (important for macOS)
app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

// Recreate the window when clicking on the app icon (macOS)
app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) {
    createWindow();
  }
});
