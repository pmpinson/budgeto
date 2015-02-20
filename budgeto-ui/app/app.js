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
 * configuration of moment timezone
 */
budgeto.constant('angularMomentConfig', {
    timezone: 'UTC'
});

budgeto.run(['$location', '$rootScope', BudgetoRun]);

/**
 * BudgetoRun : call to the init app page
 * @param $location
 * @constructor
 */
function BudgetoRun($location, $rootScope) {
    console.info('budgeto : run');

    $location.path('/loading');
}