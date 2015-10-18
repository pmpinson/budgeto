'use strict';

define(['angular', 'angular-ui-router'], function(angular) {

    var moduleDefinition = {
        name: 'budgeto.home.route',
        dependencies: [
            'ui.router'
        ],
        module: undefined
    };

    // Register angular module
    moduleDefinition.module = angular.module(moduleDefinition.name, moduleDefinition.dependencies);

    moduleDefinition.module.config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('home', {
                templateUrl: 'template/home/home.html',
                controller: 'HomeCtrl as homeCtrl'
            });
    }]);

    return moduleDefinition;
});