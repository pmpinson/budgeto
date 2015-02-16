'use strict';

// Declare module
angular.module('budgeto.common', [
  'ngResource'
])

.constant('BudgetoApi', 'http://localhost:9001/budgeto-api')

.factory('ApiResource', [ '$resource', 'BudgetoApi', ApiResourceFactory ])

.service('ApiService', [ 'ApiResource', '$rootScope', ApiServiceFactory ]);

function ApiResourceFactory($resource, BudgetoApi) {
  return $resource(BudgetoApi, {}, {});
}

function ApiServiceFactory(ApiResource, $rootScope) {
    $rootScope.apis = [];

    ApiResource.get({}, null, function(data) {
        console.debug('get alls api');
        for (var key in data.links) {
            if (data.links[key].rel !== 'self') {
                $rootScope.apis.push(data.links[key]);
            }
        }
    });

    return {
        findAll: function() {
            return $rootScope.apis;
        },

        find: function(rel) {
            return getLink(rel, $rootScope.apis);
        }
    };
}

function getLink(rel, links) {
    for (var key in links) {
        if (links[key].rel === rel) {
            return links[key];
        }
    }
    return undefined;
}