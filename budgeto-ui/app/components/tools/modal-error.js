"use strict";

// Declare progress module
var budgetoModalError = angular.module("budgeto.modalError", [
    "ui.bootstrap"
]);

/**
 * controller for modal of global error message
 */
budgetoModalError.controller("ModalErrorInstanceCtrl", ["$scope", "$log", "$modalError", function ($scope, $log, $modalError) {
    $log.debug("budgeto.modalError : load ModalErrorInstanceCtrl");

    $scope.close = function () {
        $modalError.close();
    };
}]);

/**
 * provider to manage modalError
 */
budgetoModalError.provider("$modalError", function () {
    var message = {
        title: "Error oocured",
        message: "An error occured",
        close: "Close"
    };

    var $modalErrorProvider = {

        setMessage: function (value) {
            message = value;
        },

        $get: ["$log", "$modal", "$location", function ($log, $modal, $location) {
            $log.debug("budgeto.modalError : load $modalError");

            var modalInstance;
            var $modalError = {};

            $modalError.config = function () {
                return {
                    getMessage: function () {
                        return message;
                    }
                };
            };

            $modalError.open = function () {
                modalInstance = $modal.open({
                    controller: "ModalErrorInstanceCtrl",
                    template: '<div>' +
                    '<div class="modal-header"><h3 class="modal-title">' + message.title + '</h3></div>' +
                    '<div class="modal-body"><p>' + message.message + '</p></div>' +
                    '<div class="modal-footer"><button class="btn btn-primary" ng-click="close()">' + message.close + '</button></div>' +
                    '</div>'
                });
                return modalInstance;
            };

            $modalError.close = function () {
                if (modalInstance !== undefined) {
                    modalInstance.dismiss("close");
                    $location.path("/");
                    modalInstance = undefined;
                }
            };

            return $modalError;
        }]
    };

    return $modalErrorProvider;
});