////////////////////////////////////////////////////////////////////////////////
// Crud methods that call the backend service
////////////////////////////////////////////////////////////////////////////////

//import { matchSorter } from "match-sorter";
// @@ts-expect-error - no types, but it's a tiny function
//import sortBy from "sort-by";
import invariant from "tiny-invariant";
import { getUser } from "./user";

type ContactMutation = {
  id?: string;
  first?: string;
  last?: string;
  avatar?: string;
  twitter?: string;
  notes?: string;
  favorite?: boolean;
};

export type ContactRecord = ContactMutation & {
  id: string;
  createdAt: string;
};

const serverHostPort = process.env.SERVER_PORT || "localhost:8080";
const HEADERS = {
  Accept: 'application/json',
  'Content-Type': 'application/json',
};

////////////////////////////////////////////////////////////////////////////////
// Handful of helper functions to be called from route loaders and actions

function getServerUrl(path: string) {
  return `http://${serverHostPort}${path}`
}

function getHeaders() {
  const user = getUser();
  return {...HEADERS, 'Access-Token': user?.access_token};
}

async function getAll(): Promise<ContactRecord[]> {
  try {
    const response = await fetch(getServerUrl('/contacts/'), {
      headers: getHeaders()
    });
    return await response.json();
   } catch(error) {
    console.error(error);
    return [];
  }
}

export async function getContacts(query?: string | null) {
  await new Promise((resolve) => setTimeout(resolve, 500));
  const contacts = await getAll();
  if (query) {
    /*
    contacts = matchSorter(contacts, query, {
      keys: ["first", "last"],
    });
    */
  }
  contacts.sort(function (a, b) {
    return a.last.localeCompare(b.last) || a.first.localeCompare(b.first);
  });
  return contacts;
}

export async function getContact(id: string): Promise<ContactRecord | null> {
  try {
    const response = await fetch(getServerUrl(`/contacts/${id}`), {
      method: `GET`,
      headers: getHeaders(),
    });
    return await response.json();
   } catch(error) {
    console.error(error);
    return null;
  }
}

export function getServerHostPort() {
  return serverHostPort;
}

export async function updateContact(id: string, values: ContactMutation): Promise<ContactRecord> {
  const contact = await getContact(id);
  invariant(contact, `No contact found for ${id}`);
  const updatedContact = { ...contact, ...values };

  const response = await fetch(getServerUrl(`/contacts/${id}`), {
    method: `PUT`,
    headers: getHeaders(),
    body: JSON.stringify(updatedContact),
  })
  
  return await response.json();
}

export async function createContact(contact: ContactMutation): Promise<ContactRecord> {
  const response = await fetch(getServerUrl('/contacts/'), {
    method: `POST`,
    headers: getHeaders(),
    body: JSON.stringify(contact),
  });
  
  return await response.json();
}

export async function deleteContact(id: string) {
  try {
    const response = await fetch(getServerUrl(`/contacts/${id}`), 
    { method: 'DELETE',
      headers: getHeaders()
    });
    return await response.json();
   } catch(error) {
    console.error(error);
    return null;
  }
}
