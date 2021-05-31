import React from 'react';

const UserSidebarContent = props => {

    return (
        <div className="navbar-content">
            {props.children}
        </div>
    );
}

export default UserSidebarContent;
