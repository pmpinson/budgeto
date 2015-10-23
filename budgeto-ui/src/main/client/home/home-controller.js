class HomeController {

    constructor($log, $state, loadingService) {//, ApiService, $infiniteLoader) {
        this.state = $state;
        this.log = $log;
        //this.ApiService = ApiService;
        //this.$infiniteLoader = $infiniteLoader;
        this.LoadingService = loadingService;

        this.loadFail = false;
        this.log.debug('HomeController', 'start loading service');

        var self = this;
        this.LoadingService.load().then(function (data) {
            self.log.debug('HomeController', 'loading done');
            //$infiniteLoader.hide();
            //that.apis = ApiService.findAll();
            return data;
        }).catch(function (reason) {
            self.log.error('HomeController', 'error getting apis /', reason);
            self.loadFail = true;
            //$infiniteLoader.hide();
        });
    }

    changePath(path) {
        var destination = path;
        if (destination === undefined) {
            destination = 'home';
        }
        try {
            return this.state.go(destination);
        } catch (exception) {
            this.log.error('HomeController', 'unknown path', path, ':', exception);
            return this.state.go('home');
        }
    };
}

HomeController.$inject = ['$log', '$state', 'loadingService'];

export default HomeController;