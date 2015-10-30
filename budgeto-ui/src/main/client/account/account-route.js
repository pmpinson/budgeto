/**
 * Define routes for account module
 * @param $stateProvider
 */
function AccountRoute($stateProvider) {
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
            account: undefined
        },
        templateUrl: require('./account-detail.html'),
        controller: 'accountDetailController as accountDetailCtrl',
        resolve: {
            selectedAccount: ['$stateParams', function ($stateParams) {
                return $stateParams.account;
            }]
        }
    });
}

export default AccountRoute;