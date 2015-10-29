/**
 * Account detail controller
 */
class AccountDetailController {

    constructor($log, $scope, accountService, myAccount) {
        this.$log = $log;
        this.$scope = $scope;
        this.accountService = accountService;

        console.log(myAccount);

        this.$log.debug('AccountDetailController', 'load');

        this.account = this.$scope.accountListCtrl.account;
        this.operations = [];
        var self = this;

        this.accountService.operations(this.account).then(function (data) {
            self.operations = data;
        });
    }
}

export default AccountDetailController;
