import angular from 'angular';

import loadingService from './loading-service';
loadingService.$inject = ['$log', '$q', '$injector'];

export default angular.module('budgeto.loading', [])
    .service('loadingService', loadingService)
    .name;
