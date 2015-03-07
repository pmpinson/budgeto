"use strict";

describe("Budgeto account-route module", function () {
    var $route;

    beforeEach(function () {
        module("budgeto.account.route");

        inject(function (_$route_) {
            $route = _$route_;
        });
    });

    it("$routeProvider add route", inject(function () {
        expect($route.routes["/account"]).not.toBeNull();
        expect($route.routes["/account"].templateUrl).toBe("components/account/account.html");
        expect($route.routes["/account"].controller).toBe("AccountCtrl");
    }));
});