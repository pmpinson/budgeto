import _ from 'lodash';

/**
 * provider to manage definition of apis
 */
class ApisService {

    constructor($log, $resource, budgetoRestApiURL) {
        this.$log = $log;
        this.$resource = $resource;
        this.budgetoRestApiURL = budgetoRestApiURL;
        this.apis = [];
    }

    /**
     * load apis definition
     * @returns {*}
     */
    load() {
        this.$log.debug('ApisService', 'call api to get all available apis on', this.budgetoRestApiURL);

        var self = this;

        return this.$resource(this.budgetoRestApiURL, {}, {}).get({}).$promise.then(function (data) {
            self.loadApis(data);

            return data;
        });
    }

    /**
     * load result of apis definitions
     * @param data
     */
    loadApis(data) {
        this.apis = _.filter(data.links, function (link) {
            return link.rel !== 'self';
        });
        this.$log.debug('ApisService', 'available apis', this.apis);
    }

    /**
     * get all apis
     * @returns {Array}
     */
    all() {
        return this.apis;
    }

    /**
     * get one api by is name
     * @param rel
     * @returns {*}
     */
    get(rel) {
        return ApisService.getLink(rel, this.apis);
    }

    /**
     * get link of an api by name
     * @param rel
     * @param links
     * @returns {*}
     */
    static getLink(rel, links) {
        return _.find(links, {rel: rel});
    }
}

export default ApisService;
