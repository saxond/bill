import React, { useState, useEffect } from "react";
import {
    Link
  } from 'react-router-dom';
import { getContact } from "../data.ts";
import type { ContactRecord } from "../data.ts";

interface Props {
    id: string;
}

export default function Contact(props: Props) {
    const [loading, setLoading] = useState(false);
    const [contact, setContact] = useState<ContactRecord>(null);
    const {id} = props;

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
            <Link to={`/contacts/update/${id}`}><button>Edit</button></Link>
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