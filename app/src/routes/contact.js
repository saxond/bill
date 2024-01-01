import React from "react";
import PropTypes from 'prop-types';

export default function Contact({id}) {
    return (<div>Contact {id}</div>);
}

Contact.propTypes = {
    id: PropTypes.string
};