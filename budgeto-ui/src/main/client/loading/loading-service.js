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
        if (this.promise === undefined) {
            this.$log.debug('LoadingService', 'load delayed services', this.servicesNames);

            if (this.servicesNames.length !== 0) {
                var servicesPromises = [];

                for (var key in this.servicesNames) {
                    servicesPromises.push(this.$injector.get(this.servicesNames[key]).load());
                }
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
