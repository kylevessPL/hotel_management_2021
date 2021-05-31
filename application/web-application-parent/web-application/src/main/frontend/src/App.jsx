import React, {useContext} from 'react';
import {Redirect, Route, Switch} from "react-router-dom";
import {authContext} from './context/AuthContext';
import Home from "./components/home";
import AuthenticationLayout from "./layout/authentication";
import DashboardLayout from "./layout/dashboard";
import SignInForm from "./components/authentication/signin";
import SignUpForm from "./components/authentication/signup";
import NotFound from "./components/error";
import {AdminHome, UserHome} from "./components/dashboard";

const App = () => {

    const {useAuth, useAdmin} = useContext(authContext);

    const [isLogged] = useAuth();
    const [isAdmin] = useAdmin();

    return (
        <Switch>
            <Route exact path="/" component={Home} />
            <PublicRouteWrapper
                exact
                path="/signin"
                restricted={true}
                logged={isLogged}
                component={SignInForm}
                layout={AuthenticationLayout} />
            <PublicRouteWrapper
                exact
                path="/signup"
                restricted={true}
                logged={isLogged}
                component={SignUpForm}
                layout={AuthenticationLayout} />
            <PrivateRouteWrapper
                exact
                path="/dashboard"
                logged={isLogged}
                admin={isAdmin}
                component={isAdmin ? AdminHome : UserHome}
                layout={DashboardLayout} />
            <Route path="/404" component={NotFound} />
            <Redirect to="/404" />
        </Switch>
    );
}

const PublicRouteWrapper = ({component: Component, layout: Layout, restricted, logged, ...rest}) => {

    return (
        <Route {...rest} render={(props) =>
            logged && restricted
                ? <Redirect to="/dashboard" />
                : <Layout {...props}><Component {...props} /></Layout>
        } />
    );
}

const PrivateRouteWrapper = ({component: Component, layout: Layout, logged, admin, ...rest}) => {

    return (
        <Route {...rest} render={(props) =>
            logged
                ? <Layout admin={admin} {...props}><Component {...props} /></Layout>
                : <Redirect to="/signin" />
        } />
    );
}

export default App;
