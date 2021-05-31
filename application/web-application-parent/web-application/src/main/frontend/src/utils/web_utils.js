const getPageTitle = function(pathname) {
    const arr = pathname.split("/").pop().replace(/-/g, ' ').split(' ');
    return arr.map(str => str.charAt(0).toUpperCase() + str.slice(1)).join(' ');
}

export {getPageTitle};
