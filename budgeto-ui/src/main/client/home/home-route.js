/**
 * Define routes for home module
 * @param $stateProvider
 * @constructor
 */
function HomeRoute($stateProvider) {
    $stateProvider
        .state('home', {
            templateUrl: require('./home.html'),
            controller: 'homeController as homeCtrl'
        });
}

export default HomeRoute;
