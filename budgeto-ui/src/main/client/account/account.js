//import './home.css';

import angular from 'angular';
import uirouter from 'angular-ui-router';
import angularResource from 'angular-resource';

import apis from '../apis/apis';

import accountRoute from './account-route.js';
import accountService from './account-service.js';
import accountController from './account-controller.js';
import accountListController from './account-list-controller.js';
import accountDetailController from './account-detail-controller.js';

/**
 * definition of angular account module
 */
export default angular.module('budgeto.account', [uirouter, angularResource, apis])
    .service('accountService', ['$log', '$resource', 'apisService', accountService])
    .controller('accountController', ['$log', '$state', accountController])
    .controller('accountListController', ['$log', '$scope', '$state', 'accountService', accountListController])
    .controller('accountDetailController', ['$log', 'accountService', 'selectedAccount', accountDetailController])
    .config(['$stateProvider', accountRoute])
    .name;
