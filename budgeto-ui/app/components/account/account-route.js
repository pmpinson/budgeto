'use strict';

define(['angular', 'angular-ui-router'], function(angular) {

    var moduleDefinition = {
        name: 'budgeto.account.route',
        dependencies: [
            'ui.router'
        ],
        module: undefined
    };

    // Register angular module
    moduleDefinition.module = angular.module(moduleDefinition.name, moduleDefinition.dependencies);

    moduleDefinition.module.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('account', {
            templateUrl: 'components/account/account.html',
            controller: 'AccountCtrl as accountCtrl'
        });
        $stateProvider.state('account.list', {
            templateUrl: 'components/account/account-list.html',
            controller: 'AccountListCtrl as accountListCtrl'
        });
        $stateProvider.state('account.list.detail', {
            params: {
                name:undefined,
            },
            templateUrl: 'components/account/account-detail.html',
            controller: 'AccountDetailCtrl as accountDetailCtrl'
        });
    }]);

    return moduleDefinition;
});