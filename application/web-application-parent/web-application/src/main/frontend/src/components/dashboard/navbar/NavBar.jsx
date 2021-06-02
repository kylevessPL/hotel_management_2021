import React, {useContext, useState} from 'react';
import {Link} from 'react-router-dom';
import {authContext} from '../../../context/AuthContext';
import './NavBar.css';
import {SignOutAltIcon, UserCircleIcon} from 'react-line-awesome';
import {ButtonDropdown, Col, DropdownItem, DropdownMenu, DropdownToggle, Navbar, NavbarBrand} from 'reactstrap'
import ConfirmationModal from "../../utils";

const NavBar = () => {

    const username = window.localStorage.getItem('dotcom_user');

    const {logout} = useContext(authContext);
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const [modalOpen, setModalOpen] = useState(false);

    const toggleModal = () => setModalOpen(!modalOpen);
    const toggleDropdown = () => setDropdownOpen(prevState => !prevState);

    return (
        <div>
            <Navbar color="light" light className="p-2 flex-shrink-0">
                <Col xs={12} md={3} lg={2} className="d-flex justify-content-between mb-lg-0">
                    <NavbarBrand tag={Link} exact to="/dashboard">
                        <img src="/favicon.ico" alt="HoteLA logo" width="52" height="52" className="mr-2" />
                        HoteLA Client Dashboard
                    </NavbarBrand>
                </Col>
                <Col xs={12} md={5} lg={8} className="d-flex align-items-center justify-content-md-end mt-3 mt-md-0">
                    <ButtonDropdown isOpen={dropdownOpen} toggle={toggleDropdown} className="navbar-dropdown">
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
                    </ButtonDropdown>
                </Col>
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
