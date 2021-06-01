import React, {useContext} from 'react';
import {authContext} from '../../../context/AuthContext';
import './BookRoom.css';
import {Button} from "reactstrap";

const BookRoom = () => {

    const {logout} = useContext(authContext);

    function onClick() {
        logout()
    }

    return (
        <Button onClick={onClick}>Logout admin</Button>
    );
}

export default BookRoom;
