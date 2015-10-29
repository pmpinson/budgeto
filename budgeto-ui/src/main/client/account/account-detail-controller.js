/**
 * Account detail controller
 */
class AccountDetailController {

    constructor($log, accountService, selectedAccount) {
        this.$log = $log;
        this.accountService = accountService;
        this.account = selectedAccount;

        this.$log.debug('AccountDetailController', 'load with', this.account);

        this.operations = [];
        var self = this;

        this.accountService.operations(this.account).then(function (data) {
            self.operations = data;
        });
    }
}

export default AccountDetailController;
