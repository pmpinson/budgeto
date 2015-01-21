'use strict';

// Declare app level module which depends on views, and components
angular.module('budgeto', [
  'ngRoute',
  'budgeto.home',
  'budgeto.account'
])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/home'});
}]);
