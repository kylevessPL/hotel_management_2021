import React from 'react';
import './Footer.css';
import {Nav, NavItem, NavLink} from "reactstrap";
import {NavLink as RRNavLink} from 'react-router-dom';

const Footer = ({admin}) => {

    return (
        <div className="footer-component flex-shrink-0">
            <footer className="d-flex justify-content-between flex-shrink-0">
                <span className="pt-2">Copyright © 2021 HoteLA</span>
                <Nav>
                    <NavItem>
                        <NavLink tag={RRNavLink} className="text-secondary" to="/">Home</NavLink>
                    </NavItem>
                    {!admin && <NavItem>
                        <NavLink tag={RRNavLink} className="text-secondary" to="/dashboard/faq">FAQ</NavLink>
                    </NavItem>}
                </Nav>
            </footer>
        </div>
    );
}

export default Footer;
