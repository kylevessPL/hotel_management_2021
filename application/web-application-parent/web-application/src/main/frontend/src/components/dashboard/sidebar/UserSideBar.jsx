import React from 'react';
import {BedIcon, BookmarkIcon, CalendarCheckIcon, ConciergeBellIcon, HomeIcon} from 'react-line-awesome';
import {SidebarItem} from "./utils";

const UserSideBar = () => {

    return (
        <>
            <SidebarItem path="/dashboard" title="Dashboard" icon={HomeIcon} />
            <SidebarItem path="/dashboard/available-rooms" title="Available rooms" icon={BedIcon} />
            <SidebarItem path="/dashboard/additional-services" title="Additional services" icon={ConciergeBellIcon} />
            <SidebarItem path="/dashboard/book-room" title="Book room" icon={CalendarCheckIcon} />
            <SidebarItem path="/dashboard/my-bookings" title="My bookings" icon={BookmarkIcon} />
        </>
    );
}

export default UserSideBar;
