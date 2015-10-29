import angular from 'angular';

import loadingService from './loading-service';

export default angular.module('budgeto.loading', [])
    .service('loadingService', ['$log', '$q', '$injector', loadingService])
    .name;
