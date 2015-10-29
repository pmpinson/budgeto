import _ from 'lodash';

/**
 * Account list controller
 */
class AccountListController {

    constructor($log, $scope, $state, accountService) {
        this.$log = $log;
        this.$scope = $scope;
        this.$state = $state;
        this.accountService = accountService;

        this.$log.debug('AccountListController', 'load');

        this.accounts = [];
        this.account = undefined;

        var self = this;
        // load all accounts from service
        this.accountService.all().then(function (data) {
            self.accounts = data;
            self.account = undefined;

            if (!_.isEmpty(self.accounts)) {
                self.account = self.accounts[0];
            }
        });

        // add a watch to change selected account
        this.$scope.$watch(
            function () {
                return self.account;
            },
            function () {
                if (!_.isUndefined(self.account)) {
                    self.$log.debug('AccountListController', 'select account', self.account);

                    self.$state.go('account.list.detail', {account: self.account});
                }
            }
        );
    }
}

export default AccountListController;
