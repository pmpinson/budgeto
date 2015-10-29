/**
 * Define routes for account module
 * @param $stateProvider
 */
function AccountRoute($stateProvider, $stateParams, $scopeProvider) {
    $stateProvider.state('account', {
        templateUrl: require('./account.html'),
        controller: 'accountController as accountCtrl'
    });
    $stateProvider.state('account.list', {
        templateUrl: require('./account-list.html'),
        controller: 'accountListController as accountListCtrl'
    });
    $stateProvider.state('account.list.detail', {
        params: {
            name: undefined
        },
        templateUrl: require('./account-detail.html'),
        controller: 'accountDetailController as accountDetailCtrl',
        resolve: {
            myAccount: function() {
                return 12;
            }
        }
    });
}

export default AccountRoute;