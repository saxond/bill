import React from 'react';
import { createRoot } from 'react-dom/client';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Link
} from 'react-router-dom';
import Home from './routes/Home';
import Contacts from './routes/Contacts';
import './style.css';

export default function App() {
  return (
    <Router>
      <div className="flex-column app-frame">
        <div className="flex-row align-center header container">
          <Link to="/">Header</Link>
        </div>
        <div className="flex flex-row app-main">
          <div className="flex-column sidebar container">
            <Link to="/contacts">Contacts</Link>
          </div>
          <div className="app-body flex">
            <Switch>
              <Route exact path="/" component={Home} />
              <Route exact path="/contacts" component={Contacts} />
            </Switch>
          </div>
        </div>
      </div>
    </Router>
  );
}

const container = document.getElementById('app');
const root = createRoot(container);
root.render(<App />);