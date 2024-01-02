import React, { useState, useEffect } from "react";

import {
  Link
} from 'react-router-dom';

import { getContact, updateContact, createContact } from "../data";

interface Props {
  id?: string;
}

export default function EditContact(props: Props) {
  const [loading, setLoading] = useState(false);
  const [contact, setContact] = useState({});
  const [updated, setUpdated] = useState(false);
  const { id } = props;

  useEffect(() => {
    if (id) {
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
    }
  }, [id]);

  const onSubmit = async (e) => {
    e.preventDefault();
    const data = Array.from(e.target.elements)
      .filter((input) => input.name)
      .reduce((obj, input) => Object.assign(obj, { [input.name]: input.value }), {});

    let updatedContact;
    if (contact?.id) {
      updatedContact = await updateContact(contact?.id, data);
    } else {
      updatedContact = await createContact(data);
    }
    if (updatedContact) {
      setContact(updatedContact);
      setUpdated(true);
    }
  };

  if (loading) return (<div>Loading</div>);

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
        <button type="submit">{contact?.id ? `Save` : `Create`}</button>
        <button type="button">Cancel</button>
      </p>
      { updated && <Link to={`/contacts/${contact.id}`}>Updated</Link> }
    </form>
  );
}