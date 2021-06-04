import React, {useContext, useEffect, useState} from 'react';
import {Alert, Button, CardTitle, FormGroup, Label} from "reactstrap";
import {ErrorMessage, Field, Form, Formik} from 'formik';
import FormikErrorFocus from 'formik-error-focus'
import * as Yup from 'yup';
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faLongArrowAltLeft} from "@fortawesome/free-solid-svg-icons"
import {authContext} from '../../../context/AuthContext';
import {API_PATH} from '../../../utils';

const SignInForm = props => {

    const {login} = useContext(authContext);

    const [registered, setRegistered] = useState(props.location.state && props.location.state.registered || false);
    const [requestFailedMessage, setRequestFailedMessage] = useState('');

    useEffect(() => {
        document.title = "HoteLA - Login";
    });

    return (
        <Formik
            initialValues={{username: '', password: ''}}
            validationSchema={Yup.object({
                username: Yup.string()
                    .required('Username is required'),
                password: Yup.string()
                    .required('Password is required')
            })}
            onSubmit={async (values, { setSubmitting }) => {
                setRequestFailedMessage('');
                setRegistered(false);
                const {username, password} = values;
                const body = {
                    username: username,
                    password: password
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
                await fetch(`${API_PATH}/auth/signin`, options)
                    .then(async response => {
                        const data = await response.json();
                        if (response.ok) {
                            setRequestFailedMessage('');
                            login(data);
                        } else if ([401, 403, 422].indexOf(response.status) >= 0) {
                            setRequestFailedMessage(data.message);
                        } else {
                            setRequestFailedMessage('Request cannot be fulfilled now. Please try again later.');
                        }
                    }).catch((error) => {
                        console.error(error);
                        setRequestFailedMessage('Request cannot be fulfilled now. Please try again later.');
                    }).finally(() => setSubmitting(false));
            }}
        >
            {({
                  errors,
                  touched,
                  isSubmitting
            }) => (
                <div>
                    <CardTitle tag="h5" className="text-center mb-4">Login</CardTitle>
                    {registered
                        ? <Alert color="success">Registration successful. You can login now.</Alert>
                        : requestFailedMessage.length > 0 && <Alert color="danger">{requestFailedMessage}</Alert>}
                    <Form>
                        <FormGroup className="mb-3">
                            <Label className="mb-1" for="username">Username</Label>
                            <Field id="username"
                                   name="username"
                                   type="text"
                                   className={'form-control' + (errors.username && touched.username ? ' is-invalid' : '')}
                            />
                            <ErrorMessage name="username" component="div" className="invalid-feedback"/>
                        </FormGroup>
                        <FormGroup className="mb-3">
                            <Label className="mb-1" for="password">Password</Label>
                            <Field id="password"
                                   name="password"
                                   type="password"
                                   className={'form-control' + (errors.password && touched.password ? ' is-invalid' : '')}/>
                            <ErrorMessage name="password" component="div" className="invalid-feedback"/>
                        </FormGroup>
                        <div className="d-flex justify-content-between mt-3">
                            <Link to="/"><FontAwesomeIcon icon={faLongArrowAltLeft} className="mr-2" />Back to HoteLA Home</Link>
                            <Link to="/signup">Sign Up</Link>
                        </div>
                        <Button color="success"
                               type="submit"
                               className="mt-2 w-100"
                               size="lg"
                               block
                               disabled={isSubmitting}>
                            Sign In
                        </Button>
                        <FormikErrorFocus />
                    </Form>
                </div>
            )}
        </Formik>
    );
}

export default SignInForm;
