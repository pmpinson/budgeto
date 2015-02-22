'use strict';

// Declare apis module
var budgetoApis = angular.module('budgeto.apis', [
    'ngResource'
])

/**
 * Resource http to cal apis endpoint
 * @returns {{all: to get all apis from rest endpoint, returning an array of apis in a promise}}
 */
budgetoApis.factory('ApisResource', ['$resource', '$log', 'BudgetoRestApiURL', function($resource, $log, BudgetoRestApiURL) {
    $log.debug('budgeto.apis : load ApiResource');

    return {
        all: function () {
            return $resource(BudgetoRestApiURL, {}, {}).get({}).$promise;
        }
    };
}]);

/**
 * init of module to get all available apis
 * @returns {{load: promise to load the availables api from server, returning a promise on the result}}
 */
budgetoApis.factory('ApisLoader', ['$q', '$rootScope', '$log', 'ApisResource', function($q, $rootScope, $log, ApisResource) {
    $log.debug('budgeto.apis : load ApisLoader');

    return {
        load: function () {
            return ApisResource.all().then(function (data) {
                $log.debug('budgeto.apis : call api to get all available apis');

                $rootScope.apis = [];
                for (var key in data.links) {
                    if (data.links[key].rel !== 'self') {
                        $rootScope.apis.push(data.links[key]);
                    }
                }
                $log.debug('budgeto.apis : available apis ', $rootScope.apis);
            });
        }
    };
}]);

/**
 * apis service to get api data
 * @returns {{findAll: to gell an array of all apis, returning an array, find: to get one api by name, getLink: to find a href in array of link, return a string}}
 */
budgetoApis.factory('ApisService', ['$rootScope', '$log', function($rootScope, $log) {
    $log.debug('budgeto.apis : load ApiService');

    return {

        findAll: function () {
            return $rootScope.apis;
        },

        find: function (rel) {
            return this.getLink(rel, $rootScope.apis);
        },

        getLink: function (rel, links) {
            for (var key in links) {
                if (links[key].rel === rel) {
                    return links[key];
                }
            }
            return undefined;
        }
    }
}]);