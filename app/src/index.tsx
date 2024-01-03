import React, { useState } from "react";
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

import { GoogleLogin, GoogleOAuthProvider, googleLogout } from '@react-oauth/google';
import './style.css';

const CLIENT_ID = "221336944887-mbva4chbrcusdl2gmk525jse6rudfv4q.apps.googleusercontent.com";

export default function App() {
  const [ credential, setCredential ] = useState([]);

  const logOut = () => {
    googleLogout();
    setCredential(null);
  };

  if (credential) {
    return (
      <GoogleOAuthProvider clientId={CLIENT_ID}>
        <Router>
          <div className="flex-column app-frame">
            <div className="flex-row align-center header container">
              <Link to="/">Header</Link>
            </div>
            <div className="flex flex-row app-main">
              <div className="flex-column sidebar container">
                <Link to="/contacts">Contacts</Link>
                <button onClick={logOut}>Log out</button>
              </div>
              <div className="app-body flex">
                <Switch>
                  <Route exact path="/" component={Home} />
                  <Route exact path="/contacts" component={Contacts} />
                  <Route exact path="/contacts/create" component={CreateContact} />
                  <Route path="/contacts/update/:id" component={UpdateContact} />
                  <Route path="/contacts/:id" component={Contacts} />
                </Switch>
              </div>
            </div>
          </div>
        </Router>
      </GoogleOAuthProvider>
    );
  } else {
    const responseMessage = (response) => {
      setCredential(response.credential);
    };
    const errorMessage = (error) => {
      console.log(error);
    };
    return (
      <GoogleOAuthProvider clientId={CLIENT_ID}>
        <div>
          <h2>React Google Login</h2>
          <br />
          <br />
          <GoogleLogin onSuccess={responseMessage} onError={errorMessage} />
        </div>
      </GoogleOAuthProvider>
    )
  }
}

const container = document.getElementById('app');
const root = createRoot(container);

root.render(<App />);