import _ from 'lodash';

/**
 * Manage erros with a modal
 */
class ModalErrorService {

    constructor($log, $uibModal, $state) {
        this.$log = $log;
        this.$uibModal = $uibModal;
        this.$state = $state;

        this.defaultOptions = {
            logMessages: undefined,
            reason: undefined,
            title: 'Error',
            detail: 'Details',
            close: 'Close'
        };
    }

    getDefaultOptions() {
        return this.defaultOptions;
    }

    manageError() {
        var self = this;
        var logMessages = Array.prototype.slice.call(arguments);

        return function (reason) {
            if (_.isFunction(_.first(logMessages))) {
                _.first(logMessages)();
                logMessages = _.drop(logMessages);
            }

            if (!_.isUndefined(reason)) {
                if (_.isObject(reason.data) && !_.isArray(reason.data)) {
                    logMessages.push(reason.data.message);
                }

                if (_.isArray(reason.data)) {
                    logMessages = logMessages.concat(_.pluck(reason.data, 'message'));
                }
            }

            self.$log.error(logMessages, reason);
            self.open({logMessages: logMessages, reason: reason});
        };
    }

    prepareOptions(options) {
        var newOptions = _.extend({}, this.defaultOptions, options);

        if (!_.isUndefined(newOptions.logMessages) && !_.isArray(newOptions.logMessages)) {
            newOptions.logMessages = [newOptions.logMessages];
        }

        return newOptions;
    }

    open(options) {
        var self = this;
        var modalInstance = this.$uibModal.open({
            controller: 'modalErrorController as modalErrorCtrl',
            templateUrl: require('./modal-error.html'),
            backdrop: 'static',
            resolve: {
                modalOptions: function () {
                    return self.prepareOptions(options);
                }
            }
        });

        // callback when modal close to go home in all case : close button, esc key, click on background
        modalInstance.result.then(function () {
            self.$state.go('home');
        }, function () {
            self.$state.go('home');
        });

        return modalInstance;
    }
}

export default ModalErrorService;