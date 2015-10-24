/**
 * provider to manage loading of application
 */
class ApisService {

    constructor($log, $resource, budgetoRestApiURL) {
        this.$log = $log;
        this.$resource = $resource;
        this.budgetoRestApiURL = budgetoRestApiURL;
        this.apis = [];
    }

    load() {
        this.$log.debug('ApisService', 'call api to get all available apis', this.budgetoRestApiURL);

        var self = this;

        return this.$resource(this.budgetoRestApiURL, {}, {}).get({}).$promise.then(function(data) {
            self.loadApis(data);

            return data;
        });
    }

    loadApis(data) {
        for (var key in data.links) {
            if (data.links[key].rel !== 'self') {
                this.apis.push(data.links[key]);
            }
        }
        this.$log.debug('ApisService', 'available apis', this.apis);
    }

    all() {
        return this.apis;
    }

    get(rel) {
        return this.getLink(rel, this.apis);
    }

    getLink(rel, links) {
        for (var key in links) {
            if (links[key].rel === rel) {
                return links[key];
            }
        }

        return null;
    }
}

export default ApisService;
