import React, {useEffect} from 'react';
import {Card, CardBody, Col, Container, Row} from "reactstrap";
import {useLocation} from 'react-router-dom';
import './AuthenticationLayout.css';

const AuthenticationLayout = props => {

    const location = useLocation();

    useEffect(() => {
        document.title = "HoteLA - " + (location.pathname.substr(1) === 'signin' ? "Login" : "Register")
    });

    return (
        <div className="authentication-layout w-100 vh-100">
            <Container>
                <Row>
                    <Col sm={9} md={7} lg={5} className="mx-auto">
                        <Card className="my-5">
                            <CardBody>
                                {props.children}
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            </Container>
        </div>
    );
}

export default AuthenticationLayout;
