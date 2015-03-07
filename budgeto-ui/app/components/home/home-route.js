"use strict";

// Declare module
var budgetoHomeRoute = angular.module("budgeto.home.route", [
    "ui.router"
]);

budgetoHomeRoute.config(["$stateProvider", function ($stateProvider) {
    $stateProvider
        .state("home", {
            templateUrl: "components/home/home.html",
            controller: "HomeCtrl as homeCtrl"
        });
}]);