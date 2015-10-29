import angular from 'angular';

import loadingService from './loading-service';

export default angular.module('tools.loading', [])
    .service('loadingService', ['$log', '$q', '$injector', loadingService])
    .name;
