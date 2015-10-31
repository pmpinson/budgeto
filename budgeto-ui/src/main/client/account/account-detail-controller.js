/**
 * Account detail controller
 */
class AccountDetailController {

    constructor($log, accountService, selectedAccount, loaderService) {
        this.$log = $log;
        this.accountService = accountService;
        this.account = selectedAccount;
        this.loaderService = loaderService;

        this.$log.debug('AccountDetailController', 'load with', this.account);

        this.operations = [];
        var self = this;

        this.loaderService.get('accountDetail').show();
        this.accountService.operations(this.account).then(function (data) {
            self.operations = data;
            self.loaderService.get('accountDetail').hide();
        });
    }
}

export default AccountDetailController;
