/**
 * Account defailt controller
 */
class AccountDetailController {

    constructor($log, $scope, accountService) {
        this.$log = $log;
        this.$scope = $scope;
        this.accountService = accountService;

        this.$log.debug('AccountDetailController load');

        this.account = this.$scope.accountListCtrl.account;
        this.operations = [];

        var self = this;
        this.accountService.operations(this.account).then(function (data) {
            self.$log.debug('AccountDetailController get all operations for', self.account, data);

            self.operations = data;
        });
    }
}

export default AccountDetailController;