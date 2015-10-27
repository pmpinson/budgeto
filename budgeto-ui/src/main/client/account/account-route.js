/**
 * Define routes for account module
 * @param $stateProvider
 */
function AccountRoute($stateProvider) {
    $stateProvider.state('account', {
        templateUrl: require('./account.html'),
        controller: 'accountController as accountCtrl'
    });
    $stateProvider.state('account.detail', {
        //params: {
        //    name: undefined
        //},
        templateUrl: require('./account-detail.html'),
        controller: 'accountDetailController as accountDetailCtrl'
    });
}

export default AccountRoute;