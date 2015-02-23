'use strict';

// Declare module
var budgetoLoading= angular.module('budgeto.loading', [
    'ngRoute',
    'ngResource',
    'ui.bootstrap',
    'budgeto.infiniteLoader'
]);

budgetoLoading.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/loading', {
            templateUrl: 'components/loading/loading.html',
            controller: 'LoadingCtrl',
            reloadOnSearch: false
        });
}]);

/**
 * provider to manage loading of application
 */
budgetoApis.provider('LoadingService', function() {
    var servicesNames = [];

    var $loadingServiceProvider = {

      add: function(value) {
        servicesNames.push(value);
      },

      $get: ['$log', '$q', '$injector', function ($log, $q, $injector) {
          $log.debug('budgeto.loading : load LoadingService');

          var $loadingService = {};
          var promise;

          if (servicesNames.length !== 0) {

              var servicesPromises = [];
              for (var key in servicesNames) {
                servicesPromises.push($injector.get(servicesNames[key]).$loaded());
              }
              promise = $q.all(servicesPromises);
          } else {
              var deferred = $q.defer();
              promise = deferred.promise;
              deferred.resolve(true);
          }

              $loadingService.config = function () {
                return {
                  getServicesNames: function() {
                    return servicesNames;
                  }
                };
              };

            $loadingService.$loaded = function() {
                return promise;
            };

          return $loadingService;
        }]
    };

    return $loadingServiceProvider;
});

/**
 * controller to manage loading page
 */
budgetoLoading.controller('LoadingCtrl', ['$scope', '$location', '$log', 'LoadingService', '$infiniteLoader', '$timeout', function($scope, $location, $log, LoadingService, $infiniteLoader, $timeout) {
    $log.debug('budgeto.loading : load LoadingCtrl');

    $scope.loadFail = false;
    var sourcePage = $location.search().sourcePage;
    if (sourcePage === undefined || sourcePage === null || sourcePage.indexOf('/loading') !== -1) {
        sourcePage = '/';
    }
    $location.search('sourcePage', null);

    $infiniteLoader.show();

    LoadingService.$loaded().then(function(data){
        $timeout(function(){
            $log.debug('budgeto.loading : loading done');
            $infiniteLoader.hide();
            $location.path(sourcePage);
        } , 1000);
    }).catch(function(reason){
        $log.error('error getting apis / ', reason);
        $scope.loadFail = true;
        $infiniteLoader.hide();
    });
}]);