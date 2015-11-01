import "./loader.less";
import "./loader.gif";

import angular from 'angular';

import loaderService from './loader-service.js';
import loaderDirective from './loader-directive.js';
import infiniteLoaderService from './infinite-loader-service.js';

export default angular.module('tools.loader', [])
    .service('loaderService', [loaderService])
    .directive('loader', ['loaderService', loaderDirective])
    .service('infiniteLoaderService', ['$document', infiniteLoaderService])
    .name;
