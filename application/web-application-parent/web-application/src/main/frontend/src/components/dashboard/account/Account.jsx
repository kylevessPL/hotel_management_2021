import React, {useContext, useEffect, useState} from 'react';
import './Account.css';
import {Alert, Button, Card, CardBody, Col, FormGroup, Input, Label, Row} from "reactstrap";
import {ErrorMessage, Field, Form, Formik} from "formik";
import * as Yup from 'yup';
import {API_PATH} from "../../../utils";
import {authContext} from "../../../context/AuthContext";
import BrowseImageInput from './utils'
import {ConfirmationModal} from "../../utils";
import avatarDefault from '../../../assets/images/avatar_default.png';
import FormikErrorFocus from "formik-error-focus";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrashAlt} from "@fortawesome/free-solid-svg-icons"

const Account = () => {

    const FILE_SIZE = 200 * 1024;
    const SUPPORTED_FORMATS = [
        "image/jpg",
        "image/jpeg",
        "image/png"
    ];

    const {authFetch} = useContext(authContext);

    const [avatarImage, setAvatarImage] = useState(avatarDefault);
    const [profileUpdated, setProfileUpdated] = useState(false);
    const [profileRequestFailedMessage, setProfileRequestFailedMessage] = useState('');
    const [avatarUpdated, setAvatarUpdated] = useState(false);
    const [avatarRequestFailedMessage, setAvatarRequestFailedMessage] = useState('');
    const [modalOpen, setModalOpen] = useState(false);

    const toggleModal = () => setModalOpen(!modalOpen);

    const username = window.localStorage.getItem('dotcom_user');
    const email = window.localStorage.getItem('dotcom_email');

    const removeAvatar = () => {
        authFetch(`${API_PATH}/users/current/avatar`, {
            method: "DELETE",
            mode: "cors",
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        }).then(async (response) => {
            if (response.ok) {
                setAvatarImage(avatarDefault);
            } else {
                throw new Error('Avatar image fetch failed');
            }
        }).catch((error) => console.log(error));
    }

    useEffect(() => {
        authFetch(`${API_PATH}/users/current/avatar`, {
            method: "GET",
            mode: "cors",
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        }).then(async (response) => {
            if (response.ok) {
                await response.blob()
                    .then(data => setAvatarImage(URL.createObjectURL(data)));
            } else if (response.status !== 409) {
                throw new Error('Avatar image fetch failed');
            }
        }).catch((error) => console.log(error));
    }, []);

    return (
        <div>
            <h4 className="mb-4">My account</h4>
            <Row>
                <Col lg={4} className="pb-5">
                    <Card className="pb-3">
                        <CardBody>
                            <div className="avatar rounded-circle overflow-hidden mb-4">
                                <img src={avatarImage} alt="Avatar" className="card-img-top" />
                            </div>
                            {avatarUpdated
                                ? <Alert color="success">Avatar updated successfully.</Alert>
                                : avatarRequestFailedMessage.length > 0 && <Alert color="danger">{avatarRequestFailedMessage}</Alert>}
                            <Formik
                                initialValues={{file: undefined}}
                                validationSchema={Yup.object().shape({
                                    file: Yup.mixed()
                                        .required('No image file selected')
                                        .test(
                                            'fileType',
                                            `Only ${SUPPORTED_FORMATS.map((type) => type
                                                .substr(type.indexOf('/'))
                                                .replace(/\//g, '.')
                                            ).join(', ')} image files allowed`,
                                            value => value && SUPPORTED_FORMATS.includes(value.type))
                                        .test(
                                            'fileSize',
                                            `File size too large, maximum ${FILE_SIZE / 1024} kB allowed`,
                                            value => value && value.size <= FILE_SIZE)
                                })}
                                onSubmit={async (values, { setSubmitting, resetForm }) => {
                                    setAvatarUpdated(false);
                                    setAvatarRequestFailedMessage('');
                                    const data = new FormData();
                                    data.append('file', values.file);
                                    const options = {
                                        method: 'PUT',
                                        mode: 'cors',
                                        body: data,
                                        headers: {
                                            'Access-Control-Allow-Origin': '*'
                                        }
                                    };
                                    await authFetch(`${API_PATH}/users/current/avatar`, options)
                                        .then(async response => {
                                            if (response.ok) {
                                                setAvatarImage(URL.createObjectURL(values.file));
                                                setAvatarUpdated(true);
                                                setAvatarRequestFailedMessage('');
                                                resetForm(values);
                                            } else {
                                                await response.json()
                                                    .then((data) => {
                                                        if ([413, 417].indexOf(response.status) >= 0) {
                                                            setAvatarUpdated(false);
                                                            setAvatarRequestFailedMessage(data.message);
                                                        } else {
                                                            setAvatarUpdated(false);
                                                            setAvatarRequestFailedMessage('Request cannot be fulfilled now. Please try again later.');
                                                        }
                                                    })
                                            }
                                        }).catch((error) => {
                                            console.error(error);
                                            setAvatarUpdated(false);
                                            setAvatarRequestFailedMessage('Request cannot be fulfilled now. Please try again later.');
                                        }).finally(() => setSubmitting(false));
                                }}
                            >
                                {({
                                      errors,
                                      touched,
                                      isSubmitting
                                }) => (
                                    <Form>
                                        <FormGroup>
                                            <Field
                                                name="file"
                                                accept={SUPPORTED_FORMATS.join(', ')}
                                                className={'form-control' + (errors.file && touched.file ? ' is-invalid' : '')}
                                                component={BrowseImageInput} />
                                            <ErrorMessage name="file" component="div" className="invalid-feedback"/>
                                        </FormGroup>
                                        <div className="text-right">
                                            <Button color="success" type="submit" disabled={isSubmitting}>Upload</Button>
                                            {avatarImage !== avatarDefault &&
                                            <Button
                                                color="danger"
                                                type="button"
                                                onClick={() => {setAvatarUpdated(false); toggleModal()}}
                                                className="ml-2">
                                                <FontAwesomeIcon icon={faTrashAlt} />
                                            </Button>}
                                        </div>
                                        <FormikErrorFocus />
                                    </Form>
                                )}
                            </Formik>
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
                        setProfileUpdated(false);
                        setProfileRequestFailedMessage('');
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
                                    setProfileUpdated(true);
                                    setProfileRequestFailedMessage('');
                                    resetForm(values);
                                } else {
                                    await response.json()
                                        .then((data) => {
                                            if ([409, 422].indexOf(response.status) >= 0) {
                                                if (response.status === 409) {
                                                    resetForm(values);
                                                }
                                                setProfileUpdated(false);
                                                setProfileRequestFailedMessage(data.message);
                                            } else {
                                                setProfileUpdated(false);
                                                setProfileRequestFailedMessage('Request cannot be fulfilled now. Please try again later.');
                                            }
                                        })
                                }
                            }).catch((error) => {
                                console.error(error);
                                setProfileUpdated(false);
                                setProfileRequestFailedMessage('Request cannot be fulfilled now. Please try again later.');
                            }).finally(() => {
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
                            {profileUpdated
                                ? <Alert color="success">Profile updated successfully.</Alert>
                                : profileRequestFailedMessage.length > 0 && <Alert color="danger">{profileRequestFailedMessage}</Alert>}
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
                                <FormikErrorFocus />
                            </Form>
                        </Col>
                    )}
                </Formik>
            </Row>
            <ConfirmationModal
                title="Remove avatar"
                content="Are you sure you want to remove avatar?"
                confirmText="Remove"
                onConfirm={removeAvatar}
                open={modalOpen}
                toggle={toggleModal}/>
        </div>
    );
}

export default Account;
