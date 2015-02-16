'use strict';

// Declare module
angular.module('budgeto.home', [
  'ngRoute',
  'ngResource',
  'budgeto.common'
])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {
    templateUrl: 'components/home/home.html',
    controller: 'HomeCtrl'
  });
}])

.controller('HomeCtrl', ['$scope', '$location', 'ApiService', HomeCtrl]);

/**
 * controller to manage home controoler
 * @param $scope current scope
 * @param $location location service
 */
function HomeCtrl($scope, $location, ApiService) {

  $scope.apis = ApiService.findAll();

  $scope.changePath = function(path) {
    $location.path('/' + path);
  }
};
