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
        controller: "AccountCtrl as accountCtrl"
    });
    $stateProvider.state("account.list", {
        templateUrl: "components/account/account-list.html",
        controller: "AccountListCtrl as accountListCtrl"
    });
    $stateProvider.state("account.list.detail", {
        templateUrl: "components/account/account-detail.html",
        controller: "AccountDetailCtrl"
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

    $state.go("account.list");

    this.home = function () {
        $state.go("home");
    };
}]);

/**
 * controller to manage account
 */

budgetoAccount.controller("AccountListCtrl", ["$scope", "$state", "$log", "AccountResource", "$modalError", function ($scope, $state, $log, AccountResource, $modalError) {
    $log.debug("budgeto.account : load AccountCtrl");

    this.accounts = [];
    var that = this;

    AccountResource.all().then(function (data) {
        $log.debug("budgeto.account : get all accounts", data);

        that.accounts = data;
        that.account = undefined;

        if (data.length !== 0) {
            that.account = data[0];
        }
    }).catch(function (reason) {
        that.accounts = [];
        that.account = undefined;
        $log.error("error getting accounts :", reason);
        $modalError.open();
    });

    $scope.$watch(
        function ($scope) {
            return that.account;
        },
        function () {
            if (that.account !== undefined) {
                $log.debug("budgeto.account : select account", $scope.account);
                $state.go("account.detail");
            }
        }
    );
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