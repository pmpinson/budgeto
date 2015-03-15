'use strict';

define(['angular', 'underscore', 'components/tools/utils', 'angular-ui-router', 'angular-bootstrap'], function(angular, _, utils) {

    var moduleDefinition = {
        name: 'budgeto.modalError',
        dependencies: [
            'ui.router',
            'ui.bootstrap',
            utils.name
        ],
        module: undefined
    };

    // Register angular module
    moduleDefinition.module = angular.module(moduleDefinition.name, moduleDefinition.dependencies);

    /**
     * controller for modal of global error message
     */
    moduleDefinition.module.controller('ModalErrorInstanceCtrl', ['$scope', '$log', '$modalError', 'modalOptions', '$utils', function ($scope, $log, $modalError, modalOptions, $utils) {
        $log.debug('budgeto.modalError : load ModalErrorInstanceCtrl');

        this.modalOptions = modalOptions;

        this.utils = $utils;

        this.close = function () {
            $modalError.close();
        };
    }]);

    /**
     * provider to manage modalError
     */
    moduleDefinition.module.provider('$modalError', function () {

        var defaultOptions = {
            logMessages: undefined,
            reason: undefined,
            title: 'Error',
            detail: 'detail',
            close: 'OK'
        };

        var $modalErrorProvider = {

            setDefaultOptions: function (value) {
                _.extend(defaultOptions, value);
            },

            $get: ['$log', '$modal', '$state', function ($log, $modal, $state) {
                $log.debug('budgeto.modalError : load $modalError');

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
                        controller: 'ModalErrorInstanceCtrl as modalErrorInstanceCtrl',
                        templateUrl: 'components/tools/modal-error.html',
                        resolve: {
                            modalOptions: function () {
                                return $modalError.prepareOptions(options);
                            }
                        }
                    });

                    modalInstance.result.then(function () {
                        $state.go('home');
                    }, function () {
                        $state.go('home');
                    });

                    return modalInstance;
                };

                $modalError.close = function () {
                    if (modalInstance !== undefined) {
                        modalInstance.dismiss('close');
                        modalInstance = undefined;
                    }
                };

                return $modalError;
            }]
        };

        return $modalErrorProvider;
    });

    return moduleDefinition;
});