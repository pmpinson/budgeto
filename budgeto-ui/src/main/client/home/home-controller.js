/**
 * Home controller
 */
class HomeController {

    constructor($log, $state, loadingService) {//, ApiService, $infiniteLoader) {
        this.$state = $state;
        this.$log = $log;
        //this.ApiService = ApiService;
        //this.$infiniteLoader = $infiniteLoader;
        this.LoadingService = loadingService;
        this.loadFail = false;

        var self = this;
        this.$log.debug('HomeController', 'start loading service');
        this.LoadingService.load().then(function (data) {
            self.$log.debug('HomeController', 'loading done');
            //$infiniteLoader.hide();
            //that.apis = ApiService.findAll();
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
