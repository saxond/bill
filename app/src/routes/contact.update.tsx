//import React, { useState, useEffect } from "react";
import React from "react";
import {
    useParams
} from 'react-router-dom';

//import { getContact } from "../data.ts";

export default function UpdateContact() {
    const { id } = useParams();
    return (<>{id}</>);
}