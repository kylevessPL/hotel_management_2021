import React, {useContext} from 'react';
import {Redirect, Route, Switch} from "react-router-dom";
import {authContext} from './context/AuthContext';
import Home from "./components/home";
import AuthenticationLayout from "./layout/authentication";
import DashboardLayout from "./layout/dashboard";
import SignInForm from "./components/authentication/signin";
import SignUpForm from "./components/authentication/signup";
import DashboardHome from "./components/dashboard";
import NotFound from "./components/error";

const App = () => {
    return (
        <Switch>
            <Route exact path="/" component={Home} />
            <PublicRouteWrapper exact path="/signin" restricted={true} component={SignInForm} layout={AuthenticationLayout} />
            <PublicRouteWrapper exact path="/signup" restricted={true} component={SignUpForm} layout={AuthenticationLayout} />
            <PrivateRouteWrapper exact path="/dashboard" component={DashboardHome} layout={DashboardLayout} />
            <Route path="/404" component={NotFound} />
            <Redirect to="/404" />
        </Switch>
    );
}

const PublicRouteWrapper = ({component: Component, layout: Layout, restricted, ...rest}) => {
    const {useAuth} = useContext(authContext);
    const [isLogged] = useAuth();
    return (
        <Route {...rest} render={(props) =>
            isLogged && restricted
                ? <Redirect to="/dashboard" />
                : <Layout {...props}><Component {...props} /></Layout>
        } />
    );
}

const PrivateRouteWrapper = ({component: Component, layout: Layout, ...rest}) => {
    const {useAuth} = useContext(authContext);
    const [isLogged] = useAuth();
    return (
        <Route {...rest} render={(props) =>
            isLogged
                ? <Layout {...props}><Component {...props} /></Layout>
                : <Redirect to="/signin" />
        } />
    );
}

export default App;
