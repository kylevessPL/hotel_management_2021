import React from 'react';
import {Redirect, Route} from "react-router-dom";

const PrivateRoute = ({component: Component, logged, admin, ...rest}) => {

    return (
        <Route {...rest} render={props => (
            logged
                ? <Component admin={admin} {...props} />
                : <Redirect to="/signin" />
        )} />
    );
}

export default PrivateRoute;
