import React from 'react';
import {Redirect, Route} from "react-router-dom";

const RestrictedRoute = ({component: Component, logged, ...rest}) => {

    return (
        <Route {...rest} render={props => (
            logged
                ? <Redirect to="/dashboard" />
                : <Component {...props} />
        )} />
    );
}

export default RestrictedRoute;
