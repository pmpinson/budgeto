"use strict";

// Declare module
var budgetoLoadingRoute = angular.module("budgeto.loading.route", [
    "ngRoute"
]);

budgetoLoadingRoute.config(["$routeProvider", function ($routeProvider) {
    $routeProvider
        .when("/loading", {
            templateUrl: "components/loading/loading.html",
            controller: "LoadingCtrl",
            reloadOnSearch: false
        });
}]);
