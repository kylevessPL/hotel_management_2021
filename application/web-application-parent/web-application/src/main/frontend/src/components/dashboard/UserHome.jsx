import React, {useContext} from 'react';
import {authContext} from '../../context/AuthContext';
import './UserHome.css';
import {Button} from "reactstrap";

const UserHome = () => {

    const {logout} = useContext(authContext);

    function onClick() {
        logout()
    }

    return (
        <Button onClick={onClick}>Logout user</Button>
    );
}

export default UserHome;
