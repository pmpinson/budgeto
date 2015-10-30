import angular from 'angular';
import uirouter from 'angular-ui-router';
import uibootstrap from 'angular-ui-bootstrap';

import modalErrorService from './modal-error-service.js';
import modalErrorController from './modal-error-controller.js';

export default angular.module('tools.modalError', [uirouter, uibootstrap])
    .service('modalErrorService', ['$log', '$modalInstance', 'modalOptions', modalErrorService])
    .controller('modalErrorController', ['$log', ,$uibModal, modalErrorController])
    .name;
