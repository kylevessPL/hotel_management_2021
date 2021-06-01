import React, {useState} from 'react';
import {Link} from 'react-router-dom';
import './Navbar.css';
import {AddressBookIcon, SignOutAltIcon} from 'react-line-awesome';
import {ButtonDropdown, Col, DropdownItem, DropdownMenu, DropdownToggle, Navbar, NavbarBrand} from 'reactstrap'
import ConfirmationModal from "../../utils";

const Navbar = () => {

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
                    <button className="navbar-toggler d-md-none collapsed mb-3" type="button" data-toggle="collapse"
                            data-target="#sidebar" aria-controls="sidebar" aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon" />
                    </button>
                </Col>
                <Col xs={12} md={5} lg={8} className="d-flex align-items-center justify-content-md-end mt-3 mt-md-0">
                    <ButtonDropdown isOpen={dropdownOpen} toggle={toggleDropdown}>
                        <DropdownToggle caret color="primary" className="text-left">
                            Hello, {username}
                        </DropdownToggle>
                        <DropdownMenu right={true} classname="w-100">
                            <DropdownItem tag={Link} exact to="/dashboard/account" className="pl-2">
                                <AddressBookIcon className="align-top mr-1" />
                                My account
                            </DropdownItem>
                            <DropdownItem onClick={toggleModal} className="pl-2">
                                <SignOutAltIcon className="align-top mr-1" />
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

export default Navbar;
