'use strict';

// Declare module
var budgetoLoading= angular.module('budgeto.loading', [
    'ngRoute',
    'ngResource',
    'budgeto.apis'
]);

budgetoLoading.config(['$routeProvider', function ($routeProvider) {
    console.info('budgeto.loading : load $routeProvider');

    $routeProvider
        .when('/loading', {
            templateUrl: 'components/loading/loading.html',
            controller: 'WaitCtrl'
        });
}]);

budgetoLoading.controller('WaitCtrl', ['$scope', '$location', '$timeout', 'ApisLoader', 'ProgressLoader', LoadingCtrl]);

/**
 * controller to manage loading page
 * @param $scope
 * @param $location
 * @param $timeout
 * @param ApisLoader
 * @param ProgressLoader
 * @constructor
 */
function LoadingCtrl($scope, $location, $timeout, ApisLoader, ProgressLoader) {
    console.info('budgeto.loading : load LoadingCtrl');

    ProgressLoader.show();

    ApisLoader.load().then(function(data){
        $timeout(function(){
            console.info('budgeto.loading : loadging done');
            ProgressLoader.hide();
            $location.path('/home');
        }, 500);
    });
};
