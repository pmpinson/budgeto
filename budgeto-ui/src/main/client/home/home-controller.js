/**
 * Home controller
 */
class HomeController {

    constructor($log, $state, loadingService, apisService) {//, $infiniteLoader) {
        this.$state = $state;
        this.$log = $log;
        this.apisService = apisService;
        //this.$infiniteLoader = $infiniteLoader;
        this.loadingService = loadingService;

        this.$log.debug('HomeController', 'load');

        this.loadFail = false;
        this.apis = [];
        var self = this;

        this.loadingService.load().then(function (data) {
            self.$log.debug('HomeController', 'loading done');
            //$infiniteLoader.hide();
            self.apis = self.apisService.all();
            return data;
        }).catch(function (reason) {
            self.$log.error('HomeController', 'error getting apis /', reason);
            self.loadFail = true;
            //$infiniteLoader.hide();
        });
    }

    /**
     * change current path
     * @param path
     * @returns {promise|IPromise<any>|void}
     */
    changePath(path) {
        var destination = (path === undefined) ? 'home' : path;

        try {
            return this.$state.go(destination);
        } catch (exception) {
            this.$log.error('HomeController', 'unknown path', path, ':', exception);

            return this.$state.go('home');
        }
    }
}

export default HomeController;
