import React from 'react';
import {NavItem, NavLink} from "reactstrap";
import {NavLink as RRLink} from "react-router-dom";

const SideBarItem = ({path, title, icon: Icon}) => {

    return (
        <NavItem>
            <NavLink tag={RRLink} exact to={path} style={{color: "#0c68f1"}}>
                <Icon className="align-bottom" component="span" style={{fontSize: "32px", marginRight: "4px"}} />
                <span>{title}</span>
            </NavLink>
        </NavItem>
    );
}

export default SideBarItem;
