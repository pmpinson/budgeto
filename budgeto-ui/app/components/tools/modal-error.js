'use strict';

// Declare progress module
var budgetoModalError = angular.module('budgeto.modalError', [
    'ui.bootstrap'
]);

budgetoModalError.factory('ModalError', ['$modal', ModalError]);

budgetoModalError.directive('modalerror', [function() {
    return {
        restrict: 'A'
    }

}]);

budgetoModalError.controller('ModalErrorInstanceCtrl', ['$scope', '$modalInstance', '$location', ModalErrorInstanceCtrl]);

/**
 * modal error factory
 * define the modal and open if, the close automaticaly redirect to home
 * @constructor
 */
function ModalError($modal) {
    console.info('budgeto.modalError : load InfiniteLoader');

    return {
        open: function (size) {
          var modalInstance = $modal.open({
                controller: 'ModalErrorInstanceCtrl',
                template: '<div>'
                     + '<div class="modal-header"><h3 class="modal-title">{{MessageService.errorTitle}}</h3></div>'
                     + '<div class="modal-body"><p>{{MessageService.error}}</p></div>'
                     + '<div class="modal-footer"><button class="btn btn-warning" ng-click="close()">{{MessageService.closeTitle}}</button></div>'
                 + '</div>'
          });
        }
    };
}

function ModalErrorInstanceCtrl($scope, $modalInstance, $location) {

  $scope.close = function () {
    $modalInstance.dismiss('close');
    $location.path("/");
  };
}