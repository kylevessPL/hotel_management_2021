import React from 'react';
import './DashboardBase.css';
import {Route} from "react-router-dom";
import {AdminSidebar, UserSidebar} from "../sidebar";
import Navbar from "../navbar";
import BreadcrumbBar from "../breadcrumb";
import {AdminHome, UserHome} from '../home'
import {StrictSwitch} from "../../../utils";
import Footer from "../footer";

const DashboardBase = props => {

    return (
        <div className="dashboard-layout w-100 vh-100">
            <Navbar />
            {props.admin ? <AdminSidebar /> : <UserSidebar />}
            <BreadcrumbBar />
            <StrictSwitch>
                <Route exact path="/dashboard" component={props.admin ? AdminHome : UserHome} />
                <Route exact path="/dashboard/available-rooms" component={} />
                <Route exact path="/dashboard/additional-services" component={} />
                <Route exact path="/dashboard/book-room" component={} />
                <Route exact path="/dashboard/my-bookings" component={} />
                {props.admin && <Route exact path="/dashboard/users" component={} />}
                {props.admin && <Route exact path="/dashboard/discounts" component={} />}
            </StrictSwitch>
            <Footer />
        </div>
    );
}

export default DashboardBase;
