'use strict';

// Declare module
var budgetoAccount = angular.module('budgeto.account', [
    'ngRoute',
    'ngResource',
    'budgeto.apis',
    'angularMoment'
]);

budgetoAccount.config(['$routeProvider', function ($routeProvider) {
        console.info('budgeto.account : load $routeProvider');

        $routeProvider.when('/account', {
            templateUrl: 'components/account/account.html',
            controller: 'AccountCtrl'
        });
    }]);

budgetoAccount.controller('AccountCtrl', ['$scope', '$location', 'ApisService', 'AccountResource', AccountCtrl]);

budgetoAccount.factory('AccountApi', ['$resource', 'ApisService', AccountApi]);

budgetoAccount.factory('AccountResource', ['$resource', 'AccountApi', 'ApisService', AccountResource]);

/**
 * account api to store the api definition for account
 * @param $resource
 * @param ApisService
 * @returns the api
 * @constructor
 */
function AccountApi($resource, ApisService) {
    console.info('budgeto.account : load AccountApi');

    var api = ApisService.find('account');
    console.debug('budgeto.account : api ', api);

    return api;
}

/**
 * account ressource
 * @param $resource
 * @param AccountApi
 * @param ApisService
 * @returns {{all: get all accounts, returning an array in a promise, operations: get all operation of an account, returning an array of operation in a promise}}
 * @constructor
 */
function AccountResource($resource, AccountApi, ApisService) {
    console.info('budgeto.account : load AccountResource');

    return {
        all: function () {
            var url = AccountApi.href;
            return $resource(url, {}, {}).query({}).$promise;
        },

        operations: function (account) {
            var url = ApisService.getLink('operations', account.links).href;
            return $resource(url, {}, {}).query({}).$promise;
        }
    };
}

/**
 * controller to manage account
 * @param $scope
 * @param $location
 * @param ApisService
 * @param AccountResource
 * @param OperationsResource
 * @constructor
 */
function AccountCtrl($scope, $location, ApisService, AccountResource) {
    console.info('budgeto.account : load AccountCtrl');

    $scope.accounts = [];
    $scope.operations = [];
    $scope.account = undefined;

    AccountResource.all().then(function (data) {
        console.debug('budgeto.account : get all accounts ', data);

        $scope.accounts = data;

        if (data.length != 0) {
            $scope.account = data[0];
        }
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
                console.debug('budgeto.account : select account ', $scope.account);

                 AccountResource.operations($scope.account).then(function (data) {
                    console.debug('budgeto.account : get all operation ', data);

                    $scope.operations = data;
                });
            }
        }
    );

    $scope.home = function () {
        $location.path('/');
    }
};
