import React from 'react';
import {Card, CardBody, Col, Container, Row} from "reactstrap";
import './AuthenticationBase.css';
import {Route} from "react-router-dom";
import SignInForm from '../signin/SignInForm'
import SignUpForm from '../signup/SignUpForm'
import {StrictSwitch} from "../../../utils";

const AuthenticationBase = () => {

    return (
        <div className="authentication-base w-100 vh-100">
            <Container>
                <Row>
                    <Col sm={9} md={7} lg={5} className="mx-auto">
                        <Card className="my-5">
                            <CardBody>
                                <StrictSwitch>
                                    <Route exact path="/signin" component={SignInForm} />
                                    <Route exact path="/signup" component={SignUpForm} />
                                </StrictSwitch>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            </Container>
        </div>
    );
}

export default AuthenticationBase;
