const createTokenStore = () => {
    let data;
    const getItem = () => data;
    const setItem = (value) => {
        data = value;
    };
    const removeItem = () => {
        data = null;
    };
    return { getItem, setItem, removeItem }
};

export default createTokenStore;