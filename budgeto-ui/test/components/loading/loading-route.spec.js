"use strict";

describe("Budgeto loading-route module", function () {
    var $route;

    beforeEach(function () {
        module("budgeto.loading.route");

        inject(function (_$route_) {
            $route = _$route_;
        });
    });

    it("$routeProvider add route", inject(function () {
        expect($route.routes["/loading"]).not.toBeNull();
        expect($route.routes["/loading"].templateUrl).toBe("components/loading/loading.html");
        expect($route.routes["/loading"].controller).toBe("LoadingCtrl");
        expect($route.routes["/loading"].reloadOnSearch).toBe(false);
    }));
});