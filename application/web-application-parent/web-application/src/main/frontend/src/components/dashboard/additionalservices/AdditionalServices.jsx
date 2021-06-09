import React, {useEffect, useState} from 'react';
import './AdditionalServices.css'
import {Button, Card, CardBody, CardHeader, CardTitle, Col, Row, Table} from "reactstrap";
import {API_PATH} from "../../../utils";
import ServiceDescriptionModal from "./utils"

const AdditionalServices = () => {

    const [data, setData] = useState([]);
    const [modalOpen, setModalOpen] = useState(false);
    const [modalName, setModalName] = useState('');
    const [modalPrice, setModalPrice] = useState('');
    const [modalDescription, setModalDescription] = useState('');

    const toggleModal = () => setModalOpen(!modalOpen);

    useEffect(() => {
        fetch(`${API_PATH}/additional-services`, {
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
                throw new Error('Additional services data fetch failure');
            }
        }).then((data) => setData(data)
        ).catch((error) => console.log(error));
    }, []);

    return (
        <div>
            <h4 className="mb-4">Additional services</h4>
            <Row>
                <Col xs={12} xl={7} className="mb-lg-0">
                    <Card>
                        <CardHeader>
                            <h6>We offer additional services for a reasonable price</h6>
                        </CardHeader>
                        <CardBody>
                            <CardTitle>All prices are in PLN</CardTitle>
                            <Table bordered>
                                <thead>
                                <tr className="text-center">
                                    <th scope="col">#</th>
                                    <th scope="col">Service name</th>
                                    <th scope="col">Price per night</th>
                                    <th scope="col">Description</th>
                                </tr>
                                </thead>
                                <tbody>
                                    {data &&
                                    data.map((object, i) =>
                                        <tr key={i} className="text-center">
                                            <th className="service-id align-middle" scope="row">{i + 1}</th>
                                            <td className="service-name align-middle">{object.name}</td>
                                            <td className="service-price align-middle">{object.price}</td>
                                            <td className="align-middle">
                                                <Button
                                                    color="primary"
                                                    onClick={() => {
                                                        setModalName(object.name);
                                                        setModalPrice(object.price);
                                                        setModalDescription(object.description);
                                                        toggleModal();
                                                    }}
                                                    className="py-1 px-2">View</Button>
                                            </td>
                                        </tr>
                                    )}
                                </tbody>
                            </Table>
                        </CardBody>
                    </Card>
                </Col>
            </Row>
            <ServiceDescriptionModal
                name={modalName}
                price={modalPrice}
                description={modalDescription}
                open={modalOpen}
                toggle={toggleModal} />
        </div>
    );
}

export default AdditionalServices;