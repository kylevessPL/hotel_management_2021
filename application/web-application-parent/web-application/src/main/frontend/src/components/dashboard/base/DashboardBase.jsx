import React from 'react';
import './DashboardBase.css';
import {AdminSidebar, UserSidebar} from "../sidebar";
import Navbar from "../navbar";
import BreadcrumbBar from "../breadcrumb";
import Footer from "../footer";
import {Container, Row} from "reactstrap";
import DashboardContentBase from "./DashboardContentBase";
import {SidebarParent} from "../sidebar/utils";

const DashboardBase = ({admin}) => {

    return (
        <div className="dashboard-layout d-flex flex-column min-vh-100">
            <Navbar />
            <Container fluid className="main-container flex-grow-1 d-flex flex-column">
                <Row className="flex-grow-1">
                    <SidebarParent>
                        {admin ? <AdminSidebar /> : <UserSidebar />}
                    </SidebarParent>
                    <main className="col-md-9 ml-sm-auto col-lg-10 px-md-4 py-4 d-flex flex-column">
                        <BreadcrumbBar />
                        <DashboardContentBase admin={admin} />
                        <Footer admin={admin} />
                    </main>
                </Row>
            </Container>
        </div>
    );
}

export default DashboardBase;
