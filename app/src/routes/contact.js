import React, { useState, useEffect } from "react";
import PropTypes from 'prop-types';
import { getContact } from "../data.ts";

export default function Contact({id}) {
    const [loading, setLoading] = useState(false);
    const [contact, setContact] = useState(null);

    useEffect(() => {
        setLoading(true);
        const loadContact = async () => {
            try {
                const contact = await getContact(id);
                setContact(contact);
            } finally {
                setLoading(false);
            }
        };
        loadContact();
    }, [id]);

    if (loading || !contact) return (<div>Loading</div>);
    return (
        <div id="contact">
            <div>
            <img
                alt={`${contact.first} ${contact.last} avatar`}
                key={contact.avatar}
                src={contact.avatar}
            />
            </div>

            <div>
            <h1>
                {contact.first || contact.last ? (
                <>
                    {contact.first} {contact.last}
                </>
                ) : (
                <i>No Name</i>
                )}{" "}
                
            </h1>

            {contact.twitter ? (
                <p>
                <a
                    href={`https://twitter.com/${contact.twitter}`}
                >
                    {contact.twitter}
                </a>
                </p>
            ) : null}

            {contact.notes ? <p>{contact.notes}</p> : null}

            </div>
        </div>
        );
    }


Contact.propTypes = {
    id: PropTypes.string
};