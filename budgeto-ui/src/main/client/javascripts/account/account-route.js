'use strict';

define(['angular', 'angular-ui-router'], function(angular) {

    /**
     * route definition for account
     */
    function AccountRouteDefinition($stateProvider) {
        $stateProvider.state('account', {
            templateUrl: 'javascripts/account/account.html',
            controller: 'AccountCtrl as accountCtrl'
        });
        $stateProvider.state('account.list', {
            templateUrl: 'javascripts/account/account-list.html',
            controller: 'AccountListCtrl as accountListCtrl'
        });
        $stateProvider.state('account.list.detail', {
            params: {
                name:undefined
            },
            templateUrl: 'javascripts/account/account-detail.html',
            controller: 'AccountDetailCtrl as accountDetailCtrl'
        });
    }

    // module definition
    var moduleDefinition = {
        name: 'budgeto.account.route',
        dependencies: [
            'ui.router'
        ],
        module: undefined
    };

    moduleDefinition.module = angular.module(moduleDefinition.name, moduleDefinition.dependencies);

    AccountRouteDefinition.$inject = ['$stateProvider'];
    moduleDefinition.module.config(AccountRouteDefinition);

    return moduleDefinition;
});