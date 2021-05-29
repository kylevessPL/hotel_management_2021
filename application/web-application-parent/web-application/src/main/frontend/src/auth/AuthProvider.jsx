import {useCallback, useEffect, useState} from 'react';
import createTokenStore from "./TokenStore";

const createAuthProvider = () => {
    const tokenProvider = createTokenProvider();
    const login = (newTokens) => {
        tokenProvider.setToken(newTokens);
    };
    const logout = () => {
        tokenProvider.setToken(null);
    };
    const authFetch = async (input, init) => {
        const token = await tokenProvider.getToken();
        init = init || {};
        init.headers = {
            ...init.headers,
            Authorization: `Bearer ${token}`,
        };
        return fetch(input, init);
    };
    const useAuth = () => {
        const [isLogged, setIsLogged] = useState(tokenProvider.isLoggedIn);
        const listener = useCallback((newIsLogged) => {
            setIsLogged(newIsLogged);
        }, [setIsLogged]);
        useEffect(() => {
            tokenProvider.subscribe(listener);
            return () => {
                tokenProvider.unsubscribe(listener);
            };
        }, [listener]);
        return [isLogged];
    };
    return { useAuth, authFetch, login, logout };
};

const createTokenProvider = () => {
    const tokenStore = createTokenStore();
    let listeners = [];
    const accessTokenKey = 'accessToken';
    const refreshTokenKey = 'refreshToken';
    const onUpdateToken = (token) => fetch('/refresh-token', {
        method: 'POST',
        body: token[refreshTokenKey]
    }).then(r => r.json())
    const getTokenInternal = () => {
        const data = tokenStore.getItem();
        return (data && JSON.parse(data)) || null;
    };
    const subscribe = (listener) => {
        listeners.push(listener);
    };
    const unsubscribe = (listener) => {
        listeners = listeners.filter(l => l !== listener);
    };
    const jwtExp = (token) => {
        if (!(typeof token === 'string')) {
            return null;
        }
        const split = token.split('.');
        if (split.length < 2) {
            return null;
        }
        try {
            const jwt = JSON.parse(atob(token.split('.')[1]));
            if (jwt && jwt.exp && Number.isFinite(jwt.exp)) {
                return jwt.exp * 1000;
            }
            else {
                return null;
            }
        }
        catch (e) {
            return null;
        }
    };
    const getExpire = (token) => {
        if (!token) {
            return null;
        }
        if (accessTokenKey) {
            const exp = jwtExp(token[accessTokenKey]);
            if (exp) {
                return exp;
            }
        }
        return jwtExp(token);
    };
    const isExpired = (exp) => {
        if (!exp) {
            return false;
        }
        return Date.now() > exp;
    };
    const checkExpiry = async () => {
        const token = getTokenInternal();
        if (token && isExpired(getExpire(token))) {
            const newToken = onUpdateToken ? await onUpdateToken(token) : null;
            if (newToken) {
                tokenStore.setToken(newToken);
            }
            else {
                tokenStore.removeItem();
            }
        }
    };
    const getToken = async () => {
        await checkExpiry();
        if (accessTokenKey) {
            const token = getTokenInternal();
            return token && token[accessTokenKey];
        }
        return getTokenInternal();
    };
    const isLoggedIn = () => {
        const token = getTokenInternal();
        return !!token;
    };
    const setToken = (token) => {
        if (token) {
            tokenStore.setItem(JSON.stringify(token));
        }
        else {
            tokenStore.removeItem();
        }
        notify();
    };
    const notify = () => {
        const isLogged = isLoggedIn();
        listeners.forEach(l => l(isLogged));
    };
    return { getToken, isLoggedIn, setToken, subscribe, unsubscribe };
}

export default createAuthProvider;
