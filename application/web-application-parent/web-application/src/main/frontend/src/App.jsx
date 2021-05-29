import React from 'react';
import {Redirect, Route, Switch} from "react-router-dom";
import createAuthProvider from "./auth/AuthProvider";
import Home from "./components/home";
import AuthenticationLayout from "./layout/authentication";
import DashboardLayout from "./layout/dashboard";
import SignInForm from "./components/authentication/signin";
import SignUpForm from "./components/authentication/signup";
import DashboardHome from "./components/dashboard";
import NotFound from "./components/error";

export const authProvider = createAuthProvider();

const App = () => {

    const logged = authProvider.useAuth();

    function PublicRouteWrapper({component: Component, layout: Layout, restricted, ...rest}) {
        return (
            <Route {...rest} render={(props) =>
                logged && restricted
                    ? <Redirect to="/dashboard" />
                    : <Layout {...props}><Component {...props} /></Layout>
            } />
        );
    }

    function PrivateRouteWrapper({component: Component, layout: Layout, ...rest}) {
        return (
            <Route {...rest} render={(props) =>
                logged
                    ? <Layout {...props}><Component {...props} /></Layout>
                    : <Redirect to="/signin" />
            } />
        );
    }

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

export default App;
