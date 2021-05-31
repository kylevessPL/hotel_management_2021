import React, {useContext} from 'react';
import {authContext} from '../../../context/AuthContext';
import './AdminHome.css';
import {Button} from "reactstrap";

const AdminHome = () => {

    const {logout} = useContext(authContext);

    function onClick() {
        logout()
    }

    return (
        <Button onClick={onClick}>Logout admin</Button>
    );
}

export default AdminHome;
