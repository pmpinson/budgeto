'use strict';

// Declare module
angular.module('budgeto.common', [
  'ngResource'
])

.constant('BudgetoApi', 'http://localhost:9001/budgeto-api')

.factory('ApiResource', [ '$resource', 'BudgetoApi', ApiResource ])

//.service('ApiService', [ 'ApiResource', '$rootScope', '$q', ApiService ]);

.factory('ApiService', [ 'ApiResource', '$rootScope', '$q', ApiService ]);

function ApiResource($resource, BudgetoApi) {
    console.info('common : load ApiResource');

  return $resource(BudgetoApi, {}, {});
}

function ApiServiceGood(ApiResource, $rootScope, $q) {
    console.info('common : load ApiService');

    this.promise = undefined;

    this.load = function() {
        if (this.promise === undefined) {
            var deferred = $q.defer();

            ApiResource.get({}, null, function(data) {
                console.debug('common : call api to get all available apis');
                var apis = [];
                for (var key in data.links) {
                    if (data.links[key].rel !== 'self') {
                        apis.push(data.links[key]);
                    }
                }
                console.debug('common : available apis ', apis);
                deferred.resolve(apis);
            });

            this.promise = deferred.promise;
        }
        return this.promise;
    };

    this.findAll = function() {
        return this.load();
    };

    this.findAll2 = function() {
        var deferred=$q.defer();
        this.load().then(function(data) {
            deferred.resolve(data);
        });
        return deferred.promise;
    };

    this.find = function(rel) {
        var that = this;
        this.load().then(function(result){
            return that.getLink(rel, result);
        });
    };

    this.getLink = function(rel, links) {
        for (var key in links) {
            if (links[key].rel === rel) {
                return links[key];
            }
        }
        return undefined;
    }
}



function ApiService(ApiResource, $rootScope, $q) {
    console.info('common : load ApiService');

    this.promise = undefined;

    return {
        load: function() {
            if (this.promise === undefined) {
                var deferred = $q.defer();

                ApiResource.get({}, null, function(data) {
                    console.debug('common : call api to get all available apis');
                    var apis = [];
                    for (var key in data.links) {
                        if (data.links[key].rel !== 'self') {
                            apis.push(data.links[key]);
                        }
                    }
                    console.debug('common : available apis ', apis);
                    deferred.resolve(apis);
                });

                this.promise = deferred.promise;
            }
            return this.promise;
        },

        findAll: function() {
            return this.load();
        },

        findAll2: function() {
            var result = function(data) {
                 return data;
             };
            this.load().then(result);
            return result;
        },

        find: function(rel) {
            var that = this;
            this.load().then(function(result){
                return that.getLink(rel, result);
            });
        },

        getLink: function(rel, links) {
            for (var key in links) {
                if (links[key].rel === rel) {
                    return links[key];
                }
            }
            return undefined;
        }
    }
}