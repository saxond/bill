//import React, { useState, useEffect } from "react";
import React from "react";
import {
    useParams
} from 'react-router-dom';
import EditContact from './contact.edit.tsx';

//import { getContact } from "../data.ts";

export default function UpdateContact() {
    const { id } = useParams();
    return (<EditContact id={id} />);
}