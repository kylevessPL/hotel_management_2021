import React, {useContext} from 'react';
import {authContext} from '../../../context/AuthContext';
import './Users.css';
import {Button} from "reactstrap";

const Users = () => {

    const {logout} = useContext(authContext);

    function onClick() {
        logout()
    }

    return (
        <Button onClick={onClick}>Logout admin</Button>
    );
}

export default Users;
