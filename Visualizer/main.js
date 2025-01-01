const { app, BrowserWindow } = require('electron');
const path = require('path');
const { spawn } = require('child_process');

let backendProcess; // Variable für den Backend-Prozess

function createWindow() {
  const win = new BrowserWindow({
    width: 1920,
    height: 1080,
    minWidth: 1920,
    minHeight: 1080,
    frame: true,
    autoHideMenuBar: true,
    icon: path.join(__dirname, 'resources', 'imgs', 'icon.png'),
    webPreferences: {
      nodeIntegration: true,
    },
  });

  win.loadFile(path.join(__dirname, 'resources', 'index.html'));

  win.on('resize', () => {
    const [width, height] = win.getSize();
    if (width < 1920 || height < 1080) {
      win.setSize(1920, 1080);
    }
  });

  // Event: Beende den Backend-Service, wenn das Fenster geschlossen wird
  win.on('closed', () => {
    if (backendProcess) {
      console.log('Backend wird beendet...');
      // Sendet ein SIGINT-Signal, um den Prozess zu stoppen
      backendProcess.kill('SIGINT'); 
      backendProcess = null;
    }
  });
}

function startBackend() {
  const backendPath = path.resolve(__dirname, '..', 'Executor', '323');
  console.log(`Backend-Pfad: ${backendPath}`);

  const isWin = process.platform === 'win32';
  const gradlewCmd = isWin ? 'gradlew.bat' : './gradlew';

  backendProcess = spawn(gradlewCmd, ['bootRun'], {
    cwd: backendPath,
    shell: true,
  });

  backendProcess.stdout.on('data', (data) => {
    console.log(`Backend stdout: ${data}`);
  });

  backendProcess.stderr.on('data', (data) => {
    console.error(`Backend stderr: ${data}`);
  });

  backendProcess.on('close', (code) => {
    console.log(`Backend-Prozess beendet mit Code ${code}`);
  });
}

app.whenReady().then(() => {
  createWindow();
  startBackend();
});

// Event: Beende die App und den Backend-Prozess
app.on('window-all-closed', () => {
  if (backendProcess) {
    console.log('Backend wird beendet...');
    backendProcess.kill('SIGINT');
    backendProcess = null;
  }
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

// Event: Reaktiviere die App (macOS)
app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) {
    createWindow();
  }
});