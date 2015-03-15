"use strict";

define(['angular', 'components/apis/apis', 'components/tools/infinite-loader', 'components/home/loading', 'angular-ui-router'], function(angular, apis, infiniteLoader, loading) {

    var moduleDefinition = {
        name: "budgeto.home",
        dependencies: [
            "ui.router",
            apis.name,
            infiniteLoader.name,
            loading.name
        ],
        module: undefined
    };

    // Register angular module
    moduleDefinition.module = angular.module(moduleDefinition.name, moduleDefinition.dependencies);

    /**
     * controller to manage home page
     */
    moduleDefinition.module.controller("HomeCtrl", ["$scope", "$state", "$log", "ApiService", "$infiniteLoader", "LoadingService", function ($scope, $state, $log, ApiService, $infiniteLoader, LoadingService) {
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

    return moduleDefinition;
});