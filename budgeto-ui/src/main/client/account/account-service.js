/**
 * service to manage account
 */
class AccountService {

    constructor($log, $resource, apisService) {
        this.$log = $log;
        this.$resource = $resource;
        this.apisService = apisService;

        this.api = this.apisService.get('account');
        this.$log.debug('AccountService', 'use api', this.api);
    }

    /**
     * get all accounts
     * @returns {*}
     */
    all() {
        var promise = this.$resource(this.api.href, {}, {}).query({}).$promise;//.catch($modalError.manageError('error getting accounts'));
        this.$log.debug('AccountService', 'load all accounts', promise);
        return promise;
    }

    /**
     * get operations for an account
     * @param account
     * @returns {*}
     */
    operations(account) {
        var url = this.apisService.getLink('operations', account.links).href;
        var promise = this.$resource(url, {}, {}).query({}).$promise;//.catch($modalError.manageError('error getting operations for', account));
        this.$log.debug('AccountService', 'get all operations for', account, promise);
        return promise;
    }
}

export default AccountService;