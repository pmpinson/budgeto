//import './home.css';

import angular from 'angular';
import uirouter from 'angular-ui-router';
import angularResource from 'angular-resource';

import apis from '../apis/apis';

import accountRoute from './account-route';
accountRoute.$inject = ['$stateProvider'];
import accountService from './account-service';
accountService.$inject = ['$log', '$resource', 'apisService'];
import accountController from './account-controller';
accountController.$inject = ['$log', '$scope', '$state', 'accountService'];
import accountDetailController from './account-detail-controller.js';
accountDetailController.$inject = ['$log', '$scope', 'accountService'];

/**
 * definition of angular account module
 */
export default angular.module('budgeto.account', [uirouter, angularResource, apis])
    .service('accountService', accountService)
    .controller('accountController', accountController)
    .controller('accountDetailController', accountDetailController)
    .config(accountRoute)
    .name;
