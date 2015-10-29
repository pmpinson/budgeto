//import './home.css';

import angular from 'angular';
import uirouter from 'angular-ui-router';

import loading from '../loading/loading';
import apis from '../apis/apis';

import homeRoute from './home-route';
import homeController from './home-controller';

/**
 * definition of angular home module
 */
export default angular.module('budgeto.home', [uirouter, loading, apis])
    .controller('homeController', ['$log', '$state', 'loadingService', 'apisService', homeController])
    .config(['$stateProvider', homeRoute])
    .name;
