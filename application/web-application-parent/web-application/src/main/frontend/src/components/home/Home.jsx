import React, {useContext} from 'react';
import {authContext} from '../../context/AuthContext';
import './Home.css';
import {Button} from "reactstrap";

const Home = () => {

    const {logout} = useContext(authContext);

    function onClick() {
        logout()
    }

    return (
        <Button onClick={onClick}>Logout</Button>
    );
}

export default Home;
