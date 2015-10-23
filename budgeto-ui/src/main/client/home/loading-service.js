import angular from 'angular';

/**
 * provider to manage loading of application
 */
class LoadingService {

    constructor($log, $q, $injector) {
        this.$log = $log;
        this.$q = $q;
        this.$injector = $injector;
        this.servicesNames = [];
        this.promise = undefined;
    }

    add(value) {
        this.servicesNames.push(value);
    }

    config() {
        return {
            getServicesNames: function () {
                return this.servicesNames;
            }
        };
    }

    load() {
        console.log(4)
        if (this.promise === undefined) {
            this.$log.debug('LoadingService', 'load delayed services');

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

LoadingService.$inject = ['$log', '$q', '$injector'];

export default LoadingService;