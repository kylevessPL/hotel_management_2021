import React from 'react';
import {Nav} from "reactstrap";
import '../SideBar.css';

const SideBarParent = ({children}) => {

    return (
        <nav className="sidebar-component bg-light col-md-3 col-lg-2 d-md-block collapse position-fixed">
            <div className="position-sticky">
                <Nav className="flex-column">
                    {children}
                </Nav>
            </div>
        </nav>
    );
}

export default SideBarParent;
