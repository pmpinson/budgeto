import angular from 'angular';
import angularResource from 'angular-resource';

import apisService from './apis-service.js';

export default angular.module('budgeto.apis', [angularResource])
    .service('apisService', ['$log', '$resource', 'budgetoRestApiURL', apisService])
    .name;
