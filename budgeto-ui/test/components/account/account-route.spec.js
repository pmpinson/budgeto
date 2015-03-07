"use strict";

describe("Budgeto account-route module", function () {
    var $state;

    beforeEach(function () {
        module("budgeto.account.route");

        inject(function (_$state_) {
            $state = _$state_;
        });
    });

    it("$stateProvider add route", inject(function () {
        expect($state.get("account")).not.toBeNull();
        expect($state.get("account").url).toBe("/account");
        expect($state.get("account").templateUrl).toBe("components/account/account.html");
        expect($state.get("account").controller).toBe("AccountCtrl as accountCtrl");

        expect($state.get("account.list")).not.toBeNull();
        expect($state.get("account.list").url).toBeUndefined();
        expect($state.get("account.list").templateUrl).toBe("components/account/account-list.html");
        expect($state.get("account.list").controller).toBe("AccountListCtrl as accountListCtrl");

        expect($state.get("account.list.detail")).not.toBeNull();
        expect($state.get("account.list.detail").url).toBe("/detail");
        expect($state.get("account.list.detail").templateUrl).toBe("components/account/account-detail.html");
        expect($state.get("account.list.detail").controller).toBe("AccountDetailCtrl as accountDetailCtrl");
    }));
});