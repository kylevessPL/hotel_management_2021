import React, {useContext} from 'react';
import {authContext} from '../../../context/AuthContext';
import './AvailableRooms.css';
import {Button} from "reactstrap";

const AvailableRooms = () => {

    const {logout} = useContext(authContext);

    function onClick() {
        logout()
    }

    return (
        <Button onClick={onClick}>Logout admin</Button>
    );
}

export default AvailableRooms;
