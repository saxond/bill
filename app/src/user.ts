
export interface User {
    access_token: string;
    token_type: string;
    expires_in: number;
    scope: string;
}

export interface ResolvedUser {
    name: string;
    valid: boolean;
}

export function setUser(user: User) {
    localStorage.setItem("user", JSON.stringify(user));
}

export function getUser() : User {
    const user = localStorage.getItem("user");
    if (user && user.length !== undefined && user.length > 2) {
        return JSON.parse(user);
    }
    return null;
}

export function removeUser() {
    localStorage.removeItem("user");
}