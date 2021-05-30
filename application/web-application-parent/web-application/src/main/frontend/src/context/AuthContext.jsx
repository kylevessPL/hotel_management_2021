import {createContext, useCallback, useEffect, useState} from 'react';
import createTokenStore from "./TokenStore";

export const authContext = createContext({})

const AuthContext = ({children}) => {

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
        const [isLogged, setIsLogged] = useState(tokenProvider.isLoggedIn());
        const listener = useCallback((newIsLogged) => {
            setIsLogged(newIsLogged);
        }, [setIsLogged]);
        useEffect(() => {
            tokenProvider.subscribeLogged(listener);
            return () => {
                tokenProvider.unsubscribeLogged(listener);
            };
        }, [listener]);
        return [isLogged];
    };

    const useAdmin = () => {
        const [isAdmin, setIsAdmin] = useState(tokenProvider.isAdmin());
        const listener = useCallback((newIsAdmin) => {
            setIsAdmin(newIsAdmin);
        }, [setIsAdmin]);
        useEffect(() => {
            tokenProvider.subscribeAdmin(listener);
            return () => {
                tokenProvider.unsubscribeAdmin(listener);
            };
        }, [listener]);
        return [isAdmin];
    };

    return (
        <authContext.Provider value={{login, logout, authFetch, useAuth, useAdmin}}>
            {children}
        </authContext.Provider>
    );
};

const createTokenProvider = () => {

    const tokenStore = createTokenStore();
    let isLoggedListeners = [];
    let isAdminListeners = [];

    const onUpdateToken = () => fetch('/api/v1/auth/refresh-token', {
        method: 'POST',
        credentials: 'same-origin'
    }).then(r => r.json());

    const getTokenInternal = () => {
        const token = tokenStore.getToken();
        return token || null;
    };

    const subscribeLogged = (listener) => {
        isLoggedListeners.push(listener);
    };

    const subscribeAdmin = (listener) => {
        isAdminListeners.push(listener);
    };

    const unsubscribeLogged = (listener) => {
        isLoggedListeners = isLoggedListeners.filter(l => l !== listener);
    };

    const unsubscribeAdmin = (listener) => {
        isAdminListeners = isAdminListeners.filter(l => l !== listener);
    };

    const isExpired = (exp) => {
        if (!exp) {
            return false;
        }
        const date = new Date();
        return date.setSeconds(date.getSeconds() + 5) > exp;
    };

    const checkExpiry = async () => {
        const rfAvailable = isRefreshTokenAvailable();
        if (tokenStore ? isExpired(tokenStore.getExpiry()) : rfAvailable) {
            const newToken = await onUpdateToken() || null;
            if (newToken) {
                tokenStore.setItem(newToken);
            }
            else {
                tokenStore.removeItem();
            }
        }
    };

    const isRefreshTokenAvailable = () => {
        const loggedOut = window.localStorage.getItem('dotcom_logout')
        return loggedOut === 'false' && loggedOut;
    };

    const getToken = async () => {
        await checkExpiry();
        return getTokenInternal();
    };

    const isLoggedIn = () => {
        getToken().then(r => isLoggedListeners.forEach(l => l(!!r)));
        return isRefreshTokenAvailable();
    };

    const isAdmin = () => {
        const roles = tokenStore.getRoles();
        return roles && roles.includes("ROLE_ADMIN");
    };

    const setToken = (token) => {
        if (token) {
            tokenStore.setItem(token);
        }
        else {
            tokenStore.removeItem();
        }
        notify();
    };

    const notify = () => {
        const logged = isLoggedIn()
        const admin = isAdmin()
        isLoggedListeners.forEach(l => l(logged));
        isAdminListeners.forEach(l => l(admin));
    };

    return {
        getToken,
        isLoggedIn,
        isAdmin,
        setToken,
        subscribeLogged,
        subscribeAdmin,
        unsubscribeLogged,
        unsubscribeAdmin
    };
}

export default AuthContext;
