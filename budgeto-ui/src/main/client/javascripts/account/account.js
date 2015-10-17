'use strict';

define(['../../../target/dist/app/lib/angular/angular.min', '../apis/apis', '../tools/modal-error', 'angular-ui-router', 'angular-resource', 'angular-moment'],
    function(angular, apis, modalError) {

    /**
     * account api to store the api definition for account
     * @returns the api
     */
    function AccountApi($resource, $log, ApiService) {
        $log.debug('budgeto.account : load AccountApi');

        var api = ApiService.find('account');
        $log.debug('budgeto.account : api', api);

        return api;
    }

    /**
     * account ressource
     * @returns {{all: get all accounts, returning an array in a promise, operations: get all operation of an account, returning an array of operation in a promise}}
     */
    function AccountResource($resource, $log, AccountApi, ApiService, $modalError) {
        $log.debug('budgeto.account : load AccountResource');

        var accountResource = {};

        accountResource.all = function () {
            var url = AccountApi.href;
            return $resource(url, {}, {}).query({}).$promise.catch($modalError.manageError('error getting accounts'));
        };

        accountResource.operations = function (account) {
            var url = ApiService.getLink('operations', account.links).href;
            return $resource(url, {}, {}).query({}).$promise.catch($modalError.manageError('error getting operations for', account));
        };

        return accountResource;
    }

    /**
     * controller to manage account
     */
    function AccountCtrl($scope, $state, $log) {
        $log.debug('budgeto.account : load AccountCtrl');

        $state.go('account.list');

        this.home = function () {
            $state.go('home');
        };
    }

    /**
     * controller to manage account list
     */
    function AccountListCtrl($scope, $state, $log, AccountResource) {
        $log.debug('budgeto.account : load AccountListCtrl');

        var that = this;

        that.accounts = [];
        that.account = undefined;

        AccountResource.all().then(function (data) {
            $log.debug('budgeto.account : get all accounts', data);

            that.accounts = data;
            if (that.accounts.length !== 0) {
                that.account = that.accounts[0];
            }
        });

        $scope.$watch(
            function () {
                return that.account;
            },
            function () {
                if (that.account !== undefined) {
                    $log.debug('budgeto.account : select account', that.account);

                    $state.go('account.list.detail', {name:that.account.name});
                }
            }
        );
    }

    /**
     * controller to manage detail account
     */
    function AccountDetailCtrl($scope, $log, AccountResource) {
        $log.debug('budgeto.account : load AccountDetailCtrl');

        var that = this;

        that.account = $scope.accountListCtrl.account;
        that.operations = [];

        AccountResource.operations(that.account).then(function (data) {
            $log.debug('budgeto.account : get all operations', data);

            that.operations = data;
        });
    }

    // module definition
    var moduleDefinition = {
        name: 'budgeto.account',
        dependencies: [
            'ui.router',
            'ngResource',
            'angularMoment',
            apis.name,
            modalError.name
        ],
        module: undefined
    };

    moduleDefinition.module = angular.module(moduleDefinition.name, moduleDefinition.dependencies);

    AccountApi.$inject = ['$resource', '$log', 'ApiService'];
    moduleDefinition.module.factory('AccountApi', AccountApi);
    AccountResource.$inject = ['$resource', '$log', 'AccountApi', 'ApiService', '$modalError'];
    moduleDefinition.module.factory('AccountResource', AccountResource);
    AccountCtrl.$inject = ['$scope', '$state', '$log'];
    moduleDefinition.module.controller('AccountCtrl', AccountCtrl);
    AccountListCtrl.$inject = ['$scope', '$state', '$log', 'AccountResource'];
    moduleDefinition.module.controller('AccountListCtrl', AccountListCtrl);
    AccountDetailCtrl.inject = ['$scope', '$log', 'AccountResource'];
    moduleDefinition.module.controller('AccountDetailCtrl', AccountDetailCtrl);

    return moduleDefinition;
});