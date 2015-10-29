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

            if (self.accounts.length !== 0) {
                self.account = self.accounts[0];
            }
        });

        // add a watch to change selected account
        this.$scope.$watch(
            function () {
                return self.account;
            },
            function () {
                if (self.account !== undefined) {
                    self.$log.debug('AccountListController', 'select account', self.account);

                    self.$state.go('account.list.detail', {name: self.account.name});
                }
            }
        );
    }
}

export default AccountListController;
