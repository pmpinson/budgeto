"use strict";

// Declare module
var budgetoHome = angular.module("budgeto.home", [
    "ui.router",
    "ngResource",
    "budgeto.apis"
]);

budgetoHome.config(["$stateProvider", "$urlRouterProvider", function ($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state("home", {
            url: "/",
            templateUrl: "components/home/home.html",
            controller: "HomeCtrl"
        });
    $urlRouterProvider.otherwise("home");
}]);

/**
 * controller to manage home page
 */
budgetoHome.controller("HomeCtrl", ["$scope", "$state", "$log", "ApiService", function ($scope, $state, $log, ApiService) {
    $log.debug("budgeto.home : load HomeCtrl");

    $scope.apis = ApiService.findAll();

    $scope.changePath = function (path) {
        if (path === undefined) {
            $state.go("home");
        } else {
            $state.go(path);
        }
    };
}]);
