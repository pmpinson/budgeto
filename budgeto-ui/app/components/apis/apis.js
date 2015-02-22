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
 * provider to manage apis
 */
budgetoApis.provider('ApiService', function() {
    var $apiServiceProvider = {
      $get: ['$log', 'ApisResource', function ($log, ApisResource) {
          $log.debug('budgeto.apis : load ApiService');

            var apis = [];
          var $apiService = {};

            $apiService.load = function() {
                return ApisResource.all().then(function (data) {
                    $log.debug('budgeto.apis : call api to get all available apis');

                    for (var key in data.links) {
                        if (data.links[key].rel !== 'self') {
                            apis.push(data.links[key]);
                        }
                    }
                    $log.debug('budgeto.apis : available apis ', apis);
                });
            };

            $apiService.findAll = function () {
                return apis;
            };

            $apiService.find = function (rel) {
                return this.getLink(rel, apis);
            };

            $apiService.getLink = function (rel, links) {
                for (var key in links) {
                    if (links[key].rel === rel) {
                        return links[key];
                    }
                }
                return undefined;
            };

          return $apiService;
        }]
    };

    return $apiServiceProvider;
});