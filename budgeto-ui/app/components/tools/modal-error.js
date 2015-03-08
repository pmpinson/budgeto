"use strict";

// Declare progress module
var budgetoModalError = angular.module("budgeto.modalError", [
    "ui.bootstrap"
]);

/**
 * controller for modal of global error message
 */
budgetoModalError.controller("ModalErrorInstanceCtrl", ["$scope", "$log", "$modalError", "modalOptions", function ($scope, $log, $modalError, modalOptions) {
    $log.debug("budgeto.modalError : load ModalErrorInstanceCtrl");

    this.modalOptions = modalOptions;

    this.isObject = function(val) {
        return typeof val === 'object';
    }

    this.formatObject = function(val) {
        return JSON.stringify(val, null, "    ");
    }

    this.close = function () {
        $modalError.close();
    };
}]);

/**
 * provider to manage modalError
 */
budgetoModalError.provider("$modalError", function () {

    var defaultOptions = {
        logMessages: undefined,
        reason: undefined,
        title: "Error oocured",
        message: "An error occured",
        close: "Close"
    };

    var $modalErrorProvider = {

        setDefaultOptions: function (value) {
            _.extend(defaultOptions, value);
        },

        $get: ["$log", "$modal", "$state", function ($log, $modal, $state) {
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

            $modalError.manageError = function () {
                var logMessages = Array.prototype.slice.call(arguments);
                return function(reason) {
                    $log.error(logMessages, reason);
                    $modalError.open({logMessages:logMessages, reason:reason});
                }
            }

            $modalError.open = function (options) {
                modalInstance = $modal.open({
                    controller: "ModalErrorInstanceCtrl as modalErrorInstanceCtrl",
                    templateUrl: "components/tools/modal-error.html",
                    resolve: {
                        modalOptions: function () {
                            return _.extend({}, defaultOptions, options);
                        }
                    }
                });

                modalInstance.result.then(function (selectedItem) {
                    $state.go("home");
                }, function () {
                    $state.go("home");
                });

                return modalInstance;
            };

            $modalError.close = function () {
                if (modalInstance !== undefined) {
                    modalInstance.dismiss("close");
                    modalInstance = undefined;
                }
            };

            return $modalError;
        }]
    };

    return $modalErrorProvider;
});