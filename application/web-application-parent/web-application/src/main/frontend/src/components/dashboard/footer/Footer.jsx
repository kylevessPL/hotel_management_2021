import React from 'react';
import './Footer.css';
import {Col, Container, Nav, NavItem, NavLink, Row} from "reactstrap";
import {NavLink as RRNavLink} from 'react-router-dom';

const Footer = () => {

    return (
        <Container fluid className="footer-component flex-shrink-0">
            <Row>
                <Col md={9} lg={10} className="ml-sm-auto px-md-4 py-3">
                    <footer className="d-flex justify-content-between flex-shrink-0">
                        <span className="pt-2">Copyright © 2021 HoteLA</span>
                        <Nav>
                            <NavItem>
                                <NavLink tag={RRNavLink} className="text-secondary" to="/">Home</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink tag={RRNavLink} className="text-secondary" to="/support/faq">FAQ</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink tag={RRNavLink} className="text-secondary" to="/support/contact">Contact</NavLink>
                            </NavItem>
                        </Nav>
                    </footer>
                </Col>
            </Row>
        </Container>
    );
}

export default Footer;
