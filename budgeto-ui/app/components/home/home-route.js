"use strict";

// Declare module
var budgetoHomeRoute = angular.module("budgeto.home.route", [
    "ngRoute"
]);

budgetoHomeRoute.config(["$routeProvider", function ($routeProvider) {
    $routeProvider
        .when("/home", {
            templateUrl: "components/home/home.html",
            controller: "HomeCtrl",
            reloadOnSearch: false
        })
        .when("/loading", {
            templateUrl: "components/loading/loading.html",
            controller: "LoadingCtrl"
        })
        .otherwise({redirectTo: "/home"});
}]);
