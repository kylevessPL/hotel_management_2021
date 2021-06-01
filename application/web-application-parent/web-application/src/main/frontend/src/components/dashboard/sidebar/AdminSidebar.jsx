import React from 'react';
import './Sidebar.css';
import {SidebarItem, SidebarParent} from "./utils";
import {HomeIcon, TagsIcon, UsersIcon} from "react-line-awesome";

const AdminSidebar = () => {

    return (
        <SidebarParent>
            <SidebarItem path="/dashboard" title="Dashboard" icon={HomeIcon} />
            <SidebarItem path="/dashboard/users" title="Users" icon={UsersIcon} />
            <SidebarItem path="/dashboard/discounts" title="Users" icon={TagsIcon} />
        </SidebarParent>
    );
}

export default AdminSidebar;
