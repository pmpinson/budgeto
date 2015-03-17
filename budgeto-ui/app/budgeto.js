'use strict';

define(['angular', 'components/apis/apis', 'components/tools/infinite-loader', 'components/tools/modal-error',
    'components/home/loading', 'components/home/home', 'components/home/home-route', 'components/account/account', 'components/account/account-route'
    ], function(angular, apis, infiniteLoader, modalError, loading, home, homeRoute, account, accountRoute) {

    // Register angular module
    var budgeto = angular.module('budgeto', [
        apis.name,
        infiniteLoader.name,
        modalError.name,
        loading.name,
        home.name,
        homeRoute.name,
        account.name,
        accountRoute.name
    ]);

    /**
     * rest api url
     */
    budgeto.constant('BudgetoRestApiURL', 'http://localhost:9001/budgeto-api');

    /**
     * configuration of moment timezone
     */
    budgeto.constant('angularMomentConfig', {
        timezone: 'UTC'
    });

    /**
     * messages
     */
    budgeto.constant('MessageService', {
        applicationInitFail: 'Erreur during initialisation. Come back later. So Sorry...',
        infiniteLoaderMsg: 'Work in progress. Pleas wait...',
        apisLinks: {
            account: 'Go manage your accounts',
            budget: 'Go to prepare your budget'
        },
        apisTitles: {
            account: 'Manage your accounts',
            budget: 'Prepare your budget'
        },
        homeTitle: 'Welcome to budgeto',
        homeLink: 'Go back to home',
        modalError: {
           title: 'An error occured, please advice us.',
           detail: 'Error detail',
           close: 'Close'
       }
    });

    /**
     * config of apis provider
     */
    budgeto.config(['ApiServiceProvider', 'BudgetoRestApiURL', function (ApiServiceProvider, BudgetoRestApiURL) {
        ApiServiceProvider.setUrl(BudgetoRestApiURL);
    }]);

    /**
     * config message for infinite loader
     */
    budgeto.config(['$infiniteLoaderProvider', 'MessageService', function ($infiniteLoaderProvider, MessageService) {
        $infiniteLoaderProvider.setMessage(MessageService.infiniteLoaderMsg);
    }]);

    /**
     * config service to have been loaded
     */
    budgeto.config(['LoadingServiceProvider', function (LoadingServiceProvider) {
        LoadingServiceProvider.add('ApiService');
    }]);

    /**
     * config modal error message
     */
    budgeto.config(['$modalErrorProvider', 'MessageService', function ($modalErrorProvider, MessageService) {
        $modalErrorProvider.setDefaultOptions(MessageService.modalError);
    }]);

    /**
     * BudgetoRun : call to the init app page
     */
    budgeto.run(['$state', '$rootScope', '$log', 'MessageService', function ($state, $rootScope, $log, MessageService) {
        $log.debug('budgeto : run');

        $rootScope.MessageService = MessageService;

        $state.transitionTo('home');
    }]);
});