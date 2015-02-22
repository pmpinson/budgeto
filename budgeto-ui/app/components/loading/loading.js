'use strict';

// Declare module
var budgetoLoading= angular.module('budgeto.loading', [
    'ngRoute',
    'ngResource',
    'ui.bootstrap',
    'budgeto.infiniteLoader',
    'budgeto.apis'
]);

budgetoLoading.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/loading', {
            templateUrl: 'components/loading/loading.html',
            controller: 'LoadingCtrl'
        });
}]);

/**
 * controller to manage loading page
 */
budgetoLoading.controller('LoadingCtrl', ['$scope', '$location', '$log', 'ApiService', '$infiniteLoader', '$timeout', function($scope, $location, $log, ApiService, $infiniteLoader, $timeout) {
    $log.debug('budgeto.loading : load LoadingCtrl');

    $scope.loadFail = false;
    var sourcePage = $location.search().sourcePage;
    if (sourcePage === undefined || sourcePage === null || sourcePage.indexOf('/loading') !== -1) {
        sourcePage = '/';
    }
    $location.search('sourcePage', null);

    $infiniteLoader.show();

    ApiService.loaded().then(function(data){
        $timeout(function(){
            $log.debug('budgeto.loading : loading done');
            $infiniteLoader.hide();
            $location.path(sourcePage);
        } , 1000);
    }).catch(function(error){
        $log.error('error getting apis / ', reason);
        $scope.loadFail = true;
        $infiniteLoader.hide();
    });
}]);