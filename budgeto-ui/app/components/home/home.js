"use strict";

// Declare module
var budgetoHome = angular.module("budgeto.home", [
    "ngResource",
    "budgeto.apis"
]);

/**
 * controller to manage home page
 */
budgetoHome.controller("HomeCtrl", ["$scope", "$location", "$log", "ApiService", function ($scope, $location, $log, ApiService) {
    $log.debug("budgeto.home : load HomeCtrl");

    $scope.apis = ApiService.findAll();

    $scope.changePath = function (path) {
        if (path === undefined) {
            $location.path();
        } else {
            $location.path("/" + path);
        }
    };
}]);
