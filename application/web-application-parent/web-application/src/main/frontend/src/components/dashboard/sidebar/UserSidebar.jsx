import React from 'react';
import './Sidebar.css';
import {BedIcon, CalendarCheckIcon, ConciergeBellIcon, FileInvoiceDollarIcon, HomeIcon} from 'react-line-awesome';
import {SidebarItem, SidebarParent} from "./utils";

const UserSidebar = () => {

    return (
        <SidebarParent>
            <SidebarItem path="/dashboard" title="Dashboard" icon={HomeIcon} />
            <SidebarItem path="/dashboard/available-rooms" title="Available rooms" icon={BedIcon} />
            <SidebarItem path="/dashboard/additional-services" title="Additional services" icon={ConciergeBellIcon} />
            <SidebarItem path="/dashboard/book-room" title="Book a room" icon={CalendarCheckIcon} />
            <SidebarItem path="/dashboard/my-bookings" title="My bookings" icon={FileInvoiceDollarIcon} />
        </SidebarParent>
    );
}

export default UserSidebar;
