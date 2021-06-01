const createTokenStore = () => {

    let token;
    let expiry;

    const getToken = () => token;

    const getExpiry = () => expiry;

    const getRoles = () => JSON.parse(window.localStorage.getItem('dotcom_role'));

    const setItem = (value) => {
        const date = new Date();
        token = value.accessToken;
        expiry = date.setSeconds(date.getSeconds() + value.expires);
        const roles = value.roles;
        const username = value.username;
        if (roles) {
            window.localStorage.setItem('dotcom_role', JSON.stringify(roles));
        }
        if (username) {
            window.localStorage.setItem('dotcom_user', username);
        }
        window.localStorage.setItem('dotcom_logout', false.toString());
    };

    const removeItem = () => {
        token = null;
        expiry = null;
        window.localStorage.setItem('dotcom_logout', true.toString());
        window.localStorage.removeItem('dotcom_role');
        window.localStorage.removeItem('dotcom_user');
    };

    return { getToken, getExpiry, getRoles, setItem, removeItem }
};

export default createTokenStore;
