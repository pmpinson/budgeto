'use strict';

// Declare module
var budgetoHome = angular.module('budgeto.home', [
    'ngRoute',
    'ngResource',
    'budgeto.apis'
]);

budgetoHome.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/home', {
            templateUrl: 'components/home/home.html',
            controller: 'HomeCtrl'
        })
        .otherwise({redirectTo: '/home'});
}]);

/**
 * controller to manage home page
 */
budgetoHome.controller('HomeCtrl', ['$scope', '$location', '$log', 'ApiService', function($scope, $location, $log, ApiService) {
    $log.debug('budgeto.home : load HomeCtrl');

    $scope.apis = ApiService.findAll();

    $scope.changePath = function (path) {
        $location.path('/' + path);
    }
}]);
