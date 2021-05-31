import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router} from "react-router-dom";
import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import App from "./App";
import {ScrollToTop} from "./utils";
import AuthContext from "./context/AuthContext";

ReactDOM.render(
    <AuthContext>
        <Router>
            <ScrollToTop />
            <App />
        </Router>
    </AuthContext>,
    document.getElementById('root')
);
