'use strict';

// Declare module
angular.module('budgeto.home', [
  'ngRoute',
  'ngResource',
  'budgeto.common'
])

.config(['$routeProvider', function($routeProvider) {
    console.info("home : load $routeProvider");

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
    console.info("home : load HomeCtrl");

  ApiService.findAll().then(function(result){$scope.apis = result;});
//  ApiService.findAll2().then(function(result){$scope.lst = result;});
  $scope.lst = ApiService.findAll2();
  ApiService.find('account').then(function(result){console.log('account api ', result)});

  $scope.changePath = function(path) {
    $location.path('/' + path);
  }
};
