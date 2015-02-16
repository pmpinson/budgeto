'use strict';

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

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/home'});
}]);
