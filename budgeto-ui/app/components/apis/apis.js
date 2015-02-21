'use strict';

// Declare apis module
var budgetoApis = angular.module('budgeto.apis', [
    'ngResource'
])

budgetoApis.factory('ApisResource', ['$resource', 'BudgetoRestApiURL', ApisResource]);

budgetoApis.factory('ApisLoader', ['$q', '$rootScope', 'ApisResource', ApisLoader]);

budgetoApis.factory('ApisService', ['$rootScope', ApisService]);

/**
 */
/**
 * Resource http to cal apis endpoint
 * @param $resource
 * @param BudgetoRestApiURL
 * @returns {{all: to get all apis from rest endpoint, returning an array of apis in a promise}}
 * @constructor
 */
function ApisResource($resource, BudgetoRestApiURL) {
    console.info('budgeto.apis : load ApiResource');

    return {
        all: function () {
            return $resource(BudgetoRestApiURL, {}, {}).get({}).$promise;
        }
    };
}

/**
 * init of module to get all available apis
 * @param $q
 * @param $rootScope
 * @param ApisResource
 * @returns {{load: promise to load the availables api from server, returning a promise on the result}}
 * @constructor
 */
function ApisLoader($q, $rootScope, ApisResource) {
    console.info('budgeto.apis : load ApisLoader');

    return {
        load: function () {
            return ApisResource.all().then(function (data) {
                console.debug('budgeto.apis : call api to get all available apis');

                $rootScope.apis = [];
                for (var key in data.links) {
                    if (data.links[key].rel !== 'self') {
                        $rootScope.apis.push(data.links[key]);
                    }
                }
                console.debug('budgeto.apis : available apis ', $rootScope.apis);
            });
        }
    };
}

/**
 * apis service to get api data
 */
/**
 *
 * @param $rootScope
 * @returns {{findAll: to gell an array of all apis, returning an array, find: to get one api by name, getLink: to find a href in array of link, return a string}}
 * @constructor
 */
function ApisService($rootScope) {
    console.info('budgeto.apis : load ApiService');

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
}