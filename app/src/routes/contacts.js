import React, { useState, useEffect } from "react";
import {
    Link,
    useParams
} from 'react-router-dom';
import { getContacts } from "../data.ts";
import Contact from './contact.js';

const newContactId = "create";

function showDetail(id) {
    if (id === newContactId) {
        return (<>New</>);
    } else if (id) {
        return (<Contact id={id}/>);
    }
    return (<></>);
}

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
            <div className="flex-row">
                <form action={`/contacts/${newContactId}`}>
                    <button>New</button>
                </form>
                <nav>
                    {contacts?.length ? (
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
                <div className="detail">
                    {showDetail(id)}
                </div>
            </div>
    );
}