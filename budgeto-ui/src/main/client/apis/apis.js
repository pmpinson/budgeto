import angular from 'angular';
import angularResource from 'angular-resource';



/**
 * provider to manage loading of application
 */
class ApisService {

    constructor() {
        this.url = 'noUrlSet';
        this.apis = [];
    }

    setUrl(value) {
        this.url = value;
    }


    config() {
        return {
            getUrl: function () {
                return url;
            }
        };
    }

    apisService.load = function () {
        $log.debug('ApisService', 'call api to get all available apis');

        this.$log.debug('ApisResource', 'get', this.url);

        return this.$resource(this.url, {}, {}).get({}).$promise.then(function(data) {
            apisService.loadApis(data);
            return data;
        });
    };

    apisService.loadApis = function (data) {
        for (var key in data.links) {
            if (data.links[key].rel !== 'self') {
                apis.push(data.links[key]);
            }
        }
        $log.debug('ApisService', 'available apis', apis);
    };

    apisService.findAll = function () {
        return apis;
    };

    apisService.find = function (rel) {
        return this.getLink(rel, apis);
    };

    apisService.getLink = function (rel, links) {
        for (var key in links) {
            if (links[key].rel === rel) {
                return links[key];
            }
        }
        return null;
    };
}

export default angular.module('budgeto.apis', [angularResource])
    .service('apisService', ApisService)
    .name;
