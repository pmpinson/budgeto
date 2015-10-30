import _ from 'lodash';

/**
 * Home controller
 */
class HomeController {

    constructor($log, $state, loadingService, apisService, infiniteLoaderService) {
        this.$state = $state;
        this.$log = $log;
        this.apisService = apisService;
        this.infiniteLoaderService = infiniteLoaderService;
        this.loadingService = loadingService;

        this.$log.debug('HomeController', 'load');

        this.loadFail = false;
        this.apis = [];
        var self = this;

        this.infiniteLoaderService.show();
        this.loadingService.load().then(function (data) {
            self.$log.debug('HomeController', 'loading done');
            self.infiniteLoaderService.hide();
            self.apis = self.apisService.all();

            return data;
        }).catch(function (reason) {
            self.$log.error('HomeController', 'error getting apis /', reason);
            self.loadFail = true;
            self.infiniteLoaderService.hide();
        });
    }

    /**
     * change current path
     * @param path
     * @returns {promise}
     */
    changePath(path) {
        var destination = _.isUndefined(path) ? 'home' : path;

        try {
            return this.$state.go(destination);
        } catch (exception) {
            this.$log.error('HomeController', 'unknown path', path, ':', exception);

            return this.$state.go('home');
        }
    }
}

export default HomeController;
