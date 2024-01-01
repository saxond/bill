//import React, { useState, useEffect } from "react";
import React from "react";

//import type { ContactRecord } from "../data";

interface Props {
    id?: string;
    contact: unknown // Record<string, ContactRecord>; 
}

export default function EditContact(props: Props) {
    const {id, contact} = props;
    console.log("Edit", id, contact);
    return (<>Edit {id} {contact}</>);
}