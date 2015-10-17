'use strict';

define(['../../../target/dist/app/lib/angular/angular.min'], function(angular) {

    var moduleDefinition = {
        name: 'budgeto.loading',
        dependencies: [
        ],
        module: undefined
    };

    // Register angular module
    moduleDefinition.module = angular.module(moduleDefinition.name, moduleDefinition.dependencies);

    /**
     * provider to manage loading of application
     */
    moduleDefinition.module.provider('LoadingService', function () {
        var servicesNames = [];

        var $loadingServiceProvider = {

            add: function (value) {
                servicesNames.push(value);
            },

            $get: ['$log', '$q', '$injector', function ($log, $q, $injector) {
                $log.debug('budgeto.loading : load LoadingService');

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

    return moduleDefinition;
});