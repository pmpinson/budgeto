import angular from 'angular';
import angularResource from 'angular-resource';

import apisService from './apis-service';
apisService.$inject = ['$log', '$resource', 'budgetoRestApiURL'];

export default angular.module('budgeto.apis', [angularResource])
    .service('apisService', apisService)
    .name;
