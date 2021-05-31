import React from 'react';
import './Breadcrumb.css';

const Breadcrumb = props => {

    return (
        <div className="breadcrumb">
            {props.children}
        </div>
    );
}

export default Breadcrumb;
