import React, { useState, useEffect } from "react";
import {
    Link
} from 'react-router-dom';
import { getContacts } from "../data.ts";
import axios from "axios";

export default function Contacts() {
    const [loading, setLoading] = useState(false);
    const [contacts, setContacts] = useState([]);
    const serverHostPort = "localhost:8080";

    useEffect(() => {
        const loadPost = async () => {
            // Till the data is fetch using API
            // the Loading page will show.
            setLoading(true);

            const response = await getContacts();

            // After fetching data stored it in posts state.
            setContacts(response);

            // Closed the loading page
            setLoading(false);
        };
        // Call the function
        loadPost();
    }, []);

    if (loading) return <div>Loading</div>;

    return (
        <div id="sidebar">
            <h1>Contacts</h1>
            <nav>
                {contacts.length ? (
                    <ul>
                        {contacts.map((contact) => (
                            <li key={contact.id}>
                                <Link to={`contacts/${contact.id}`}>
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
    );
}