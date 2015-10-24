//import './home.css';

import angular from 'angular';
import uirouter from 'angular-ui-router';

import loadingService from './loading-service';
loadingService.$inject = ['$log', '$q', '$injector'];
import homeRoute from './home-route';
homeRoute.$inject = ['$stateProvider'];
import homeController from './home-controller';
homeController.$inject = ['$log', '$state', 'loadingService'];

/**
 * definition of angular home module
 */
export default angular.module('budgeto.home', [uirouter])
    .controller('homeController', homeController)
    .config(homeRoute)
    .service('loadingService', loadingService)
    .name;
