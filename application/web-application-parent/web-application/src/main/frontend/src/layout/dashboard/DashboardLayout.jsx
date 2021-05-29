import React, {useEffect} from 'react';
import {useLocation} from 'react-router-dom';
import {getPageTitle} from "../../utils/web_utils";
import './DashboardLayout.css';

const DashboardLayout = props => {

    const location = useLocation();

    useEffect(() => {
        document.title = "HoteLA - " + getPageTitle(location.pathname)
    });

    return (
        <div className="dashboard-layout w-100 vh-100">
            {props.children}
        </div>
    );
}

export default DashboardLayout;
