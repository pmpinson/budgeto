"use strict";

describe("Budgeto home-route module", function () {
    var $route;

    beforeEach(function () {
        module("budgeto.home.route");

        inject(function (_$route_) {
            $route = _$route_;
        });
    });

    it("$routeProvider add route home", inject(function () {
        expect($route.routes["/home"]).not.toBeNull();
        expect($route.routes["/home"].templateUrl).toBe("components/home/home.html");
        expect($route.routes["/home"].controller).toBe("HomeCtrl");
        expect($route.routes["/home"].reloadOnSearch).toBe(false);
    }));

    it("$routeProvider add route default", inject(function () {
        expect($route.routes[null]).not.toBeNull();
        expect($route.routes[null].redirectTo).toBe("/home");
    }));
});