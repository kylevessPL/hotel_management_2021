import React from 'react';
import './BreadcrumbBar.css';
import {Breadcrumb, BreadcrumbItem} from "reactstrap";
import {matchRoutes} from 'react-router-config';
import {Link, useLocation} from 'react-router-dom';
import routes from "./utils";

const BreadcrumbBar = () => {

    const location = useLocation();

    let matchedRoutes = matchRoutes(routes, location.pathname);

    return (
        <Breadcrumb>
            {matchedRoutes.map((matchRoute, i) => {

                const { path, title } = matchRoute.route;
                const isActive = path === location.pathname;

                return isActive ? (
                    <BreadcrumbItem active key={i}>
                        {title}
                    </BreadcrumbItem>
                ) : (
                    <BreadcrumbItem key={i}>
                        <Link to={path}>{title}</Link>
                    </BreadcrumbItem>
                );
            })}
        </Breadcrumb>
    );
}

export default BreadcrumbBar;
