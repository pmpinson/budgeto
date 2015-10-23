function HomeRoute($stateProvider) {
    $stateProvider
        .state('home', {
            templateUrl: require('./home.html'),
            controller: 'homeController',
            controllerAs: 'homeCtrl'
        });
}

HomeRoute.$inject = ['$stateProvider'];

export default HomeRoute;