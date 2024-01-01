import React, { useState, useEffect } from "react";

import { getContact, getServerHostPort } from "../data";

interface Props {
    id?: string;
}

export default function EditContact(props: Props) {
    const [loading, setLoading] = useState(false);
    const [contact, setContact] = useState({});
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

    const onSubmit = (e) => {
        e.preventDefault();

        const data = Array.from(e.target.elements)
          .filter((input) => input.name)
          .reduce((obj, input) => Object.assign(obj, { [input.name]: input.value }), {});

          console.log("Update", data);

    const url = id ? `/contacts/${id}` : `/contacts`;
    const fullUrl = `http://${getServerHostPort()}${url}`;
    const method = id ? `PUT` : `POST`;
    fetch(fullUrl, {
        method: method,
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      })
        .then((response) => {
          if (response.status !== 200) {
            throw new Error(response.statusText);
          }
  
          return response.json();
        })
        .then(() => {
          //setMessage("We'll be in touch soon.");
          //setStatus('success');
        })
        .catch((err) => {
          console.log("Error", err);
          //setMessage(err.toString());
          //setStatus('error');
        });

        //this.setState({description: ''});
    };

    if (loading) return (<div>Loading</div>);

    console.log("Edit", id);
    return (
        <form id="contact-form" method="post" onSubmit={onSubmit}>
          <p>
            <span>Name</span>
            <input
              defaultValue={contact.first}
              aria-label="First name"
              name="first"
              type="text"
              placeholder="First"
            />
            <input
              aria-label="Last name"
              defaultValue={contact.last}
              name="last"
              placeholder="Last"
              type="text"
            />
          </p>
          <label>
            <span>Twitter</span>
            <input
              defaultValue={contact.twitter}
              name="twitter"
              placeholder="@jack"
              type="text"
            />
          </label>
          <label>
            <span>Avatar URL</span>
            <input
              aria-label="Avatar URL"
              defaultValue={contact.avatar}
              name="avatar"
              placeholder="https://example.com/avatar.jpg"
              type="text"
            />
          </label>
          <label>
            <span>Notes</span>
            <textarea
              defaultValue={contact.notes}
              name="notes"
              rows={6}
            />
          </label>
          <p>
            <button type="submit">{id ? `Save` : `Create`}</button>
            <button type="button">Cancel</button>
          </p>
        </form>
      );
    }