import {createContext, useCallback, useEffect, useState} from 'react';
import createTokenStore from "./TokenStore";

export const authContext = createContext({})

const AuthContext = ({children}) => {

    const tokenProvider = createTokenProvider();

    window.addEventListener('storage', event => {
        if (event.key === 'dotcom_logout') {
            if (event.newValue === true.toString()) {
                tokenProvider.setToken(null);
                return;
            }
            tokenProvider.updateLoggedListneners(true);
        }
    });

    const login = newTokens => {
        tokenProvider.setToken(newTokens);
    };

    const logout = () => {
        tokenProvider.setToken(null);
    };

    const authFetch = async (input, init) => {
        return tokenProvider.waitForLogin().then(async () => {
            const token = await tokenProvider.getToken();
            init = init || {};
            init.headers = {
                ...init.headers,
                Authorization: `Bearer ${token}`,
            };
            return fetch(input, init);
        });
    };

    const useAuth = () => {
        const [isLogged, setIsLogged] = useState(tokenProvider.isLoggedIn());
        const listener = useCallback( newIsLogged => setIsLogged(newIsLogged), [setIsLogged]);
        useEffect(() => {
            tokenProvider.subscribeLogged(listener);
            return () => tokenProvider.unsubscribeLogged(listener);
        }, [listener]);
        return [isLogged];
    };

    const useAdmin = () => {
        const [isAdmin, setIsAdmin] = useState(tokenProvider.isAdmin());
        const listener = useCallback(newIsAdmin => setIsAdmin(newIsAdmin), [setIsAdmin]);
        useEffect(() => {
            tokenProvider.subscribeAdmin(listener);
            return () => tokenProvider.unsubscribeAdmin(listener);
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

    let loggedListeners = [];
    let adminListeners = [];

    let isLoggingIn = null;

    const waitForLogin = () => {
        if (!isLoggingIn) {
            return Promise.resolve();
        }
        return isLoggingIn.then(() => {
            isLoggingIn = null;
            return true;
        });
    }

    const onUpdateToken = () => fetch('/api/v1/auth/refresh-token', {
        method: 'POST',
        credentials: 'same-origin'
    }).then(r => r.ok && r.json());

    const getTokenInternal = () => {
        const token = tokenStore.getToken();
        return token || null;
    };

    const subscribeLogged = listener => {
        loggedListeners.push(listener);
    };

    const unsubscribeLogged = listener => {
        loggedListeners = loggedListeners.filter(l => l !== listener);
    };

    const subscribeAdmin = listener => {
        adminListeners.push(listener);
    };

    const unsubscribeAdmin = listener => {
        adminListeners = adminListeners.filter(l => l !== listener);
    };

    const isExpired = exp => {
        if (!exp) {
            return false;
        }
        const date = new Date();
        return date.setSeconds(date.getSeconds() + 5) > exp;
    };

    const checkExpiry = async () => {
        const rfAvailable = isRefreshTokenAvailable();
        const token = getTokenInternal();
        if (token ? isExpired(tokenStore.getExpiry()) : rfAvailable) {
            const response = await onUpdateToken();
            setToken(response || null);
        }
    };

    const isRefreshTokenAvailable = () => {
        const loggedOut = window.localStorage.getItem('dotcom_logout')
        return loggedOut === 'false';
    };

    const getToken = async () => {
        await checkExpiry();
        return getTokenInternal();
    };

    const isLoggedIn = () => {
        isLoggingIn = getToken();
        return isRefreshTokenAvailable();
    };

    const isAdmin = () => {
        const roles = tokenStore.getRoles();
        return roles && roles.includes("ROLE_ADMIN");
    };

    const setToken = token => {
        if (token) {
            tokenStore.setItem(token);
        } else {
            tokenStore.removeItem();
        }
        loggedListeners.forEach(l => l(!!token));
        adminListeners.forEach(l => l(isAdmin()));
    };

    const updateLoggedListneners = logged => loggedListeners.forEach(l => l(logged));

    return {
        getToken,
        isLoggedIn,
        isAdmin,
        setToken,
        subscribeLogged,
        unsubscribeLogged,
        subscribeAdmin,
        unsubscribeAdmin,
        updateLoggedListneners,
        waitForLogin
    };
}

export default AuthContext;
