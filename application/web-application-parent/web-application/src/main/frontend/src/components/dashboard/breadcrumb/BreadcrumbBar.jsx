import React from 'react';
import './BreadcrumbBar.css';
import {Breadcrumb, BreadcrumbItem} from "reactstrap";
import {matchRoutes} from 'react-router-config';
import {Link, useLocation} from 'react-router-dom';
import routes from "./routes";

const BreadcrumbBar = () => {

    const location = useLocation();

    let matchedRoutes = matchRoutes(routes, location.pathname);

    return (
        <Breadcrumb>
            {matchedRoutes.map((matchRoute, i) => {

                const { path, breadcrumbName } = matchRoute.route;
                const isActive = path === location.pathname;

                return isActive ? (
                    <BreadcrumbItem active key={i}>
                        {breadcrumbName}
                    </BreadcrumbItem>
                ) : (
                    <BreadcrumbItem key={i}>
                        <Link to={path}>{breadcrumbName} </Link>
                    </BreadcrumbItem>
                );
            })}
        </Breadcrumb>
    );
}

export default BreadcrumbBar;
