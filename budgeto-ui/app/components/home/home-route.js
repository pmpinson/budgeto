"use strict";

// Declare module
var budgetoHomeRoute = angular.module("budgeto.home.route", [
    "ngRoute"
]);

budgetoHomeRoute.config(["$routeProvider", function ($routeProvider) {
    $routeProvider
        .when("/home", {
            templateUrl: "components/home/home.html",
            controller: "HomeCtrl"
        })
        .otherwise({redirectTo: "/home"});
}]);
