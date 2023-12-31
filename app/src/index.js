import React, { Component } from 'react';
import { render } from 'react-dom';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Link
} from 'react-router-dom';
import Home from './routes/Home';
import Contacts from './routes/Contacts';
import './style.css';

class App extends Component {
  render() {
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
}

render(<App />, document.getElementById('root'));
