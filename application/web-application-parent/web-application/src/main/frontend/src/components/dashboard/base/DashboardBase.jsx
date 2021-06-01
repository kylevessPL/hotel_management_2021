import React from 'react';
import './DashboardBase.css';
import {Route} from "react-router-dom";
import {AdminSidebar, UserSidebar} from "../sidebar";
import Navbar from "../navbar";
import BreadcrumbBar from "../breadcrumb";
import {AdminHome, UserHome} from '../home'
import {StrictSwitch} from "../../../utils";
import Footer from "../footer";
import AvailableRooms from "../availablerooms";
import AdditionalServices from "../additionalservices";
import BookRoom from "../bookroom";
import MyBookings from "../mybookings";
import Faq from "../faq";
import Account from "../account";
import Users from "../users";
import Discounts from "../discounts";

const DashboardBase = props => {

    return (
        <div className="dashboard-layout w-100 vh-100">
            <Navbar />
            {props.admin ? <AdminSidebar /> : <UserSidebar />}
            <BreadcrumbBar />
            <StrictSwitch>
                <Route exact path="/dashboard" component={props.admin ? AdminHome : UserHome} />
                <Route exact path="/dashboard/available-rooms" component={AvailableRooms} />
                <Route exact path="/dashboard/additional-services" component={AdditionalServices} />
                <Route exact path="/dashboard/book-room" component={BookRoom} />
                <Route exact path="/dashboard/my-bookings" component={MyBookings} />
                <Route exact path="/dashboard/faq" component={Faq} />
                {!props.admin && <Route exact path="/dashboard/account" component={Account} />}
                {props.admin && <Route exact path="/dashboard/users" component={Users} />}
                {props.admin && <Route exact path="/dashboard/discounts" component={Discounts} />}
            </StrictSwitch>
            <Footer />
        </div>
    );
}

export default DashboardBase;
