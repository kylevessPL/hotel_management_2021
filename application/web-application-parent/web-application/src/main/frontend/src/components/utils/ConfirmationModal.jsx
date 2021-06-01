import React from 'react';
import {Alert, Button, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";

const ConfirmationModal = ({title, content, confirmText, onConfirm, open, toggle}) => {

    return (
        <Modal centered fade isOpen={open} toggle={toggle}>
            <ModalHeader toggle={toggle}>{title}</ModalHeader>
            <ModalBody>
                <Alert color="danger">{content}</Alert>
            </ModalBody>
            <ModalFooter>
                <Button color="secondary" onClick={onConfirm}>{confirmText}</Button>
                <Button color="danger" onClick={toggle}>Cancel</Button>
            </ModalFooter>
        </Modal>
    );
}

export default ConfirmationModal;
