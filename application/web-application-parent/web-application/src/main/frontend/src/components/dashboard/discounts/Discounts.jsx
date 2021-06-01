import React, {useContext} from 'react';
import {authContext} from '../../../context/AuthContext';
import './Discounts.css';
import {Button} from "reactstrap";

const Discounts = () => {

    const {logout} = useContext(authContext);

    function onClick() {
        logout()
    }

    return (
        <Button onClick={onClick}>Logout admin</Button>
    );
}

export default Discounts;
