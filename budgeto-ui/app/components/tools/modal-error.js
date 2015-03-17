'use strict';

define(['angular', 'underscore', 'components/tools/utils', 'angular-ui-router', 'angular-bootstrap'], function(angular, _, utils) {

    // module definition
    var moduleDefinition = {
        name: 'budgeto.modalError',
        dependencies: [
            'ui.router',
            'ui.bootstrap',
            utils.name
        ],
        module: undefined
    };
    moduleDefinition.module = angular.module(moduleDefinition.name, moduleDefinition.dependencies);

    /**
     * controller for modal of global error message
     * keep util in utils var
     * method close to close the modal instance
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
     * show a modal with define content to show errors during execution
     * available config : setDefaultOptions to define default messages : var defaultOptions = {logMessages: default error message,reason: default reason,title: modal title,detail: collapse detail title,close: 'button close title}
     * config : method to get the config : return {getDefaultOptions()}
     * prepareOptions : internal method to extends defaultOptions with other object and override messages
     * open : open the modal, can path object to override default options
     * close : close the modal instance
     * manageError : callback function to be use with catch of promise
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

                    // callback when modal close to go home in all case : close button, esc key, click on background
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