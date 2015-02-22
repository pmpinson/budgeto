'use strict';

// Declare progress module
var budgetoModalError = angular.module('budgeto.modalError', [
    'ui.bootstrap'
]);

/**
 * controller for modal of global error message
 */
budgetoModalError.controller('ModalErrorInstanceCtrl', ['$scope', '$log', '$modalInstance', '$location', function($scope, $log, $modalInstance, $location) {
 $log.debug('budgeto.modalError : load ModalErrorInstanceCtrl');

  $scope.close = function () {
    $modalInstance.dismiss('close');
    $location.path("/");
  };
}]);

/**
 * provider to manage modalError
 */
budgetoModalError.provider('$modalError', function() {
    var $modalErrorProvider = {
      $get: ['$log', '$modal', function ($log, $modal) {
          $log.debug('budgeto.modalError : load $modalError');

          var $modalError = {};

            $modalError.open = function() {
              $modal.open({
                    controller: 'ModalErrorInstanceCtrl',
                    template: '<div>'
                         + '<div class="modal-header"><h3 class="modal-title">{{MessageService.errorTitle}}</h3></div>'
                         + '<div class="modal-body"><p>{{MessageService.error}}</p></div>'
                         + '<div class="modal-footer"><button class="btn btn-primary" ng-click="close()">{{MessageService.closeTitle}}</button></div>'
                     + '</div>'
              });
            };

          return $modalError;
        }]
    };

    return $modalErrorProvider;
});