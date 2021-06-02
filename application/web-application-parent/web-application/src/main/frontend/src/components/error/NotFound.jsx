import React, {useEffect} from 'react'
import {Link} from 'react-router-dom'
import './NotFound.css';

const NotFound = () => {

    useEffect(() => {
        document.title = "HoteLA - Page Not Found";
    });

    return (
        <div id="notfound">
            <div className="notfound">
                <div className="notfound-404" />
                <h1>404</h1>
                <h2>Oops! Page Cannot Be Found</h2>
                <p>Sorry but the page you are looking for does not exist, have been removed, name changed or is temporarily unavailable</p>
                <Link to="/">Back to homepage</Link>
            </div>
        </div>
    )
}

export default NotFound;
