'use strict';

// Declare module
angular.module('budgeto.common', [
    'ngResource'
])

    .constant('BudgetoApi', 'http://localhost:9001/budgeto-api')

    .factory('ProgressLoader', [ProgressLoader])

    .factory('ApiResource', ['$resource', 'BudgetoApi', ApiResource])

//.service('ApiService', [ 'ApiResource', '$rootScope', '$q', ApiService ]);

    .factory('ApiService', ['ApiResource', '$rootScope', '$q', ApiService]);

function ProgressLoader() {
    console.info('common : load ProgressLoader');

    var loader = angular.element(document.getElementsByTagName("body"));

    var cpt = 0;

    return {
        setVisible: function (visible) {
            if (visible) {
                this.show();
            } else {
                this.hide();
            }
        },

        show: function () {
            cpt++;
            loader.addClass("progressdialog-loader");
        },

        hide: function () {
            cpt--;
            if (cpt < 0) {
                cpt = 0;
            }
            if (cpt == 0) {
                loader.removeClass("progressdialog-loader");
            }
        }
    };
}

function ApiResource($resource, BudgetoApi) {
    console.info('common : load ApiResource');

    return {
        get: function(success) {
            return $resource(BudgetoApi, {}, {}).get({}, null, success);
        }
    };
}


function ApiService(ApiResource, $rootScope, $q) {
    console.info('common : load ApiService');

    this.promise = undefined;

    this.apis = undefined;

    return {
        load: function () {
            if (this.promise === undefined) {
                var deferred = $q.defer();
                var that = this;
                ApiResource.get(function (data) {
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

                this.promise = deferred.promise.then(function(result){
                    that.apis = result;
                    return result;
                });
            }
            return this.promise;
        },

        findAll: function () {
            return this.apis;
        },

        find: function (rel) {
            return this.getLink(rel, this.apis);
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