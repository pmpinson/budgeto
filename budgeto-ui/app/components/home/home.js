'use strict';

// Declare module
angular.module('budgeto.home', [
    'ngRoute',
    'ngResource',
    'budgeto.common'
])

    .config(['$routeProvider', function ($routeProvider) {
        console.info("home : load $routeProvider");

        $routeProvider.when('/home', {
            templateUrl: 'components/home/home.html',
            controller: 'HomeCtrl'
        });
    }])

    .controller('HomeCtrl', ['$scope', '$location', 'ApiService', 'ProgressLoader', HomeCtrl]);

/**
 * controller to manage home controoler
 * @param $scope current scope
 * @param $location location service
 */
function HomeCtrl($scope, $location, ApiService, ProgressLoader) {
    console.info("home : load HomeCtrl");

    //ApiService.load().then(function (result) {
        $scope.apis = ApiService.findAll();
    //    ProgressLoader.hide();
    //});

    $scope.changePath = function (path) {
        $location.path('/' + path);
    }
};
