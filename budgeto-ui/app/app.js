'use strict';

console.info('budgeto : load');

// Budgeto app
var budgeto = angular.module('budgeto', [
    'ngRoute',
    'budgeto.apis',
    'budgeto.loading',
    'budgeto.home',
    'budgeto.account'
]);

/**
 * configuration of moment timezone
 */
budgeto.constant('angularMomentConfig', {
    timezone: 'UTC'
});

budgeto.run(['$location', BudgetoRun]);

function BudgetoRun($location) {
    console.info('budgeto : run');

    $location.path('/loading');
}