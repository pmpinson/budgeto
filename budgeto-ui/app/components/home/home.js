"use strict";

// Declare module
var budgetoHome = angular.module("budgeto.home", [
    "ui.router",
    "ngResource",
    "budgeto.apis",
    "budgeto.loading",
    "budgeto.infiniteLoader"
]);

/**
 * controller to manage home page
 */
budgetoHome.controller("HomeCtrl", ["$scope", "$state", "$log", "ApiService", "$infiniteLoader", "LoadingService", function ($scope, $state, $log, ApiService, $infiniteLoader, LoadingService) {
    $log.debug("budgeto.home : load HomeCtrl");

    var that = this;

    $infiniteLoader.show();
    this.loadFail = false;

    LoadingService.loaded().then(function (data) {
        $log.debug("budgeto.loading : loading done");
        $infiniteLoader.hide();
        that.apis = ApiService.findAll();
        return data;
    }).catch(function (reason) {
        $log.error("error getting apis /", reason);
        that.loadFail = true;
        $infiniteLoader.hide();
    });

    this.changePath = function (path) {
        var destination = path;
        if (destination === undefined) {
            destination = "home";
        }
        try {
            return $state.go(destination);
        } catch (exception) {
            $log.error("unknown path", path, ":", exception);
            return $state.go("home");
        }
    };
}]);
