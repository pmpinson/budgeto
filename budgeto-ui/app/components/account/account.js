"use strict";

// Declare module
var budgetoAccount = angular.module("budgeto.account", [
    "ngResource",
    "angularMoment",
    "budgeto.modalError",
    "budgeto.apis"
]);

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

budgetoAccount.factory("AccountResource", ["$resource", "$log", "AccountApi", "ApiService", "$modalError", function ($resource, $log, AccountApi, ApiService, $modalError) {
    $log.debug("budgeto.account : load AccountResource");

    return {
        all: function () {
            var url = AccountApi.href;
            return $resource(url, {}, {}).query({}).$promise.catch(function (reason) {
                $log.error("error getting accounts :", reason);
                $modalError.open();
            });
        },

        operations: function (account) {
            var url = ApiService.getLink("operations", account.links).href;
            return $resource(url, {}, {}).query({}).$promise.catch(function (reason) {
                $log.error("error getting operations for", account, ":", reason);
                $modalError.open();
            });
        }
    };
}]);

/**
 * controller to manage account
 */

budgetoAccount.controller("AccountCtrl", ["$scope", "$location", "$log", "AccountResource", function ($scope, $location, $log, AccountResource) {
    $log.debug("budgeto.account : load AccountCtrl");

    $scope.accounts = [];
    $scope.account = undefined;
    $scope.operations = [];

    AccountResource.all().then(function (data) {
        $log.debug("budgeto.account : get all accounts", data);

        $scope.accounts = data;
        if (data.length !== 0) {
            $scope.account = data[0];
        }
    });

    $scope.$watch(
        function ($scope) {
            return $scope.account;
        },
        function () {
            $scope.operations = [];
            if ($scope.account !== undefined) {
                $log.debug("budgeto.account : select account", $scope.account);

                AccountResource.operations($scope.account).then(function (data) {
                    $log.debug("budgeto.account : get all operations", data);

                    $scope.operations = data;
                });
            }
        }
    );

    $scope.home = function () {
        $location.path("/");
    };
}]);