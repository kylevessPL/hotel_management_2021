import React, {useContext} from 'react';
import {authContext} from '../../../context/AuthContext';
import './MyBookings.css';
import {Button} from "reactstrap";

const MyBookings = () => {

    const {logout} = useContext(authContext);

    function onClick() {
        logout()
    }

    return (
        <Button onClick={onClick}>Logout admin</Button>
    );
}

export default MyBookings;
