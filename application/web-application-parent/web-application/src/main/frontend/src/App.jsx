import React, {useContext} from 'react';
import {Route} from "react-router-dom";
import {authContext} from './context/AuthContext';
import Home from "./components/home";
import AuthenticationBase from "./components/authentication/base";
import DashboardBase from "./components/dashboard/base";
import {PrivateRoute, RestrictedRoute, StrictSwitch} from "./utils";

const App = () => {

    const {useAuth, useAdmin} = useContext(authContext);

    const [isLogged] = useAuth();
    const [isAdmin] = useAdmin();

    return (
        <StrictSwitch>
            <Route exact path="/" component={Home} />
            <RestrictedRoute
                exact
                path={["/signin", "/signup"]}
                logged={isLogged}
                component={AuthenticationBase} />
            <PrivateRoute
                path="/dashboard"
                logged={isLogged}
                admin={isAdmin}
                component={DashboardBase} />
        </StrictSwitch>
    );
}

export default App;
