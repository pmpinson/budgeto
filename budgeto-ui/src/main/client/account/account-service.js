/**
 * service to manage account
 */
class AccountService {

    constructor($log, $resource, apisService) {
        this.$log = $log;
        this.$resource = $resource;
        this.apisService = apisService;

        this.api = this.apisService.get('account');
        $log.debug('AccountService', 'use api', this.api);
    }

    /**
     * get all accounts
     * @returns {*}
     */
    all() {
        return this.$resource(this.api.href, {}, {}).query({}).$promise;//.catch($modalError.manageError('error getting accounts'));
    }

    /**
     * get operations for an account
     * @param account
     * @returns {*}
     */
    operations(account) {
        var url = this.apisService.getLink('operations', account.links).href;
        return this.$resource(url, {}, {}).query({}).$promise;//.catch($modalError.manageError('error getting operations for', account));
    }
}

export default AccountService;