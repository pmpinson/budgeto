'use strict';

console.info('app : load');

// Declare app level module which depends on views, and components
angular.module('budgeto', [
    'ngRoute',
    'budgeto.common',
    'budgeto.home',
    'budgeto.account'
])

    .constant('angularMomentConfig', {
        timezone: 'UTC'
    })

    .config(['$routeProvider', function ($routeProvider) {
        console.info('app : load $routeProvider');

        $routeProvider.otherwise({redirectTo: '/home'});
    }])

    .run(['ApiService', 'ProgressLoader', AppRun]);

function AppRun(ApiService, ProgressLoader) {
    console.info('app : load AppRun');

    ApiService.load().then(function (result) {
        ProgressLoader.hide();
    });
}