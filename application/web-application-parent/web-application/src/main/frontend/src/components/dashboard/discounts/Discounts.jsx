import React, {useContext, useEffect, useState} from 'react';
import {authContext} from '../../../context/AuthContext';
import './Discounts.css';
import {
    Alert,
    Badge,
    Button,
    Card,
    CardBody,
    CardHeader,
    Col,
    FormGroup,
    Label,
    ListGroup,
    ListGroupItem,
    Row
} from "reactstrap";
import {ErrorMessage, Field, Form, Formik} from "formik";
import * as Yup from "yup";
import {API_PATH} from "../../../utils";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlusCircle, faTags, faTrashAlt} from "@fortawesome/free-solid-svg-icons";
import FormikErrorFocus from "formik-error-focus";
import {ConfirmationModal} from "../../utils";

const Discounts = () => {

    const {authFetch} = useContext(authContext);

    const [data, setData] = useState([]);
    const [removedCode, setRemovedCode] = useState(undefined);
    const [discountAdded, setDiscountAdded] = useState(false);
    const [discountAddRequestFailedMessage, setDiscountAddRequestFailedMessage] = useState('');
    const [modalOpen, setModalOpen] = useState(false);

    const toggleModal = () => {
        modalOpen && setRemovedCode(undefined);
        setModalOpen(!modalOpen);
    }

    useEffect(() => {
        authFetch(`${API_PATH}/discounts`, {
            method: "GET",
            mode: "cors",
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        }).then((response) => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Discounts data fetch failure');
            }
        }).then((data) => setData(data)
        ).catch((error) => console.log(error));
    }, []);

    const removeDiscount = () => {
        authFetch(`${API_PATH}/discounts/?code=${removedCode}`, {
            method: "DELETE",
            mode: "cors",
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        }).then(async (response) => {
            if (response.ok) {
                let newData = [...data];
                const idx = data.findIndex(el => el.code === removedCode);
                newData = newData.slice(0, idx).concat(newData.slice(idx + 1, newData.length))
                setData(newData);
            } else {
                throw new Error('Remove discount request failure');
            }
        }).catch((error) => console.log(error)
        ).finally(() => setRemovedCode(undefined));
    }

    return (
        <div>
            <h4 className="mb-4">Discounts</h4>
            <Row>
                <Col lg={4} className="pb-5">
                    <Card className="h-100">
                        <CardHeader className="d-flex align-items-center">
                            <FontAwesomeIcon icon={faTags} size="2x" className="mr-3" /><h5 className="mb-0">Available discounts</h5>
                        </CardHeader>
                        <CardBody className="d-inline-block">
                            {!data || data.length === 0
                                ? <div className="text-center">No discounts in database</div>
                                : <ListGroup>
                                    {data.map((object, i) =>
                                        <ListGroupItem key={i} className="d-flex justify-content-between align-items-center">
                                            <div>
                                                {object.code}<Badge pill color="success" className="ml-3">{object.value}%</Badge>
                                            </div>
                                            <div>
                                                <Button
                                                    color="danger"
                                                    disabled={removedCode === object.code}
                                                    onClick={() => {
                                                        setRemovedCode(object.code);
                                                        setDiscountAdded(false);
                                                        toggleModal();
                                                    }}>
                                                    <FontAwesomeIcon icon={faTrashAlt} />
                                                </Button>
                                            </div>
                                        </ListGroupItem>
                                    )}
                                </ListGroup>}
                        </CardBody>
                    </Card>
                </Col>
                <Formik
                    initialValues={{code: '', value: 1}}
                    validationSchema={Yup.object({
                        code: Yup.string()
                            .min(2, 'Discount code must be at least 2 characters long')
                            .max(20, 'Discount code must be maximum 20 characters long')
                            .matches('^[a-zA-Z0-9]*$', "Discount code must contain only digits and numbers")
                            .required('Discount code is required')
                            .test('discountUnique', 'Discount already exists', value =>
                                !data.some(e => e.code === value)),
                        value: Yup.number()
                            .moreThan(0, 'Discount value must be more than 0')
                            .max(100, 'Discount value cannot be more than 100')
                            .integer('Discount value must be a positive integer')
                            .required('Discount value is required')
                    })}
                    onSubmit={async (values, { setSubmitting, resetForm }) => {
                        setDiscountAdded(false);
                        setDiscountAddRequestFailedMessage('');
                        const {code, value} = values;
                        const body = {
                            code: code,
                            value: value
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
                        await authFetch(`${API_PATH}/discounts`, options)
                            .then(async response => {
                                if (response.ok) {
                                    setData([...data, {
                                        code: code,
                                        value: value
                                    }]);
                                    setDiscountAdded(true);
                                    setDiscountAddRequestFailedMessage('');
                                    resetForm(values);
                                } else {
                                    await response.json()
                                        .then((data) => {
                                            if ([409, 422].indexOf(response.status) >= 0) {
                                                throw new Error(data.message);
                                            } else {
                                                throw new Error('Request cannot be fulfilled now. Please try again later.');
                                            }
                                        })
                                }
                            }).catch((error) => {
                                console.error(error);
                                setDiscountAdded(false);
                                setDiscountAddRequestFailedMessage(error.message);
                            }).finally(() => setSubmitting(false));
                    }}
                >
                    {({
                          errors,
                          touched,
                          isSubmitting
                    }) => (
                        <Col lg={8} className="pb-5">
                            <h5 className="mb-4">Add new discount</h5>
                            {discountAdded
                                ? <Alert color="success">Discount added successfully.</Alert>
                                : discountAddRequestFailedMessage.length > 0 && <Alert color="danger">{discountAddRequestFailedMessage}</Alert>}
                            <Form noValidate>
                                <Row form>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label for="code">Code</Label>
                                            <Field id="code"
                                                   name="code"
                                                   type="text"
                                                   className={'form-control' + (errors.code && touched.code ? ' is-invalid' : '')} />
                                            <ErrorMessage name="code" component="div" className="invalid-feedback"/>
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label for="value">Value</Label>
                                            <Field id="value"
                                                   name="value"
                                                   type="number"
                                                   min="1"
                                                   max="100"
                                                   className={'form-control' + (errors.value && touched.value ? ' is-invalid' : '')} />
                                            <ErrorMessage name="value" component="div" className="invalid-feedback"/>
                                        </FormGroup>
                                    </Col>
                                    <Col xs={12}>
                                        <hr className="mt-2 mb-3" />
                                        <div className="d-flex flex-wrap justify-content-between align-items-center">
                                            <Button outline color="success" type="submit" disabled={isSubmitting}>
                                                <FontAwesomeIcon icon={faPlusCircle} className="mr-2" /> Add discount
                                            </Button>
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
                title="Remove discount"
                content="Are you sure you want to remove discount?"
                confirmText="Remove"
                onConfirm={removeDiscount}
                open={modalOpen}
                toggle={toggleModal}/>
        </div>
    );
}

export default Discounts;
