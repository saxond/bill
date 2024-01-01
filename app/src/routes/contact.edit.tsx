//import React, { useState, useEffect } from "react";
import React from "react";

//import type { ContactRecord } from "../data";

interface Props {
    id?: string;
}

export default function EditContact(props: Props) {
    const {id} = props;
    const contact = {};
    console.log("Edit", id);
    return (
        <form id="contact-form" method="post">
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
            <button type="submit">Save</button>
            <button type="button">Cancel</button>
          </p>
        </form>
      );
    }