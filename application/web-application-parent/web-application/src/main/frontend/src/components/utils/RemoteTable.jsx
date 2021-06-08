import './RemoteTable.css'
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css'
import paginationFactory, {
    PaginationListStandalone,
    PaginationProvider,
    PaginationTotalStandalone,
    SizePerPageDropdownStandalone
} from "react-bootstrap-table2-paginator";
import BootstrapTable from "react-bootstrap-table-next";
import overlayFactory from "react-bootstrap-table2-overlay";
import {Spinner} from "reactstrap";
import React from "react";

const RemoteTable = ({data, columns, totalSize, loading, noDataText, onTableChange}) => {
    return (
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
                            defaultSorted={[{
                                dataField: 'id',
                                order: 'asc'
                            }]}
                            onTableChange={onTableChange}
                            noDataIndication={() => <span>{!loading ? noDataText : ''}</span>}
                            loading={loading}
                            overlay={overlayFactory({
                                spinner: <Spinner color="primary" style={{borderWidth: '0.15em'}} />,
                                styles: { overlay: (base) => ({...base, background: 'transparent'}) }
                            })}
                            wrapperClasses="table-responsive"
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
    );
}

export default RemoteTable;
