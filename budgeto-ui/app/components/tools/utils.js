"use strict";

define(['angular', 'underscore'], function(angular, _) {

    var moduleDefinition = {
        name: "budgeto.utils",
        dependencies: [
        ],
        module: undefined
    }

    // Register angular module
    moduleDefinition.module = angular.module(moduleDefinition.name, moduleDefinition.dependencies);

    /**
     * provider an utility object
     */
    moduleDefinition.module.provider("$utils", function () {

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

    return moduleDefinition;
});