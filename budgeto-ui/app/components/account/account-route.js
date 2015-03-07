"use strict";

// Declare module
var budgetoAccountRoute = angular.module("budgeto.account.route", [
    "ngRoute"
]);

budgetoAccountRoute.config(["$routeProvider", function ($routeProvider) {
    $routeProvider.when("/account", {
        templateUrl: "components/account/account.html",
        controller: "AccountCtrl"
    });
}]);
