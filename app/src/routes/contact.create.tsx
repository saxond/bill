//import React, { useState, useEffect } from "react";
import React from "react";
//import { getContact } from "../data.ts";
import EditContact from './contact.edit.tsx';
//import type { ContactRecord } from "../data.ts";

export default function CreateContact() {
    const contact = {
        first: "",
        last: "",
        avatar: "",
        twitter: "",
        notes: "",
      };
    console.log(contact);
    //return (<EditContact contact={contact} />);
    return (<EditContact contact={null} />);
}