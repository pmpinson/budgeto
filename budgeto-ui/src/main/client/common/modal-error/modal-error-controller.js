import _ from 'lodash';

/**
 * controller for modal of global error message
 */
class ModalErrorController {

    constructor($log, $uibModalInstance, modalOptions) {
        this.$log = $log;
        this.$uibModalInstance = $uibModalInstance;
        this.modalOptions = modalOptions;

        this.$log.debug('ModalErrorController', 'load');

        this.detailsCollapsed = true;
    }

    isObject(val) {
        return _.isObject(val) && !_.isArray(val);
    }

    formatObject(val) {
        return JSON.stringify(val, null, '    ');
    }

    close() {
        this.$uibModalInstance.dismiss('cancel');
    }
}

export default ModalErrorController;