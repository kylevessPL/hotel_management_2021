import React, {useState} from 'react';
import {Alert, Button, FormGroup, Label} from "reactstrap";
import {ErrorMessage, Field, Form, Formik} from 'formik';
import FormikErrorFocus from 'formik-error-focus'
import * as Yup from 'yup';

const SignUpForm = () => {
    const [requestFailed, setRequestFailed] = useState( false );
    const [requestFailedMessage, setRequestFailedMessage] = useState( '' );
    return (
        <Formik
            initialValues={{ username: '', password: '', repeatPassword: '', email: '' }}
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
                    .required('Email is required'),
            })}
            onSubmit={ async (values, setSubmitting) => {
                setRequestFailed(false);
                const { username, password, email } = values;
                const body = {
                    username: username,
                    password: password,
                    email: email
                };
                console.log(body);
                const options = {
                    method: 'POST',
                    mode: 'cors',
                    body: JSON.stringify(body),
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    }
                };
                const res = await fetch('api/v1/auth/signup', options)
                    .then(async response => {
                        const data = await response.json();
                        if (response.ok) {
                            console.log("Success")
                            setRequestFailed(false)
                            return response.json();
                        } else if ([409, 422].indexOf(response.status) >= 0) {
                            console.log("Failure")
                            console.log(data.message)
                            setRequestFailed(true)
                            setRequestFailedMessage(data.message)
                        } else {
                            console.log("Failure")
                            console.log(data && data.message)
                            setRequestFailed(true)
                            setRequestFailedMessage('Request cannot be proceeded now. Please try again later.')
                        }
                    }).catch(() => {
                        console.log("Failure")
                        setRequestFailed(true)
                        setRequestFailedMessage('Request cannot be proceeded now. Please try again later.')
                    }).finally(() => setSubmitting(false));
                console.log(res);
              }}
        >
            {({
                  errors,
                  touched,
                  isSubmitting,
                  requestFailed,
                  requestFailedMessage }) => (
                      <div>
                          <Alert variant="danger" style={{ display: requestFailed ? "block" : "none" }}>
                              {requestFailedMessage}
                          </Alert>
                          <Form>
                              <FormGroup>
                                  <Label for="username">Username<span style={{color: "red"}}>*</span></Label>
                                  <Field name="username"
                                         type="text"
                                         className={'form-control' + (errors.username && touched.username ? ' is-invalid' : '')}
                                  />
                                  <ErrorMessage name="username" component="div" className="invalid-feedback" />
                              </FormGroup>
                              <FormGroup>
                                  <Label for="password">Password<span style={{color: "red"}}>*</span></Label>
                                  <Field name="password"
                                         type="password"
                                         className={'form-control' + (errors.password && touched.password ? ' is-invalid' : '')}/>
                                  <ErrorMessage name="password" component="div" className="invalid-feedback" />
                              </FormGroup>
                              <FormGroup>
                                  <Label for="repeatPassword">Repeat password<span style={{color: "red"}}>*</span></Label>
                                  <Field name="repeatPassword"
                                         type="password"
                                         className={'form-control' + (errors.repeatPassword && touched.repeatPassword ? ' is-invalid' : '')}/>
                                  <ErrorMessage name="repeatPassword" component="div" className="invalid-feedback" />
                              </FormGroup>
                              <FormGroup>
                                  <Label for="email">Email<span style={{color: "red"}}>*</span></Label>
                                  <Field name="email"
                                         type="text"
                                         className={'form-control' + (errors.email && touched.email ? ' is-invalid' : '')}/>
                                  <ErrorMessage name="email" component="div" className="invalid-feedback" />
                              </FormGroup>
                              <Button color="primary" type="submit" disabled={isSubmitting}>Submit</Button>
                              <FormikErrorFocus />
                          </Form>
                      </div>
            )}
        </Formik>
    );
}

export default SignUpForm;
