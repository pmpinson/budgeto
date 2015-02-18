'use strict';

// Declare apis module
var budgetoApis = angular.module('budgeto.apis', [
    'ngResource',
    'budgeto.progress'
])

budgetoApis.constant('BudgetoApi', 'http://localhost:9001/budgeto-api');

budgetoApis.factory('ApisResource', ['$resource', 'BudgetoApi', ApisResource]);

budgetoApis.factory('Apis', ['$q', 'ApisResource', 'ProgressLoader', Apis]);

budgetoApis.factory('ApisService', ['Apis', ApisService]);

/**
 * Resource http to cal apis endpoint
 */
function ApisResource($resource, BudgetoApi) {
    console.info('budgeto.apis : load ApiResource');

    return {
        get: function(success) {
            return $resource(BudgetoApi, {}, {}).get({}, null, success);
        }
    };
}

/**
 * init of module to get all available apis
 */
function Apis($q, ApisResource, ProgressLoader) {
    console.info('budgeto.apis : load Apis');

    ProgressLoader.hide();

    var deferred = $q.defer();
    ApisResource.get(function (data) {
        console.debug('budgeto.apis : call api to get all available apis');

        var apis = [];
        for (var key in data.links) {
            if (data.links[key].rel !== 'self') {
                apis.push(data.links[key]);
            }
        }
        console.debug('budgeto.apis : available apis ', apis);
        deferred.resolve(apis);

        ProgressLoader.hide();
    });

    return deferred.promise;
}

/**
 * apis service to get api data
 */
function ApisService(Apis) {
    console.info('budgeto.apis : load ApiService');

    return {

        findAll: function () {
            return Apis.then(function(data){return data;});
        },

        find: function (rel) {
            return this.getLink(rel, Apis);
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