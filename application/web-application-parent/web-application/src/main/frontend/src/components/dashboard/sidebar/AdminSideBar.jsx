import React from 'react';
import {SidebarItem} from "./utils";
import {HomeIcon, TagsIcon, UsersIcon} from "react-line-awesome";

const AdminSideBar = () => {

    return (
        <>
            <SidebarItem path="/dashboard" title="Dashboard" icon={HomeIcon} />
            <SidebarItem path="/dashboard/users" title="Users" icon={UsersIcon} />
            <SidebarItem path="/dashboard/discounts" title="Discounts" icon={TagsIcon} />
        </>
    );
}

export default AdminSideBar;
