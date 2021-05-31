import React from 'react';
import {NavItem, NavLink} from "reactstrap";
import {Link, useLocation} from "react-router-dom";

const SidebarItem = ({path, title, icon: Icon}) => {

    const location = useLocation();

    return (
        <NavItem>
            <NavLink tag={Link} exact to={path} active={path === location.pathname}>
                <Icon className="align-bottom" component="span">
                    <span>{title}</span>
                </Icon>
            </NavLink>
        </NavItem>
    );
}

export default SidebarItem;
