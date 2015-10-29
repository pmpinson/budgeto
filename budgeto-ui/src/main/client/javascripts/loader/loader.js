import angular from 'angular';

import loaderService from './loader-service';
import loaderDirective from './loader-directive';
import infiniteLoaderService from './infinite-loader-service';

export default angular.module('tools.loader', [])
    .service('loaderService', ['$rootScope', loaderService])
    .directive('loaderDirective', [loaderDirective])
    .service('infiniteLoaderService', ['$document', infiniteLoaderService])
    .name;
