import React, {useContext} from 'react';
import {authContext} from '../../../context/AuthContext';
import './AdditionalServices.css';
import {Button} from "reactstrap";

const AdditionalServices = () => {

    const {logout} = useContext(authContext);

    function onClick() {
        logout()
    }

    return (
        <Button onClick={onClick}>Logout admin</Button>
    );
}

export default AdditionalServices;
