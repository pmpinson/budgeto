'use strict';

console.info('budgeto : load');

// Budgeto app
var budgeto = angular.module('budgeto', [
    'ngRoute',
    'budgeto.apis',
    'budgeto.home',
    'budgeto.account'
]);

/**
 * configuration of moment timezone
 */
budgeto.constant('angularMomentConfig', {
    timezone: 'UTC'
});