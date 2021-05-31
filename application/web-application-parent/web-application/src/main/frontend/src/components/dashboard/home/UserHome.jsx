import React, {useContext} from 'react';
import './UserHome.css';
import {authContext} from "../../../context/AuthContext";
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
