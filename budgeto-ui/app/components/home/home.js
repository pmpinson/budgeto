"use strict";

// Declare module
var budgetoHome = angular.module("budgeto.home", [
    "ngResource",
    "budgeto.apis",
    "budgeto.loading",
    "budgeto.infiniteLoader"
]);

/**
 * controller to manage home page
 */
budgetoHome.controller("HomeCtrl", ["$scope", "$location", "$log", "ApiService", "$infiniteLoader", "LoadingService", function ($scope, $location, $log, ApiService, $infiniteLoader, LoadingService) {
    $log.debug("budgeto.home : load HomeCtrl");

    $infiniteLoader.show();
    $scope.loadFail = false;

    var sourcePage = $location.search().sourcePage;
    $location.search("sourcePage", null);

    LoadingService.loaded().then(function (data) {
        $log.debug("budgeto.loading : loading done");
        $infiniteLoader.hide();
        $scope.apis = ApiService.findAll();
        if (sourcePage !== undefined) {
            $location.path(sourcePage);
        }
        return data;
    }).catch(function (reason) {
        $log.error("error getting apis /", reason);
        $scope.loadFail = true;
        $infiniteLoader.hide();
    });

    $scope.changePath = function (path) {
        if (path === undefined) {
            $location.path();
        } else {
            $location.path("/" + path);
        }
    };
}]);
