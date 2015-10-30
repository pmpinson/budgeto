import angular from 'angular';

import loaderService from './loader-service.js';
import loaderDirective from './loader-directive.js';
import infiniteLoaderService from './infinite-loader-service.js';

export default angular.module('tools.loader', [])
    .service('loaderService', ['$rootScope', loaderService])
    .directive('loaderDirective', [loaderDirective])
    .service('infiniteLoaderService', ['$document', infiniteLoaderService])
    .name;
