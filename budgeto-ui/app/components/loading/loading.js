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
    console.info('budgeto.loading : load $routeProvider');

    $routeProvider
        .when('/loading', {
            templateUrl: 'components/loading/loading.html',
            controller: 'WaitCtrl'
        });
}]);

budgetoLoading.controller('WaitCtrl', ['$scope', '$location', 'ApisLoader', 'InfiniteLoader', '$timeout', LoadingCtrl]);

/**
 * controller to manage loading page
 * @param $scope
 * @param $location
 * @param $timeout
 * @param ApisLoader
 * @param InfiniteLoader
 * @constructor
 */
function LoadingCtrl($scope, $location, ApisLoader, InfiniteLoader, $timeout) {
    console.info('budgeto.loading : load LoadingCtrl');

    $scope.loadFail = false;
    var sourcePage = $location.search().sourcePage;
    if (sourcePage !== undefined && sourcePage !== null && sourcePage.indexOf('/loading') !== -1) {
        sourcePage = '/';
    }
    $location.search('sourcePage', null);

    InfiniteLoader.show();

    ApisLoader.load().then(function(data){
        $timeout(function(){
            console.info('budgeto.loading : loading done');
            InfiniteLoader.hide();
            $location.path(sourcePage);

        } , 1000);
    }).catch(function(error){
        $scope.loadFail = true;
        InfiniteLoader.hide();
    });
};
