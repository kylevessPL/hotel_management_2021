import React, {useContext, useEffect, useState} from 'react';
import './Users.css';
import {Button, Col, Row} from "reactstrap";
import {API_PATH} from "../../../utils";
import {authContext} from "../../../context/AuthContext";
import {ConfirmationModal, RemoteTable} from "../../utils";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faLock, faUnlock} from "@fortawesome/free-solid-svg-icons"

const Users = () => {

    const INITIAL_PAGE = 1;
    const INITIAL_SIZE_PER_PAGE = 10;
    const INITIAL_SORT_FIELD = 'id';
    const INITIAL_SORT_ORDER = 'asc';

    const {authFetch} = useContext(authContext);

    const [data, setData] = useState([]);
    const [totalSize, setTotalSize] = useState(0);
    const [loading, setLoading] = useState(false);
    const [updatedId, setUpdatedId] = useState(0);
    const [updatedStatus, setUpdatedStatus] = useState("");
    const [modalOpen, setModalOpen] = useState(false);

    const toggleModal = () => {
        modalOpen && setUpdatedId(undefined);
        setModalOpen(!modalOpen);
    }

    useEffect(() => {
        fetchData(INITIAL_PAGE, INITIAL_SIZE_PER_PAGE, INITIAL_SORT_FIELD, INITIAL_SORT_ORDER);
    }, []);

    const handleTableChange = (type, { page, sizePerPage, sortField, sortOrder }) => {
        fetchData(page, sizePerPage, sortField, sortOrder);
    }

    const fetchData = (page, sizePerPage, sortField, sortOrder) => {

        const setPageInfo = headers => {
            setTotalSize(headers.get('X-Total-Count'));
        }

        setLoading(true);

        authFetch(`${API_PATH}/users?page=${page}&size=${sizePerPage}&sortBy=${sortField}&sortDir=${sortOrder.toUpperCase()}`, {
            method: "GET",
            mode: "cors",
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Expose-Headers': 'X-Total-Count'
            }
        }).then(async (response) => {
            if (response.ok) {
                await response.json().then(data => {
                    setPageInfo(response.headers);
                    setData(data);
                });
            } else {
                throw new Error('Update account status failure');
            }
        }).catch((error) => console.log(error)
        ).finally(() => setLoading(false));
    }

    const updateAccountStatus = () => {
        authFetch(`${API_PATH}/users/${updatedId}/status`, {
            method: "PUT",
            mode: "cors",
            body: JSON.stringify({ status: updatedStatus }),
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        }).then(async (response) => {
            if (response.ok) {
                const idx = data.findIndex(el => el.id === updatedId);
                let newData = [...data];
                newData[idx] = {...newData[idx], status: updatedStatus}
                setData(newData);
            } else {
                throw new Error('Update account status failure');
            }
        }).catch((error) => console.log(error)
        ).finally(() => setUpdatedId(undefined));
    }

    const columns = [{
        dataField: 'id',
        text: '#',
        sort: true,
        align: 'center',
        headerAlign: 'center',
        style: {verticalAlign: 'middle'}
    }, {
        dataField: 'username',
        text: 'Username',
        sort: true,
        style: {verticalAlign: 'middle'}
    }, {
        dataField: 'email',
        text: 'Email',
        sort: true,
        style: {verticalAlign: 'middle'}
    }, {
        dataField: 'roles',
        text: 'Roles',
        style: {verticalAlign: 'middle'},
        formatter: cell => <>{cell.map(role => <li>{role}</li>)}</>
    }, {
        dataField: 'status',
        text: 'Account status',
        sort: true,
        align: 'center',
        headerAlign: 'center',
        style: {verticalAlign: 'middle'}
    }, {
        dataField: 'action',
        isDummyField: true,
        text: 'Action',
        align: 'center',
        headerAlign: 'center',
        style: {verticalAlign: 'middle'},
        formatter: (cell, row) => {
            return <Button
                color={row.status === 'ACTIVE' ? 'danger' : 'success'}
                disabled={updatedId === row.id || row.username === window.localStorage.getItem('dotcom_user')}
                onClick={() => {
                    setUpdatedId(row.id);
                    setUpdatedStatus(row.status === 'ACTIVE' ? 'DISABLED' : 'ACTIVE');
                    toggleModal();
                }}>
                <FontAwesomeIcon icon={row.status === 'ACTIVE' ? faLock : faUnlock} className="mr-2" />
                {row.status === 'ACTIVE' ? 'Disable' : 'Enable'}
            </Button>
        }
    }];

    return (
        <div>
            <h4 className="mb-4">Users</h4>
            <Row>
                <Col xs={12} className="mb-lg-0">
                    <RemoteTable
                        data={data}
                        columns={columns}
                        totalSize={totalSize}
                        loading={loading}
                        noDataText="No users in database"
                        onTableChange={handleTableChange}/>
                </Col>
            </Row>
            <ConfirmationModal
                title={(updatedStatus === 'DISABLED' ? 'Disable' : 'Enable') + ' account'}
                content={'Are you sure you want to ' + (updatedStatus === 'DISABLED' ? 'disable' : 'enable') + ' this account?'}
                confirmText={updatedStatus === 'DISABLED' ? 'Disable' : 'Enable'}
                onConfirm={updateAccountStatus}
                open={modalOpen}
                toggle={toggleModal} />
        </div>
    );
}

export default Users;
