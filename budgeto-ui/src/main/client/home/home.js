import angular from 'angular';
import uirouter from 'angular-ui-router';

import loading from '../common/loading/loading.js';
import apis from '../apis/apis.js';
import loader from '../common/loader/loader.js';

import homeRoute from './home-route.js';
import homeController from './home-controller.js';

/**
 * definition of angular home module
 */
export default angular.module('budgeto.home', [uirouter, loading, apis, loader])
    .controller('homeController', ['$log', '$state', 'loadingService', 'apisService', 'infiniteLoaderService', homeController])
    .config(['$stateProvider', homeRoute])
    .name;
