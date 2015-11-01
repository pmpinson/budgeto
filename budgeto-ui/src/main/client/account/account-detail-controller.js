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
        this.accountService.find(this.account).then(function (data) {
            self.account = data;
            self.loaderService.get('accountDetail').hide();

            self.loaderService.get('accountDetailOperations').show();
            self.accountService.operations(self.account).then(function (data) {
                self.operations = data;
                self.loaderService.get('accountDetailOperations').hide();
            });
        });
    }
}

export default AccountDetailController;
