var utils = {};

/*
 * function formatObject to format an object to a JSON string
 */
utils.formatObject = function(val) {
    return JSON.stringify(val, null, '    ');
};

export default utils;