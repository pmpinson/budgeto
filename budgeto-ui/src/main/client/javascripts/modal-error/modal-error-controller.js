import _ from 'lodash';

/**
 * controller for modal of global error message
 */
function ($log, $modalInstance, modalOptions) {
    $log.debug('wcm2.modalError : load ModalErrorController');

    this.modalOptions = modalOptions;
    this.detailsCollapsed = true;

    this.isObject = function (val) {
        return _.isObject(val) && !_.isArray(val);
    };

    this.formatObject = function (val) {
        return JSON.stringify(val, null, '    ');
    };

    this.close = function () {
        $modalInstance.dismiss('cancel');
    };
}]);
