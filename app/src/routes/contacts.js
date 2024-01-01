import React, { useState, useEffect } from "react";
import {
    Link,
    useParams
} from 'react-router-dom';
import PropTypes from 'prop-types';
import { getContacts } from "../data.ts";
//import Contact from './contact';

export default function Contacts() {
    const [loading, setLoading] = useState(false);
    const [contacts, setContacts] = useState([]);

    const { id } = useParams();

    useEffect(() => {
        setLoading(true);
        const loadPost = async () => {
            try {
                setContacts(await getContacts());
            } finally {
                setLoading(false);
            }
        };
        loadPost();
    }, []);

    if (loading) return <div>Loading</div>;

    return (
        <div className="flex-column">
            <div>
                <h1>Contacts</h1>
                <nav>
                    {contacts && contacts.length ? (
                        <ul>
                            {contacts.map((contact) => (
                                <li key={contact.id}>
                                    <Link to={`/contacts/${contact.id}`}>
                                        {contact.first || contact.last ? (
                                            <>
                                                {contact.first} {contact.last}
                                            </>
                                        ) : (
                                            <i>No Name</i>
                                        )}{" "}
                                        {contact.favorite ? (
                                            <span>★</span>
                                        ) : null}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>
                            <i>No contacts</i>
                        </p>
                    )}
                </nav>
            </div>
            <div className="detail">
                { id ? <>{id}</> : <></> }
            </div>
        </div>
    );
}

Contacts.propTypes = {
    id: PropTypes.string
};