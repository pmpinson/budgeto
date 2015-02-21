'use strict';

console.info('budgeto : load');

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
        homeLink: 'Go back to home'
    });

/**
 * configuration of moment timezone
 */
budgeto.constant('angularMomentConfig', {
    timezone: 'UTC'
});

budgeto.run(['$location', '$rootScope', 'MessageService', BudgetoRun]);

/**
 * BudgetoRun : call to the init app page
 * @param $location
 * @constructor
 */
function BudgetoRun($location, $rootScope, MessageService) {
    console.info('budgeto : run');

    $location.search('sourcePage', $location.path());

    $location.path('/loading');

    $rootScope.MessageService = MessageService;
}