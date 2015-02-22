'use strict';

// Declare module
var budgetoAccount = angular.module('budgeto.account', [
    'ngRoute',
    'ngResource',
    'angularMoment',
    'budgeto.modalError',
    'budgeto.apis'
]);

budgetoAccount.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/account', {
        templateUrl: 'components/account/account.html',
        controller: 'AccountCtrl'
    });
}]);

/**
 * account api to store the api definition for account
 * @returns the api
 */
budgetoAccount.factory('AccountApi', ['$resource', '$log', 'ApiService', function($resource, $log, ApiService) {
    $log.debug('budgeto.account : load AccountApi');

    var api = ApiService.find('account');
    $log.debug('budgeto.account : api ', api);

    return api;
}]);

/**
 * account ressource
 * @returns {{all: get all accounts, returning an array in a promise, operations: get all operation of an account, returning an array of operation in a promise}}
 */

budgetoAccount.factory('AccountResource', ['$resource', '$log', 'AccountApi', 'ApiService', function($resource, $log, AccountApi, ApiService) {
    $log.debug('budgeto.account : load AccountResource');

    return {
        all: function () {
            var url = AccountApi.href;
            return $resource(url, {}, {}).query({}).$promise;
        },

        operations: function (account) {
            var url = ApiService.getLink('operations', account.links).href;
            return $resource(url, {}, {}).query({}).$promise;
        }
    };
}]);

/**
 * controller to manage account
 */

budgetoAccount.controller('AccountCtrl', ['$scope', '$location', '$log', 'AccountResource', '$modalError', function($scope, $location, $log, AccountResource, $modalError) {
    $log.debug('budgeto.account : load AccountCtrl');

    $scope.accounts = [];
    $scope.operations = [];
    $scope.account = undefined;

    AccountResource.all().then(function (data) {
        $log.debug('budgeto.account : get all accounts ', data);

        $scope.accounts = data;

        if (data.length != 0) {
            $scope.account = data[0];
        }
    }).catch(function(reason){
        $log.error('error getting accounts / ', reason);
        $modalError.open();
    });

    $scope.formatDate = function (date) {
        return moment(date).format('ddd, hA');
    }

    $scope.$watch(
        function ($scope) {
            return $scope.account
        }
        , function () {
            $scope.operations = [];
            if ($scope.account !== undefined) {
                $log.debug('budgeto.account : select account ', $scope.account);

                 AccountResource.operations($scope.account).then(function (data) {
                    $log.debug('budgeto.account : get all operation ', data);

                    $scope.operations = data;
                }).catch(function(reason){
                    $log.error('error getting operation for ', $scope.account, ' / ', reason);
                    $modalError.open();
                });
            }
        }
    );

    $scope.home = function () {
        $location.path('/');
    }
}]);