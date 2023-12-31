// routes
import Router from "./routes";
// theme
import ThemeProvider from './theme';
// components
import ThemeSettings from './components/settings';
import { AuthContextProvider } from './contexts/AuthContext';

function App() {
  return (
    <ThemeProvider>
      <ThemeSettings>
        <AuthContextProvider>
          {" "}
          <Router />{" "}
        </AuthContextProvider>
      </ThemeSettings>
    </ThemeProvider>
  );
}

export default App;
