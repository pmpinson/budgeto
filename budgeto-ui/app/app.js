'use strict';

// Budgeto app
var budgeto = angular.module('budgeto', [
    'ngRoute',
    'budgeto.infiniteLoader',
    'budgeto.loading',
    'budgeto.home',
    'budgeto.account',
    'budgeto.analytics'
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
 * message
 */
budgeto.constant('MessageService', {
    applicationInit: 'Wait for application loading',
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
    errorTitle: 'Error',
    error: 'An error occured, please advice us.',
    closeTitle: 'Close'
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
 * BudgetoRun : call to the init app page
 */
budgeto.run(['$location', '$rootScope', '$log', 'MessageService', function ($location, $rootScope, $log, MessageService) {
    $log.debug('budgeto : run');

    $rootScope.MessageService = MessageService;

    $location.search('sourcePage', $location.path());
    $location.path('/loading');
}]);