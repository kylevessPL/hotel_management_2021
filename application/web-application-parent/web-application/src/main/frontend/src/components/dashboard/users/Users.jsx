import React, {useContext, useEffect, useState} from 'react';
import './Users.css';
import {Button, Col, Row, Spinner} from "reactstrap";
import {API_PATH} from "../../../utils";
import {authContext} from "../../../context/AuthContext";
import BootstrapTable from 'react-bootstrap-table-next';
import paginationFactory, {
    PaginationListStandalone,
    PaginationProvider,
    PaginationTotalStandalone,
    SizePerPageDropdownStandalone
} from 'react-bootstrap-table2-paginator';
import overlayFactory from 'react-bootstrap-table2-overlay';
import {ConfirmationModal} from "../../utils";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faLock, faUnlock} from "@fortawesome/free-solid-svg-icons"

const Users = () => {

    const INITIAL_PAGE = 1;
    const INITIAL_SIZE_PER_PAGE = 10;

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
        fetchData(INITIAL_PAGE, INITIAL_SIZE_PER_PAGE)
    }, []);

    const handleTableChange = (type, { page, sizePerPage }) => {
        fetchData(page, sizePerPage);
    }

    const fetchData = (page, sizePerPage) => {

        const setPageInfo = headers => {
            setTotalSize(headers.get('X-Total-Count'));
        }

        setLoading(true);

        authFetch(`${API_PATH}/users?page=${page}&size=${sizePerPage}`, {
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
                newData[idx] = {...newData[idx], status: updatedStatus === 'ACTIVE' ? 'DISABLED' : 'ACTIVE'}
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
                <Col xs={12} xl={9} className="mb-lg-0">
                    <PaginationProvider
                        pagination={paginationFactory({
                            custom: true,
                            totalSize: totalSize,
                            alwaysShowAllBtns: true,
                            showTotal: true
                        })}
                    >
                        {
                            ({
                                 paginationProps,
                                 paginationTableProps
                            }) => (
                                <>
                                    <BootstrapTable
                                        { ...paginationTableProps }
                                        remote
                                        bootstrap4
                                        keyField="id"
                                        data={data}
                                        columns={columns}
                                        onTableChange={handleTableChange}
                                        noDataIndication={() => <span>{!loading ? 'No users in database' : ''}</span>}
                                        loading={loading}
                                        overlay={overlayFactory({
                                            spinner: <Spinner color="primary" style={{borderWidth: '0.15em'}} />,
                                            background: 'transparent',
                                        })}
                                    />
                                    <div className="d-flex justify-content-between">
                                        <div>
                                            <SizePerPageDropdownStandalone
                                                { ...paginationProps }
                                                variation="dropup"
                                                className="mr-1"
                                            />
                                            <PaginationTotalStandalone
                                                { ...paginationProps }
                                            />
                                        </div>
                                        <div>
                                            <PaginationListStandalone
                                                { ...paginationProps }
                                            />
                                        </div>
                                    </div>
                                </>
                            )
                        }
                    </PaginationProvider>
                </Col>
            </Row>
            <ConfirmationModal
                title="Disable account"
                content="Are you sure you want to disable this account?"
                confirmText={updateAccountStatus === 'DISABLED' ? 'Disable' : 'Enable'}
                onConfirm={updateAccountStatus}
                open={modalOpen}
                toggle={toggleModal} />
        </div>
    );
}

export default Users;
