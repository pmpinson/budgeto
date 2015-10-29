/**
 * Account controller
 */
class AccountController {

    constructor($log, $state) {
        this.$log = $log;
        this.$state = $state;

        this.$log.debug('AccountController load');

        this.$state.go('account.list');
    }

    home() {
        this.$log.debug('AccountController go back to home');
        this.$state.go('home');
    }
}

export default AccountController;
