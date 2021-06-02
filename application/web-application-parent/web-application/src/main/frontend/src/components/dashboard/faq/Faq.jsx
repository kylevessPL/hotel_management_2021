import React, {useEffect, useState} from 'react';
import './Faq.css';
import {Col, Container, Row} from "reactstrap";
import {Accordion, data} from './utils';
import {API_PATH} from '../../../utils';

const Faq = () => {

    const [paymentFormsData, setPaymentFormsData] = useState([]);

    useEffect(() => {
        fetch(`${API_PATH}/payment-forms`, {
            method: "GET",
            mode: "cors",
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        }).then(r => r.ok && setPaymentFormsData(r.json()));
    }, []);

    return (
        <div>
            <p>Frequently asked questions</p>
            <Row>
                <Col xs={12} xl={7} className="mb-lg-0">
                    <Container>
                        {data.map((object, i) =>
                            <Accordion key={i} question={object.question} answer={object.answer} />
                        )}
                        <Accordion question="What are the available payment forms?" answer={
                            <ul className="list-unstyled">
                                <li>Currently payment forms listed below are supported:
                                    <ul>
                                        {paymentFormsData &&
                                        paymentFormsData.map((object, i) => <li key={i}>{object.name}</li>)}
                                    </ul>
                                </li>
                            </ul>
                        } />
                    </Container>
                </Col>
            </Row>
        </div>
    );
}

export default Faq;
