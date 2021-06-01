import React, {useContext} from 'react';
import {authContext} from '../../../context/AuthContext';
import './Account.css';
import {Button} from "reactstrap";

const Account = () => {

    const {logout} = useContext(authContext);

    function onClick() {
        logout()
    }

    return (
        <Button onClick={onClick}>Logout admin</Button>
    );
}

export default Account;
