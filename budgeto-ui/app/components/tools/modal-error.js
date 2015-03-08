"use strict";

// Declare progress module
var budgetoModalError = angular.module("budgeto.modalError", [
    "ui.router",
    "ui.bootstrap",
    "budgeto.utils"
]);

/**
 * controller for modal of global error message
 */
budgetoModalError.controller("ModalErrorInstanceCtrl", ["$scope", "$log", "$modalError", "modalOptions", "$utils", function ($scope, $log, $modalError, modalOptions, $utils) {
    $log.debug("budgeto.modalError : load ModalErrorInstanceCtrl");

    this.modalOptions = modalOptions;

    this.utils = $utils;

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
        title: "Error",
        detail: "detail",
        close: "OK"
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
                    getDefaultOptions: function () {
                        return defaultOptions;
                    }
                };
            };

            $modalError.manageError = function () {
                var logMessages = Array.prototype.slice.call(arguments);
                return function(reason) {
                    $log.error(logMessages, reason);
                    $modalError.open({logMessages:logMessages, reason:reason});
                };
            };

            $modalError.prepareOptions = function(options) {
                return _.extend({}, defaultOptions, options);
            };

            $modalError.open = function (options) {
                modalInstance = $modal.open({
                    controller: "ModalErrorInstanceCtrl as modalErrorInstanceCtrl",
                    templateUrl: "components/tools/modal-error.html",
                    resolve: {
                        modalOptions: function () {
                            return $modalError.prepareOptions(options);
                        }
                    }
                });

                modalInstance.result.then(function () {
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