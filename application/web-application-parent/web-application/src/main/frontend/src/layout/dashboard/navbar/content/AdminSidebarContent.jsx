import React from 'react';

const AdminSidebarContent = props => {

    return (
        <div className="navbar-content">
            {props.children}
        </div>
    );
}

export default AdminSidebarContent;
