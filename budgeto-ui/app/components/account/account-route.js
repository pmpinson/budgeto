"use strict";

// Declare module
var budgetoAccountRoute = angular.module("budgeto.account.route", [
    "ui.router"
]);

budgetoAccountRoute.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("account", {
        url: "/account",
        templateUrl: "components/account/account.html",
        controller: "AccountCtrl as accountCtrl"
    });
    $stateProvider.state("account.list", {
        templateUrl: "components/account/account-list.html",
        controller: "AccountListCtrl as accountListCtrl"
    });
    $stateProvider.state("account.list.detail", {
        url: "/detail",
        params: {
            name:undefined,
        },
        templateUrl: "components/account/account-detail.html",
        controller: "AccountDetailCtrl as accountDetailCtrl"
    });
}]);
