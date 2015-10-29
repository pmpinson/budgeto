//import './home.css';

import angular from 'angular';
import uirouter from 'angular-ui-router';
import angularResource from 'angular-resource';

import apis from '../apis/apis';

import accountRoute from './account-route';
accountRoute.$inject = ['$stateProvider', '$stateParamsProvider'];
import accountService from './account-service';
accountService.$inject = ['$log', '$resource', 'apisService'];
import accountController from './account-controller';
accountController.$inject = ['$log', '$state'];
import accountListController from './account-list-controller';
accountListController.$inject = ['$log', '$scope', '$state', 'accountService'];
import accountDetailController from './account-detail-controller.js';
accountDetailController.$inject = ['$log', '$scope', 'accountService', 'myAccount'];

/**
 * definition of angular account module
 */
export default angular.module('budgeto.account', [uirouter, angularResource, apis])
    .service('accountService', accountService)
    .controller('accountController', accountController)
    .controller('accountListController', accountListController)
    .controller('accountDetailController', accountDetailController)
    .config(accountRoute)
    .name;
