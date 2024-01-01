////////////////////////////////////////////////////////////////////////////////
// 🛑 Nothing in here has anything to do with Remix, it's just a fake database
////////////////////////////////////////////////////////////////////////////////

//import { matchSorter } from "match-sorter";
// @@ts-expect-error - no types, but it's a tiny function
//import sortBy from "sort-by";
import invariant from "tiny-invariant";

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

////////////////////////////////////////////////////////////////////////////////
// This is just a fake DB table. In a real app you'd be talking to a real db or
// fetching from an existing API.
const fakeContacts = {
  records: {} as Record<string, ContactRecord>,

  async getAll(): Promise<ContactRecord[]> {
    try {
      const response = await fetch(`http://${serverHostPort}/contacts/`);
      return await response.json();
     } catch(error) {
      console.error(error);
      return [];
    }
  },

  async get(id: string): Promise<ContactRecord | null> {
    try {
      const response = await fetch(`http://${serverHostPort}/contacts/${id}`);
      return await response.json();
     } catch(error) {
      console.error(error);
      return null;
    }
  },

  async set(id: string, values: ContactMutation): Promise<ContactRecord> {
    const contact = await fakeContacts.get(id);
    invariant(contact, `No contact found for ${id}`);
    const updatedContact = { ...contact, ...values };
    fakeContacts.records[id] = updatedContact;
    return updatedContact;
  },

  async destroy(id: string): Promise<null> {
    try {
      const response = await fetch(`http://${serverHostPort}/contacts/${id}`, { method: 'DELETE' });
      return await response.json();
     } catch(error) {
      console.error(error);
      return null;
    }
  },
};

////////////////////////////////////////////////////////////////////////////////
// Handful of helper functions to be called from route loaders and actions
export async function getContacts(query?: string | null) {
  await new Promise((resolve) => setTimeout(resolve, 500));
  const contacts = await fakeContacts.getAll();
  if (query) {
    /*
    contacts = matchSorter(contacts, query, {
      keys: ["first", "last"],
    });
    */
  }
  //return contacts.sort(sortBy("last", "createdAt"));
  return contacts;
}

export async function createEmptyContact() {
  const contact = null; //await fakeContacts.create({});
  return contact;
}

export async function getContact(id: string) {
  return fakeContacts.get(id);
}

export async function updateContact(id: string, updates: ContactMutation) {
  const contact = await fakeContacts.get(id);
  if (!contact) {
    throw new Error(`No contact found for ${id}`);
  }
  await fakeContacts.set(id, { ...contact, ...updates });
  return contact;
}

export async function deleteContact(id: string) {
  fakeContacts.destroy(id);
}
