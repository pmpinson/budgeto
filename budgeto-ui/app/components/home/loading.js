"use strict";

// Declare module
var budgetoLoading = angular.module("budgeto.loading", []);

/**
 * provider to manage loading of application
 */
budgetoLoading.provider("LoadingService", function () {
    var servicesNames = [];

    var $loadingServiceProvider = {

        add: function (value) {
            servicesNames.push(value);
        },

        $get: ["$log", "$q", "$injector", function ($log, $q, $injector) {
            $log.debug("budgeto.loading : load LoadingService");

            var $loadingService = {};
            var promise;

            if (servicesNames.length !== 0) {

                var servicesPromises = [];
                for (var key in servicesNames) {
                    servicesPromises.push($injector.get(servicesNames[key]).loaded());
                }
                promise = $q.all(servicesPromises);
            } else {
                var deferred = $q.defer();
                promise = deferred.promise;
                deferred.resolve(true);
            }

            $loadingService.config = function () {
                return {
                    getServicesNames: function () {
                        return servicesNames;
                    }
                };
            };

            $loadingService.loaded = function () {
                return promise;
            };

            return $loadingService;
        }]
    };

    return $loadingServiceProvider;
});