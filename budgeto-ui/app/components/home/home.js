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

budgetoHome.controller('HomeCtrl', ['$scope', '$location', 'ApisService', 'ProgressLoader', HomeCtrl]);

/**
 * controller to manage home page
 * @param $scope current scope
 * @param $location location service
 */
function HomeCtrl($scope, $location, ApisService, ProgressLoader) {
    console.info('budgeto.home : load HomeCtrl');

    $scope.apis = ApisService.findAll();

    $scope.changePath = function (path) {
        $location.path('/' + path);
    }
};
