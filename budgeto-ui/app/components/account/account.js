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

budgetoAccount.controller('AccountCtrl', ['$scope', '$location', 'ApisService', 'AccountResource', 'OperationsResource', AccountCtrl]);

budgetoAccount.factory('AccountApi', ['$resource', 'ApisService', AccountApi]);

budgetoAccount.factory('AccountResource', ['$resource', 'AccountApi', 'ApisService', AccountResource]);

budgetoAccount.factory('OperationsResource', ['$resource', 'ApisService', OperationsResource]);

function AccountApi($resource, ApisService) {
    console.info('budgeto.account : load AccountApi');

    var api = ApisService.find('account');
    console.debug('budgeto.account : api ', api);

    return api;
}

function AccountResource($resource, AccountApi, ApisService) {
    console.info('budgeto.account : load AccountResource');

    return {
        all: function (success) {
            return $resource(AccountApi.href, {}, {}).query({}, null, success);
        },

        operations: function (Account, success) {
            return $resource(ApisService.getLink('operations', account.links).href, {}, {});
        }
    };
}

function OperationsResource($resource, ApisService) {
    console.info('account : budgeto.account OperationsResource');

    return {
        get: function (account) {
            return $resource(ApisService.getLink('operations', account.links).href, {}, {});
            OperationsResource.get($scope.account).query({}, null);
        }
    }
}

/**
 * controller to manage account
 * @param $scope current scope
 */
function AccountCtrl($scope, $location, ApisService, AccountResource, OperationsResource) {
    console.info('budgeto.account : load AccountCtrl');

    $scope.operations = [];
    AccountResource.all(function (data) {
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
            if ($scope.account !== undefined) {
                console.debug('budgeto.account : select account ', $scope.account);

                $scope.operations = OperationsResource.get($scope.account).query({}, null);
            }
        }
    );

    $scope.home = function () {
        $location.path('/');
    }
};
