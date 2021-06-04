import React, {useState} from 'react';
import {Card, CardBody, CardHeader, Collapse} from "reactstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus} from "@fortawesome/free-solid-svg-icons"

const Accordion = ({question, answer}) => {

    const [open, setOpen] = useState(false);

    const toggle = () => setOpen(!open);

    return (
        <Card>
            <CardHeader onClick={toggle} className="pl-2">
                <h6><FontAwesomeIcon icon={faPlus} size="sm" className="mr-2" />{question}</h6>
            </CardHeader>
            <Collapse isOpen={open}>
                <CardBody>
                    <p>{answer}</p>
                </CardBody>
            </Collapse>
        </Card>
    );
}

export default Accordion;
