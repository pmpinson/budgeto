'use strict';

// Declare progress module
var budgetoModalError = angular.module('budgeto.modalError', [
    'ui.bootstrap'
]);

budgetoModalError.provider('$modalError', ModalErrorProvider);

budgetoModalError.controller('ModalErrorInstanceCtrl', ['$scope', '$modalInstance', '$location', ModalErrorInstanceCtrl]);

function ModalErrorInstanceCtrl($scope, $modalInstance, $location) {

  $scope.close = function () {
    $modalInstance.dismiss('close');
    $location.path("/");
  };
}

function ModalErrorProvider() {
    console.info('budgeto.modalError : load $modalErrorProvider');

    var $modalErrorProvider = {
      $get: ['$modal', function ($modal) {
          console.info('budgeto.modalError : load $modalError');

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
}