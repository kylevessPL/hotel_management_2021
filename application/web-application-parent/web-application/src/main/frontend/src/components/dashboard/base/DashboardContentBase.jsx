import React, {useEffect} from 'react';
import './DashboardBase.css';
import {Route, useLocation} from "react-router-dom";
import {AdminHome, UserHome} from '../home'
import {StrictSwitch} from "../../../utils";
import AvailableRooms from "../availablerooms";
import AdditionalServices from "../additionalservices";
import BookRoom from "../bookroom";
import MyBookings from "../mybookings";
import Faq from "../faq";
import Account from "../account";
import Users from "../users";
import Discounts from "../discounts";
import {matchRoutes} from "react-router-config";
import routes from "../breadcrumb/utils/routes";

const DashboardContentBase = ({admin}) => {

    const location = useLocation();

    let matchedRoutes = matchRoutes(routes, location.pathname);

    useEffect(() => {
        document.title = "HoteLA - " + matchedRoutes.pop().route.title;
    });

    return (
        <StrictSwitch>
            <Route exact path="/dashboard" component={admin ? AdminHome : UserHome} />
            {!admin && <Route exact path="/dashboard/available-rooms" component={AvailableRooms} />}
            {!admin && <Route exact path="/dashboard/additional-services" component={AdditionalServices} />}
            {!admin && <Route exact path="/dashboard/book-room" component={BookRoom} />}
            {!admin && <Route exact path="/dashboard/my-bookings" component={MyBookings} />}
            {!admin && <Route exact path="/dashboard/faq" component={Faq} />}
            {!admin && <Route exact path="/dashboard/account" component={Account} />}
            {admin && <Route exact path="/dashboard/users" component={Users} />}
            {admin && <Route exact path="/dashboard/discounts" component={Discounts} />}
        </StrictSwitch>
    );
}

export default DashboardContentBase;
