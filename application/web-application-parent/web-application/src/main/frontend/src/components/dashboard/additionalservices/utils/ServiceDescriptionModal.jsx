import React from 'react';
import {Button, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";

const ServiceDescriptionModal = ({name, price, description, open, toggle}) => {

    return (
        <Modal centered fade isOpen={open} toggle={toggle}>
            <ModalHeader toggle={toggle}>{name}</ModalHeader>
            <ModalBody>
                <div className="mb-3">Price: {price} PLN</div>
                <div>{description}</div>
            </ModalBody>
            <ModalFooter>
                <Button color="secondary" onClick={toggle}>Close</Button>
            </ModalFooter>
        </Modal>
    );
}

export default ServiceDescriptionModal;
