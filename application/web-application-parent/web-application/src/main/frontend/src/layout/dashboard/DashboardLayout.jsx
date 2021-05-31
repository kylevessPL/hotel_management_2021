import React, {useEffect} from 'react';
import {useLocation} from 'react-router-dom';
import {getPageTitle} from "../../utils/web_utils";
import './DashboardLayout.css';
import Sidebar from "./sidebar";
import Navbar from "./navbar";
import Breadcrumb from "./breadcrumb";
import {AdminSidebarContent, UserSidebarContent} from "./navbar/content";

const DashboardLayout = props => {

    const location = useLocation();

    useEffect(() => {
        document.title = "HoteLA - " + getPageTitle(location.pathname)
    });

    return (
        <div className="dashboard-layout w-100 vh-100">
            <Navbar />
            <Sidebar>
                {props.admin ? <AdminSidebarContent /> : <UserSidebarContent />}
            </Sidebar>
            <Breadcrumb />
            {props.children}
        </div>
    );
}

export default DashboardLayout;
