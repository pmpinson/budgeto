import _ from 'lodash';

/**
 * Service to manage loading of all necessary services before start
 */
class LoadingService {

    constructor($log, $q, $injector) {
        this.$log = $log;
        this.$q = $q;
        this.$injector = $injector;
        this.servicesNames = [];
        this.promise = undefined;
    }

    /**
     * Add a new service
     * @param value
     */
    add(value) {
        this.servicesNames.push(value);
    }

    /**
     * get actuals configured services
     * @returns {Array}
     */
    getServicesNames() {
        return this.servicesNames;
    }

    /**
     * load all registered service
     * @returns {promise|IPromise<any>}
     */
    load() {
        if (_.isUndefined(this.promise)) {
            this.$log.debug('LoadingService', 'load delayed services', this.servicesNames);

            if (!_.isEmpty(this.servicesNames)) {
                var self = this;
                var servicesPromises = _.map(this.servicesNames, function(serviceName) {
                    return self.$injector.get(serviceName).load();
                });
                this.promise = this.$q.all(servicesPromises);
            } else {
                var deferred = this.$q.defer();
                this.promise = deferred.promise;
                deferred.resolve(true);
            }
        }

        return this.promise;
    }
}

export default LoadingService;
