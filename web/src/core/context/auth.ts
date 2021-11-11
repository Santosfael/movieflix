import jwtDecode from "jwt-decode";

import history from './history';

export const CLIENT_ID = process.env.REACT_APP_CLIENT_ID ?? 'dsmovie';
export const CLIENT_SECRET = process.env.REACT_APP_CLIENT_SECRET ?? 'dsmovie123';

type LoginResponse = {
    access_token: string;
    token_type: string;
    expires_in: number;
    scope: string;
    userName: string;
    useId: number;
}

export type Role = 'ROLE_VISITOR' | 'ROLE_MEMBER';

type AccessToken = {
    exp: number;
    user_name: string;
    authorities: Role[];
}

export function saveSessionData(loginResponse: LoginResponse) {
    localStorage.setItem('authData', JSON.stringify(loginResponse));
};

export function getSessionData() {
    const sessionData = localStorage.getItem('authData') ?? '{}';
    const parsedSessionData = JSON.parse(sessionData);

    return parsedSessionData as LoginResponse;
};

export function getAccessTokenDecoded() {
    const sessionData = getSessionData();
    try {
        const tokenDecoded = jwtDecode(sessionData.access_token);
        return tokenDecoded as AccessToken;
    } catch (error) {
        return {} as AccessToken;
    }
};

export function isTokenValid() {
    const { exp } = getAccessTokenDecoded();
    return Date.now() <= (exp * 1000);
};

export function isAuthenticated() {
    const sessionData = getSessionData();

    return sessionData.access_token && isTokenValid();
};

export function isAllowedByRole(routeRoles: Role[] = []) {
    if (routeRoles.length === 0) {
        return true;
    }

    const { authorities } = getAccessTokenDecoded();
    return routeRoles.some(role => authorities?.includes(role));
}

export function logout() {
    localStorage.removeItem('authData');
    history.replace('/');
}