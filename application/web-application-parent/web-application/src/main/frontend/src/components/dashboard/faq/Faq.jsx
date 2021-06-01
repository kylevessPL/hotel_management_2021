import React, {useContext} from 'react';
import {authContext} from '../../../context/AuthContext';
import './Faq.css';
import {Button} from "reactstrap";

const Faq = () => {

    const {logout} = useContext(authContext);

    function onClick() {
        logout()
    }

    return (
        <Button onClick={onClick}>Logout admin</Button>
    );
}

export default Faq;
