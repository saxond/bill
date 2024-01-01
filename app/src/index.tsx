import React from 'react';
import { createRoot } from 'react-dom/client';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Link
} from 'react-router-dom';
import Home from './routes/home.tsx';
import Contacts from './routes/contacts.tsx';
import UpdateContact from './routes/contact.update.tsx';
import CreateContact from './routes/contact.create.tsx';
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
              <Route exact path="/contacts/create" component={CreateContact} />
              <Route path="/contacts/:id" component={Contacts} />
              <Route path="/contacts/update/:id" component={UpdateContact} />
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