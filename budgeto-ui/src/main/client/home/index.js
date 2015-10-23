//import './home.css';

import angular from 'angular';
import uirouter from 'angular-ui-router';

import loadingService from './loading-service';

import homeRoute from './home-route';
import homeController from './home-controller';

export default angular.module('budgeto.home', [uirouter])
    .controller('homeController', homeController)
    .config(homeRoute)
    .service('loadingService', loadingService)
    .name;
