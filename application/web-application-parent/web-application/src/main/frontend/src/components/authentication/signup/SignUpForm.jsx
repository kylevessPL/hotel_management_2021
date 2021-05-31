import React, {useEffect, useState} from 'react';
import {Alert, Button, CardTitle, FormGroup, Label} from "reactstrap";
import {ErrorMessage, Field, Form, Formik} from 'formik';
import FormikErrorFocus from 'formik-error-focus'
import * as Yup from 'yup';
import {Link} from "react-router-dom";
import PasswordStrengthBar from "react-password-strength-bar";

const SignUpForm = props => {

    const [requestFailed, setRequestFailed] = useState(false);
    const [requestFailedMessage, setRequestFailedMessage] = useState('');

    useEffect(() => {
        document.title = "HoteLA - Login";
    });

    const redirectToSignIn = () => {
        props.history.push({
            pathname: '/signin',
            state: { registered: true }
        })
    }

    return (
        <Formik
            initialValues={{username: '', password: '', repeatPassword: '', email: ''}}
            validationSchema={Yup.object({
                username: Yup.string()
                    .min(3, 'Username must be at least 3 characters long')
                    .max(20, 'Username must be maximum 20 characters long')
                    .required('Username is required'),
                password: Yup.string()
                    .min(3, 'Password must be at least 6 characters long')
                    .max(120, 'Password must be maximum 120 characters long')
                    .matches(
                        '^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).+$',
                        'Password must contain at least one lowercase letter, one uppercase letter, one number and one special character')
                    .required('Password is required'),
                repeatPassword: Yup.string()
                    .required('Repeat password is required')
                    .oneOf([Yup.ref('password'), null], 'Passwords must match'),
                email: Yup.string()
                    .email('Invalid email address')
                    .max(50, 'Email must be maximum 50 characters long')
                    .required('Email is required')
            })}
            onSubmit={async (values, { setSubmitting }) => {
                setRequestFailed(false);
                const {username, password, email} = values;
                const body = {
                    username: username,
                    password: password,
                    email: email
                };
                const options = {
                    method: 'POST',
                    mode: 'cors',
                    body: JSON.stringify(body),
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    }
                };
                await fetch('api/v1/auth/signup', options)
                    .then(async response => {
                        if (response.ok) {
                            setRequestFailed(false)
                            redirectToSignIn()
                        } else {
                            await response.json()
                                .then((data) => {
                                    if ([409, 422].indexOf(response.status) >= 0) {
                                        setRequestFailed(true)
                                        setRequestFailedMessage(data.message)
                                    } else {
                                        setRequestFailed(true)
                                        setRequestFailedMessage('Request cannot be fulfilled now. Please try again later.')
                                    }
                                })
                        }
                    }).catch((error) => {
                        console.error(error)
                        setRequestFailed(true)
                        setRequestFailedMessage('Request cannot be fulfilled now. Please try again later.')
                    }).finally(() => setSubmitting(false));
            }}
        >
            {({
                  values,
                  errors,
                  touched,
                  isSubmitting
              }) => (
                <div>
                    <CardTitle tag="h5" className="text-center mb-4">Register</CardTitle>
                    {requestFailed && <Alert color="danger">{requestFailedMessage}</Alert>}
                    <Form>
                        <FormGroup className="mb-3">
                            <Label className="mb-1" for="username">Username<span style={{color: "red"}}>*</span></Label>
                            <Field id="username"
                                   name="username"
                                   type="text"
                                   className={'form-control' + (errors.username && touched.username ? ' is-invalid' : '')}
                            />
                            <ErrorMessage name="username" component="div" className="invalid-feedback"/>
                        </FormGroup>
                        <FormGroup className="mb-3">
                            <Label className="mb-2" for="password">Password<span style={{color: "red"}}>*</span></Label>
                            <Field id="password"
                                   name="password"
                                   type="password"
                                   className={'form-control' + (errors.password && touched.password ? ' is-invalid' : '')}/>
                            <PasswordStrengthBar className="mt-2" minLength={3} password={values.password} />
                            <ErrorMessage name="password" component="div" className="invalid-feedback"/>
                        </FormGroup>
                        <FormGroup className="mb-3">
                            <Label className="mb-1" for="repeatPassword">Repeat password<span style={{color: "red"}}>*</span></Label>
                            <Field id="repeatPassword"
                                   name="repeatPassword"
                                   type="password"
                                   className={'form-control' + (errors.repeatPassword && touched.repeatPassword ? ' is-invalid' : '')}/>
                            <ErrorMessage name="repeatPassword" component="div" className="invalid-feedback"/>
                        </FormGroup>
                        <FormGroup className="mb-3">
                            <Label className="mb-1" for="email">Email<span style={{color: "red"}}>*</span></Label>
                            <Field id="email"
                                   name="email"
                                   type="text"
                                   className={'form-control' + (errors.email && touched.email ? ' is-invalid' : '')}/>
                            <ErrorMessage name="email" component="div" className="invalid-feedback"/>
                        </FormGroup>
                        <div className="d-flex justify-content-end mt-3">
                            <Link to="/signin">Sign In</Link>
                        </div>
                        <Button color="primary"
                               type="submit"
                               className="mt-2 w-100"
                               size="lg"
                               block
                               disabled={isSubmitting}>
                            Sign Up
                        </Button>
                        <FormikErrorFocus />
                    </Form>
                </div>
            )}
        </Formik>
    );
}

export default SignUpForm;
