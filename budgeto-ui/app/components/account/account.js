'use strict';

// Declare module
angular.module('budgeto.account', [
    'ngRoute',
    'ngResource',
    'budgeto.common',
    'angularMoment'
])

    .config(['$routeProvider', function ($routeProvider) {
        console.info("account : load $routeProvider");

        $routeProvider.when('/account', {
            templateUrl: 'components/account/account.html',
            controller: 'AccountCtrl'
        });
    }])

    .controller('AccountCtrl', ['$scope', '$location', 'ApiService', 'AccountResource', 'OperationsResource', AccountCtrl])

    .factory('AccountResource', ['$resource', 'ApiService', AccountResource])

    .factory('OperationsResource', ['$resource', OperationsResource]);

function AccountResource($resource, ApiService) {
    console.info("account : load AccountResource");

    return {
        all: function (AccountApi, success) {
            return $resource(AccountApi.href, {}, {}).query({}, null, success);
        },

        operations: function(Account, success){
            return $resource(getLink('operations', account.links).href, {}, {});
        }
    };
}

function OperationsResource($resource) {
    console.info("account : load OperationsResource");

    return {
        get: function (account) {
            return $resource(getLink('operations', account.links).href, {}, {});
            OperationsResource.get($scope.account).query({}, null);
        }
    }
}

/**
 * controller to manage account
 * @param $scope current scope
 */
function AccountCtrl($scope, $location, ApiService, AccountResource, OperationsResource) {
    console.info("account : load AccountCtrl");

    var AccountApi = ApiService.find('account');


    $scope.operations = [];
    AccountResource.all(AccountApi, function (data) {
        console.debug("get all accounts ", data);

        $scope.accounts = data;

        if (data.length != 0) {
            $scope.account = data[0];
        }
    });

    $scope.formatDate = function (date) {
        return moment(date).format("ddd, hA");
    }

    $scope.$watch(
        function ($scope) {
            return $scope.account
        }
        , function () {
            if ($scope.account !== undefined) {
                console.debug("select account ", $scope.account);

                $scope.operations = OperationsResource.get($scope.account).query({}, null);
            }
        }
    );

    $scope.home = function () {
        $location.path('/');
    }
};
