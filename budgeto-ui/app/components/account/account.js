"use strict";

// Declare module
var budgetoAccount = angular.module("budgeto.account", [
    "ui.router",
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
budgetoAccount.controller("AccountCtrl", ["$scope", "$state", "$log", function ($scope, $state, $log) {
    $log.debug("budgeto.account : load AccountCtrl");

    $state.go("account.list");

    this.home = function () {
        $state.go("home");
    };
}]);

/**
 * controller to manage account list
 */

budgetoAccount.controller("AccountListCtrl", ["$scope", "$state", "$log", "AccountResource", function ($scope, $state, $log, AccountResource) {
    $log.debug("budgeto.account : load AccountListCtrl");

    var that = this;

    that.accounts = [];
    that.account = undefined;

    AccountResource.all().then(function (data) {
        $log.debug("budgeto.account : get all accounts", data);

        that.accounts = data;
        if (that.accounts.length !== 0) {
            that.account = that.accounts[0];
        }
    });

    $scope.$watch(
        function () {
            return that.account;
        },
        function () {
            if (that.account !== undefined) {
                $log.debug("budgeto.account : select account", that.account);

                $state.go("account.list.detail", {name:that.account.name});
            }
        }
    );
}]);

/**
 * controller to manage detail account
 */
budgetoAccount.controller("AccountDetailCtrl", ["$scope", "$log", "AccountResource", function ($scope, $log, AccountResource) {
    $log.debug("budgeto.account : load AccountDetailCtrl");

    var that = this;

    that.account = $scope.accountListCtrl.account;
    that.operations = [];

    AccountResource.operations(that.account).then(function (data) {
        $log.debug("budgeto.account : get all operations", data);

        that.operations = data;
    });
}]);