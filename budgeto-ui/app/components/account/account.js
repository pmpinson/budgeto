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
        resolve: {
            accounts: ["AccountResource", "$log", "$modalError", function (AccountResource, $log, $modalError) {
                return AccountResource.all();
            }],
            account: function(){return undefined}
        },
        templateUrl: "components/account/account-list.html",
        controller: "AccountListCtrl as accountListCtrl"
    });
    $stateProvider.state("account.list.detail", {
        parent: "account.list",
        resolve: {
            account: ["account", function (account) {
                return account;
            }]
        },
        templateUrl: "components/account/account-detail.html",
        controller: "AccountDetailCtrl as accountDetailCtrl"
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
budgetoAccount.controller("AccountCtrl", ["$scope", "$state", "$log", "AccountResource", "$modalError", function ($scope, $state, $log, AccountResource, $modalError) {
budgetoAccount.controller("AccountCtrl", ["$scope", "$location", "$log", "AccountResource", function ($scope, $location, $log, AccountResource) {
    $log.debug("budgeto.account : load AccountCtrl");

    $scope.accounts = [];
    $scope.account = undefined;
    $scope.operations = [];

    AccountResource.all().then(function (data) {
        $log.debug("budgeto.account : get all accounts", data);
    $state.go("account.list");

        $scope.accounts = data;
        if (data.length !== 0) {
            $scope.account = data[0];
        }
    });
    this.home = function () {
        $state.go("home");
    };
}]);

/**
 * controller to manage account
 */

budgetoAccount.controller("AccountListCtrl", ["$scope", "$state", "$log", "AccountResource", "$modalError", "accounts", "account", function ($scope, $state, $log, AccountResource, $modalError, accounts, account) {
    $log.debug("budgeto.account : load AccountListCtrl");
    $log.debug("budgeto.account : get all accounts", accounts);

    var that = this;

    this.accounts = accounts;
    this.account = account;
    if (this.accounts.length !== 0) {
        this.account = this.accounts[0];
    }

    $scope.$watch(
        function ($scope) {
            return that.account;
        },
        function () {
            $scope.operations = [];
            if ($scope.account !== undefined) {
                $log.debug("budgeto.account : select account", $scope.account);

                AccountResource.operations($scope.account).then(function (data) {
                    $log.debug("budgeto.account : get all operations", data);

                    $scope.operations = data;
            if (that.account !== undefined) {
                $log.debug("budgeto.account : select account", that.account);
                $state.go("account.list.detail").catch(function (reason) {
                    $log.error("error getting accounts :", reason);
                    $modalError.open();
                });
            }
        }
    );
}]);

/**
 * controller to manage account
 */

budgetoAccount.controller("AccountDetailCtrl", ["$scope", "$state", "$log", "AccountResource", "$modalError", function ($scope, $state, $log, AccountResource, $modalError) {
    $log.debug("budgeto.account : load AccountDetailCtrl");

    var that = this;
    that.operations = [];
    console.log(that);
    if (that.account !== undefined) {
        $log.debug("budgeto.account : select account", that.account);

        AccountResource.operations(that.account).then(function (data) {
            $log.debug("budgeto.account : get all operations", data);

            that.operations = data;
            $state.go("account.detail.operations");
        });
    }

}]);