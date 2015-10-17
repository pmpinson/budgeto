'use strict';

/**
 * provider an utility object
 * function isObject to know if var is an object
 * function formatObject to format an object to a JSON string
 */
define(['../../../target/dist/app/lib/underscore/underscore-min'], function(_) {

    var utils = {};

    utils.isObject = function(val) {
        return _.isObject(val);
    };

    utils.formatObject = function(val) {
        return JSON.stringify(val, null, '    ');
    };

    return utils;
});