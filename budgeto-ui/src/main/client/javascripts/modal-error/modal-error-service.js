    var defaultOptions = {
        logMessages: undefined,
        reason: undefined,
        title: 'Error',
        detail: 'Details',
        close: 'Close'
    };

    var modalErrorProvider = {

        setDefaultOptions: function (value) {
            _.extend(defaultOptions, value);
        },

        $get: ['$log', '$uibModal', function ($log, $uibModal) {

            var modalInstance;
            var modalError = {};

            modalError.config = function () {
                return {
                    getDefaultOptions: function () {
                        return defaultOptions;
                    }
                };
            };

            modalError.manageError = function () {
                var logMessages = Array.prototype.slice.call(arguments);
                return function (reason) {
                    if (_.isFunction(_.first(logMessages))) {
                        _.first(logMessages)();
                        logMessages = _.drop(logMessages);
                    }

                    if (reason != undefined) {
                        if (_.isObject(reason.data) && !_.isArray(reason.data)) {
                            logMessages.push(reason.data.message);
                        }

                        if (_.isArray(reason.data)) {
                            logMessages = logMessages.concat(_.pluck(reason.data, 'message'));
                        }
                    }

                    $log.error(logMessages, reason);
                    modalError.open({logMessages: logMessages, reason: reason});
                };
            };

            modalError.prepareOptions = function (options) {
                var newOptions = _.extend({}, defaultOptions, options);
                if (!_.isUndefined(newOptions.logMessages) && !_.isArray(newOptions.logMessages)) {
                    newOptions.logMessages = [newOptions.logMessages];
                }
                return newOptions;
            };

            modalError.open = function (options) {
                modalInstance = $uibModal.open({
                    controller: 'ModalErrorController as modalErrorCtrl',
                    templateUrl: 'views/modalError.html',
                    backdrop: 'static',
                    resolve: {
                        modalOptions: function () {
                            return modalError.prepareOptions(options);
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

            return modalError;
        }]
    };
