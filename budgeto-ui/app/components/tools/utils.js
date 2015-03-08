"use strict";

// Declare progress module
var budgetoUtils = angular.module("budgeto.utils", [
]);

/**
 * provider an utility object
 */
budgetoUtils.provider("$utils", function () {

    var $utilsProvider = {

        $get: ["$log", function ($log) {
            $log.debug("budgeto.utils : load $utils");

            var $utils = {};

            $utils.isObject = function(val) {
                return _.isObject(val);
            };

            $utils.formatObject = function(val) {
                return JSON.stringify(val, null, "    ");
            };

            return $utils;
        }]
    };

    return $utilsProvider;
});