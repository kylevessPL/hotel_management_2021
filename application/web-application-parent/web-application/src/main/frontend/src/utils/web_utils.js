const getPageTitle = function(pathname) {
    const arr = pathname.substr(1).split('-');
    return arr.map(str => str.charAt(0).toUpperCase() + str.slice(1)).join(' ');
}

export {getPageTitle};
