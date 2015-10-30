import angular from 'angular';
import uirouter from 'angular-ui-router';

import loading from '../loading/loading.js';
import apis from '../apis/apis.js';

import homeRoute from './home-route.js';
import homeController from './home-controller.js';

/**
 * definition of angular home module
 */
export default angular.module('budgeto.home', [uirouter, loading, apis])
    .controller('homeController', ['$log', '$state', 'loadingService', 'apisService', homeController])
    .config(['$stateProvider', homeRoute])
    .name;
