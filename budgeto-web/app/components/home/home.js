'use strict';

// Declare module
angular.module('budgeto.home', [
  'ngRoute'
])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {
    templateUrl: 'components/home/home.html',
    controller: 'HomeCtrl'
  });
}])

.controller('HomeCtrl', ['$scope', '$location', HomeCtrl]);

/**
 * controller to manage home controoler
 * @param $scope current scope
 * @param $location location service
 */
function HomeCtrl($scope, $location) {

  $scope.account = function() {
    $location.path('/account');
  }
};
