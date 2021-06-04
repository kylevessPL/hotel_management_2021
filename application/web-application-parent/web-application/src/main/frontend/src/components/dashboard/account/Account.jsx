import React, {useContext, useState} from 'react';
import './Account.css';
import {Alert, Button, Card, CardBody, Col, FormGroup, Input, Label, Row} from "reactstrap";
import {ErrorMessage, Field, Form, Formik} from "formik";
import * as Yup from 'yup';
import {API_PATH} from "../../../utils";
import {authContext} from "../../../context/AuthContext";
import avatarDefault from '../../../assets/images/avatar_default.png';

const Account = () => {

    const {authFetch} = useContext(authContext);

    const [updated, setUpdated] = useState(false);
    const [requestFailedMessage, setRequestFailedMessage] = useState('');

    const username = window.localStorage.getItem('dotcom_user');
    const email = window.localStorage.getItem('dotcom_email');

    return (
        <div>
            <h4 className="mb-4">My account</h4>
            <Row>
                <Col lg={4} className="pb-5">
                    <Card className="pb-3">
                        <CardBody>
                            <div className="avatar rounded-circle overflow-hidden">
                                <img src={avatarDefault} alt="Avatar" className="card-img-top" />
                            </div>
                        </CardBody>
                    </Card>
                </Col>
                <Formik
                    initialValues={{currentPassword: '', newPassword: '', repeatPassword: ''}}
                    validationSchema={Yup.object({
                        currentPassword: Yup.string()
                            .required('Current password is required'),
                        newPassword: Yup.string()
                            .min(3, 'Password must be at least 6 characters long')
                            .max(120, 'Password must be maximum 120 characters long')
                            .matches(
                                '^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).+$',
                                'Password must contain at least one lowercase letter, one uppercase letter, one number and one special character')
                            .required('New password is required'),
                        repeatPassword: Yup.string()
                            .required('Repeat password is required')
                            .oneOf([Yup.ref('newPassword'), null], 'Passwords must match')
                    })}
                    onSubmit={async (values, { setSubmitting, resetForm }) => {
                        setUpdated(false);
                        setRequestFailedMessage('');
                        const {currentPassword, newPassword} = values;
                        const body = {
                            oldPassword: currentPassword,
                            newPassword: newPassword
                        };
                        const options = {
                            method: 'PUT',
                            mode: 'cors',
                            body: JSON.stringify(body),
                            headers: {
                                'Content-Type': 'application/json',
                                'Access-Control-Allow-Origin': '*'
                            }
                        };
                        await authFetch(`${API_PATH}/users/current/password`, options)
                            .then(async response => {
                                if (response.ok) {
                                    setUpdated(true);
                                    setRequestFailedMessage('');
                                } else {
                                    await response.json()
                                        .then((data) => {
                                            if ([409, 422].indexOf(response.status) >= 0) {
                                                if (response.status === 409) {
                                                    resetForm(values);
                                                }
                                                setUpdated(false);
                                                setRequestFailedMessage(data.message);
                                            } else {
                                                setUpdated(false);
                                                setRequestFailedMessage('Request cannot be fulfilled now. Please try again later.');
                                            }
                                        })
                                }
                            }).catch((error) => {
                                console.error(error);
                                setUpdated(false);
                                setRequestFailedMessage('Request cannot be fulfilled now. Please try again later.');
                            }).finally(() => {
                                resetForm(values);
                                setSubmitting(false);
                            });
                    }}
                >
                    {({
                          errors,
                          touched,
                          isSubmitting
                    }) => (
                        <Col lg={8} className="pb-5">
                            {updated
                                ? <Alert color="success">Profile updated successfully.</Alert>
                                : requestFailedMessage.length > 0 && <Alert color="danger">{requestFailedMessage}</Alert>}
                            <Form>
                                <Row form>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label for="username">Username</Label>
                                            <Input disabled
                                                   id="username"
                                                   name="username"
                                                   type="text"
                                                   value={username}
                                                   className="form-control" />
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label for="email">Email</Label>
                                            <Input disabled
                                                   id="email"
                                                   name="email"
                                                   type="text"
                                                   value={email}
                                                   className="form-control" />
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label for="current-password">Current Password</Label>
                                            <Field id="current-password"
                                                   name="currentPassword"
                                                   type="password"
                                                   className={'form-control' + (errors.currentPassword && touched.currentPassword ? ' is-invalid' : '')} />
                                            <ErrorMessage name="currentPassword" component="div" className="invalid-feedback"/>
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label for="new-password">New Password</Label>
                                            <Field id="new-password"
                                                   name="newPassword"
                                                   type="password"
                                                   className={'form-control' + (errors.newPassword && touched.newPassword ? ' is-invalid' : '')} />
                                            <ErrorMessage name="newPassword" component="div" className="invalid-feedback"/>
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label for="repeat-password">Confirm Password</Label>
                                            <Field id="repeat-password"
                                                   name="repeatPassword"
                                                   type="password"
                                                   className={'form-control' + (errors.repeatPassword && touched.repeatPassword ? ' is-invalid' : '')} />
                                            <ErrorMessage name="repeatPassword" component="div" className="invalid-feedback"/>
                                        </FormGroup>
                                    </Col>
                                    <Col xs={12}>
                                        <hr className="mt-2 mb-3" />
                                        <div className="d-flex flex-wrap justify-content-between align-items-center">
                                            <Button color="primary" type="submit" disabled={isSubmitting}>Update Profile</Button>
                                        </div>
                                    </Col>
                                </Row>
                            </Form>
                        </Col>
                    )}
                </Formik>
            </Row>
        </div>
    );
}

export default Account;
