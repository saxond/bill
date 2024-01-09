//  https://github.com/callicoder/spring-boot-react-oauth2-social-login-demo

import React from 'react';
//import { ACCESS_TOKEN } from '../../constants';
import { Redirect } from 'react-router-dom'

export default function OAuth2RedirectHandler() {
    const getUrlParameter = (name) => {
        name = name.replace(/[\\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');

        var results = regex.exec(this.props.location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    };

    const token = getUrlParameter('token');
    const error = getUrlParameter('error');

    if (token) {
        localStorage.setItem("access-token", token);
        return (<Redirect to={{
            pathname: "/profile",
            state: { from: this.props.location }
        }}/>);
    } else {
        return (<Redirect to={{
            pathname: "/login",
            state: { 
                from: this.props.location,
                error: error 
            }
        }}/>);
    }
}