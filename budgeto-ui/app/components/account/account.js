"use strict";

// Declare module
var budgetoAccount = angular.module("budgeto.account", [
    "ui.router",
    "ngResource",
    "angularMoment",
    "budgeto.modalError",
    "budgeto.apis"
]);

budgetoAccount.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("account", {
        templateUrl: "components/account/account.html",
        controller: "AccountCtrl"
    });
    $stateProvider.state("account.detail", {
        templateUrl: "components/account/detail.html",
        controller: "AccountDetailCtrl"
    });
    $stateProvider.state("account.detail.operations", {
        templateUrl: "components/account/operations.html",
        controller: "OperationsCtrl"
    });
}]);

/**
 * account api to store the api definition for account
 * @returns the api
 */
budgetoAccount.factory("AccountApi", ["$resource", "$log", "ApiService", function ($resource, $log, ApiService) {
    $log.debug("budgeto.account : load AccountApi");

    var api = ApiService.find("account");
    $log.debug("budgeto.account : api", api);

    return api;
}]);

/**
 * account ressource
 * @returns {{all: get all accounts, returning an array in a promise, operations: get all operation of an account, returning an array of operation in a promise}}
 */

budgetoAccount.factory("AccountResource", ["$resource", "$log", "AccountApi", "ApiService", function ($resource, $log, AccountApi, ApiService) {
    $log.debug("budgeto.account : load AccountResource");

    return {
        all: function () {
            var url = AccountApi.href;
            return $resource(url, {}, {}).query({}).$promise;
        },

        operations: function (account) {
            var url = ApiService.getLink("operations", account.links).href;
            return $resource(url, {}, {}).query({}).$promise;
        }
    };
}]);

/**
 * controller to manage account
 */

budgetoAccount.controller("AccountCtrl", ["$scope", "$state", "$log", "AccountResource", "$modalError", function ($scope, $state, $log, AccountResource, $modalError) {
    $log.debug("budgeto.account : load AccountCtrl");

    AccountResource.all().then(function (data) {
        $log.debug("budgeto.account : get all accounts", data);

        $scope.accounts = data;
        $scope.account = undefined;

        if (data.length !== 0) {
            $scope.account = data[0];
        }
    }).catch(function (reason) {
        $scope.accounts = [];
        $scope.account = undefined;
        $log.error("error getting accounts :", reason);
        $modalError.open();
    });

    $scope.$watch(
        function ($scope) {
            return $scope.account;
        },
        function () {
            if ($scope.account !== undefined) {
                $log.debug("budgeto.account : select account", $scope.account);
            $state.go("account.detail");

//                AccountResource.operations($scope.account).then(function (data) {
//                    $log.debug("budgeto.account : get all operations", data);
//
//                    $scope.operations = data;
//                }).catch(function (reason) {
//                    $log.error("error getting operations for", $scope.account, ":", reason);
//                    $modalError.open();
//                });
            }
        }
    );

    $scope.home = function () {
        $state.go("home");
    };
}]);

/**
 * controller to manage account
 */

budgetoAccount.controller("AccountDetailCtrl", ["$scope", "$state", "$log", "AccountResource", "$modalError", function ($scope, $state, $log, AccountResource, $modalError) {
    $log.debug("budgeto.account : load AccountCtrl");

    $scope.operations = [];
    if ($scope.account !== undefined) {
        $log.debug("budgeto.account : select account", $scope.account);

        AccountResource.operations($scope.account).then(function (data) {
            $log.debug("budgeto.account : get all operations", data);

            $scope.operations = data;
            $state.go("account.detail.operations");
        }).catch(function (reason) {
            $log.error("error getting operations for", $scope.account, ":", reason);
            $modalError.open();
        });
    }

}]);

/**
 * controller to manage account
 */

budgetoAccount.controller("OperationsCtrl", ["$scope", "$state", "$log", "AccountResource", "$modalError", function ($scope, $state, $log, AccountResource, $modalError) {
    $log.debug("budgeto.account : load OperationsCtrl");
}]);