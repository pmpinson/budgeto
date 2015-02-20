'use strict';

// Declare module
var budgetoHome = angular.module('budgeto.home', [
    'ngRoute',
    'ngResource',
    'budgeto.apis'
]);

budgetoHome.config(['$routeProvider', function ($routeProvider) {
    console.info('budgeto.home : load $routeProvider');

    $routeProvider
        .when('/home', {
            templateUrl: 'components/home/home.html',
            controller: 'HomeCtrl'
        })
        .otherwise({redirectTo: '/home'});
}]);

budgetoHome.controller('HomeCtrl', ['$scope', '$location', 'ApisService', HomeCtrl]);

/**
 * controller to manage home page
 * @param $scope
 * @param $location
 * @param ApisService
 * @param ProgressLoader
 * @constructor
 */
function HomeCtrl($scope, $location, ApisService) {
    console.info('budgeto.home : load HomeCtrl');

    $scope.apis = ApisService.findAll();

    $scope.changePath = function (path) {
        $location.path('/' + path);
    }
};
