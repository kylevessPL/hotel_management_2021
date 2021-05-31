import React from 'react';
import {Redirect, Route, Switch} from "react-router-dom";
import NotFound from "../components/error";

function StrictSwitch(props) {

    return (
        <Switch>
            {props.children}
            <Route path="/404" component={NotFound} />
            <Redirect to="/404" />
        </Switch>
    );
}

export default StrictSwitch;
