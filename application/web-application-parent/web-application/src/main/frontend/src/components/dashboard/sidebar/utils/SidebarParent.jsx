import React from 'react';
import {Nav, Navbar} from "reactstrap";

const SidebarParent = props => {

    return (
        <Navbar color="light" light className="sidebar-component col-md-3 col-lg-2 d-md-block collapse">
            <div className="position-sticky">
                <Nav className="flex-column">
                    {props.children}
                </Nav>
            </div>
        </Navbar>
    );
}

export default SidebarParent;
