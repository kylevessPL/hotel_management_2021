import React, {useContext, useState} from 'react';
import {Link} from 'react-router-dom';
import {authContext} from '../../../context/AuthContext';
import './NavBar.css';
import {SignOutAltIcon, UserCircleIcon} from 'react-line-awesome';
import {
    Col,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Nav,
    Navbar,
    NavbarBrand,
    NavbarToggler,
    UncontrolledButtonDropdown,
    UncontrolledCollapse
} from 'reactstrap'
import {ConfirmationModal} from "../../utils";
import {AdminSidebar, UserSidebar} from "../sidebar";

const NavBar = ({admin}) => {

    const username = window.localStorage.getItem('dotcom_user');

    const {logout} = useContext(authContext);
    const [modalOpen, setModalOpen] = useState(false);

    const toggleModal = () => setModalOpen(!modalOpen);

    return (
        <div>
            <Navbar color="light" light className="p-2">
                <Col xs={12} md={3} lg={2} className="d-flex justify-content-between mb-lg-0">
                    <NavbarBrand tag={Link} exact to="/dashboard">
                        <img src="/favicon.ico" alt="HoteLA logo" width="52" height="52" className="mr-2" />
                        HoteLA Client Dashboard
                    </NavbarBrand>
                    <NavbarToggler id="toggler" className="d-md-none mb-3" />
                </Col>
                <Col xs={12} md={5} lg={8} className="d-flex align-items-center justify-content-md-end mt-3 mt-md-0">
                    <UncontrolledButtonDropdown className="navbar-dropdown">
                        <DropdownToggle caret color="primary" className="text-left">
                            Hello, {username}
                        </DropdownToggle>
                        <DropdownMenu right={true} classname="w-100">
                            <DropdownItem tag={Link} exact to="/dashboard/account" className="pl-2">
                                <UserCircleIcon className="align-top mr-1" style={{fontSize: "28px"}} />
                                My account
                            </DropdownItem>
                            <DropdownItem onClick={toggleModal} className="pl-2">
                                <SignOutAltIcon className="align-top mr-1" style={{fontSize: "28px"}} />
                                Sign out
                            </DropdownItem>
                        </DropdownMenu>
                    </UncontrolledButtonDropdown>
                </Col>
                <UncontrolledCollapse toggler="#toggler" className="d-md-none mt-3">
                    <Nav vertical className="sidebar-nav">
                        {admin ? <AdminSidebar /> : <UserSidebar />}
                    </Nav>
                </UncontrolledCollapse>
            </Navbar>
            <ConfirmationModal
                title="Sign out"
                content="Are you sure you want to sign out?"
                confirmText="Sign out"
                onConfirm={logout}
                open={modalOpen}
                toggle={toggleModal}/>
        </div>
    );
}

export default NavBar;
