import React, { useState, useEffect } from "react";
import { createRoot } from 'react-dom/client';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Link
} from 'react-router-dom';
import axios from 'axios';
import { setUser as setUserInLocal, getUser, removeUser } from "./user.ts";
import Home from './routes/home.tsx';
import Contacts from './routes/contacts.tsx';
import UpdateContact from './routes/contact.update.tsx';
import CreateContact from './routes/contact.create.tsx';
import { fetchUser, getServerUrl } from "./data.ts";
import type { ResolvedUser, User } from "./user.ts";

import { GoogleOAuthProvider, googleLogout, useGoogleLogin } from '@react-oauth/google';
import './style.css';

const CLIENT_ID = "221336944887-mbva4chbrcusdl2gmk525jse6rudfv4q.apps.googleusercontent.com";

export default function App() {
  const [ user, setUser ] = useState<User>(null);
  const [ remoteUser, setRemoteUser ] = useState<ResolvedUser>(null);

  useEffect(() => {
    // get from local storage
    const u = getUser();
    if (u) {
      // set user state
      setUser(u);
    }
  }, []);

  useEffect(() => {
    if (user) {
      fetchUser(user["access_token"]).then((u: ResolvedUser) => {
        setRemoteUser(u);
        if (u.valid) {
          setUserInLocal(user);
        }
      }).catch((e) => {
        console.log(e);
      });
      
      //debugger; // eslint-disable-line no-debugger
    }
  }, [ user ]);

  const login = useGoogleLogin({
      onSuccess: (codeResponse) => setUser(codeResponse),
      onError: (error) => console.log('Login Failed:', error)
  });

  const logOut = () => {
    googleLogout();
    setUser(null);
    setRemoteUser(null);
    removeUser();
  };

  const handleGoogleLogin = async () => {
    // Make a request to the backend server to initiate the Google OAuth2 flow
    //debugger; // eslint-disable-line no-debugger
    try {
      /*const response = await fetch(getServerUrl('/auth/google'), {
        mode: 'no-cors',
        redirect: 'follow',
      });*/
      axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';
      const response = await axios.get(getServerUrl('/auth/google'), {
        //mode: 'no-cors',
      });
      debugger; // eslint-disable-line no-debugger
      console.log(response);
      //window.location.href = response.data.redirectUrl;
    } catch (e) {
      console.log(e);
    }

    /*
    fetch(getServerUrl('/auth/google'), {
      headers: {'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',},
      redirect: 'follow',
    }).then(response => {
      debugger; // eslint-disable-line no-debugger
      console.log("dude", response);
      //window.location.href = response.data.redirectUrl;  
    }).catch(error => {
      debugger; // eslint-disable-line no-debugger
      console.error('Error initiating Google login:', error);
      console.log(error);
    });
    */
  };

  const loggedIn = true; //user && user.length === undefined;
//  const loggedIn = remoteUser && remoteUser.valid;

  return (
      <Router>
        <div className="flex-column app-frame">
          <div className="flex-row align-center header container">
            <Link to="/">Header</Link>
          </div>
          <div className="flex flex-row app-main">
            <div className="flex-column sidebar container">
              {loggedIn &&
                <>
                  <Link to="/contacts">Contacts</Link>
                  <button onClick={logOut}>Log out</button>
                </>
              }
            </div>
            <div className="app-body flex">
              <button onClick={handleGoogleLogin}>Login with Google</button>
              {!loggedIn ?                
                <button onClick={() => login()}>Sign in with Google 🚀 </button>
              :
                <Switch>
                  <Route exact path="/" component={Home} />
                  <Route exact path="/contacts" component={Contacts} />
                  <Route exact path="/contacts/create" component={CreateContact} />
                  <Route path="/contacts/update/:id" component={UpdateContact} />
                  <Route path="/contacts/:id" component={Contacts} />
                </Switch>
              }
            </div>
            {remoteUser && !remoteUser.valid &&
                <div>You are not authorized to use this site.  Contact the administrator</div>}
          </div>
        </div>
      </Router>
  );
}

const container = document.getElementById('app');
const root = createRoot(container);
root.render(
  <GoogleOAuthProvider clientId={CLIENT_ID}>
    <App/>
  </GoogleOAuthProvider>);