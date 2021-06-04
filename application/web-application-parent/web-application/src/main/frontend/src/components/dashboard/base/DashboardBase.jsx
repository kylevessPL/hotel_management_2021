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
        <div className="dashboard-layout min-vh-100 d-flex flex-column">
            <Navbar />
            <Container fluid className="main-container flex-grow-1">
                <Row>
                    <SidebarParent>
                        {admin ? <AdminSidebar /> : <UserSidebar />}
                    </SidebarParent>
                    <main className="col-md-9 ml-sm-auto col-lg-10 px-md-4 py-4">
                        <BreadcrumbBar />
                        <DashboardContentBase admin={admin} />
                    </main>
                </Row>
            </Container>
            <Footer admin={admin} />
        </div>
    );
}

export default DashboardBase;
