'use strict';

// Budgeto app
var budgeto = angular.module('budgeto', [
    'ngRoute',
    'budgeto.infiniteLoader',
    'budgeto.apis',
    'budgeto.loading',
    'budgeto.home',
    'budgeto.account'
]);

/**
 * rest api url
 */
budgeto.constant('BudgetoRestApiURL', 'http://localhost:9001/budgeto-api');

/**
 * message
 */
budgeto.constant('MessageService', {
        applicationInit : 'Wait for application loading',
        applicationInitFail: 'Erreur during initialisation. Come back later. So Sorry...',
        infiniteLoaderMsg: 'Work in progress. Pleas wait...',
        apisLinks: {
            account : 'Go manage your accounts',
            budget: 'Go to prepare your budget'
        },
        apisTitles: {
            account : 'Manage your accounts',
            budget: 'Prepare your budget'
        },
        homeTitle: 'Welcome to budgeto',
        homeLink: 'Go back to home',
        errorTitle: 'Error',
        error: 'An error occured, please advice us.',
        closeTitle: 'Close'
    });

/**
 * configuration of moment timezone
 */
budgeto.constant('angularMomentConfig', {
    timezone: 'UTC'
});

budgeto.config(['$infiniteLoaderProvider', 'MessageService', function($infiniteLoaderProvider, MessageService) {
  $infiniteLoaderProvider.setMessage(MessageService.infiniteLoaderMsg);
}]);

/**
 * BudgetoRun : call to the init app page
 */
budgeto.run(['$location', '$rootScope', '$log', 'MessageService', function($location, $rootScope, $log, MessageService) {
    $log.debug('budgeto : run');

    $location.search('sourcePage', $location.path());

    $location.path('/loading');

    $rootScope.MessageService = MessageService;
}]);