"use strict";

describe("Budgeto account module", function () {
    describe("", function () {
        var $rootScope;
        var scope;
        var $log;
        var $route;
        var $location;
        var $controller;
        var $httpBackend;
        var $q;

        beforeEach(function () {
            module("budgeto.account");

            inject(function (_$rootScope_, _$log_, _$route_, _$location_, _$controller_, _$httpBackend_, _$q_) {
                $rootScope = _$rootScope_;
                scope = _$rootScope_.$new();
                $log = _$log_;
                $route = _$route_;
                $location = _$location_;
                $controller = _$controller_;
                $httpBackend = _$httpBackend_;
                $q = _$q_;
            });
        });

        it("$routeProvider add route", inject(function () {
            $rootScope.$apply();

            expect($route.routes["/account"]).not.toBeNull();
            expect($route.routes["/account"].templateUrl).toBe("components/account/account.html");
            expect($route.routes["/account"].controller).toBe("AccountCtrl");
        }));

        describe("AccountCtrl", function () {

            it("define default scope value", inject(function (AccountResource, $modalError) {
                var deferred = $q.defer();
                deferred.resolve([]);
                spyOn(AccountResource, "all").and.returnValue(deferred.promise);
                var ctrl = $controller("AccountCtrl", {
                    $scope: scope,
                    "$location": $location,
                    "$log": $log,
                    "AccountResource": AccountResource,
                    "$modalError": $modalError
                });
                $rootScope.$apply();

                expect(scope.accounts).not.toBeNull();
                expect(scope.operations).not.toBeNull();
                expect(scope.account).toBeUndefined();
            }));

            it("function home change location to home", inject(function (AccountResource, $modalError) {
                var deferred = $q.defer();
                deferred.resolve([]);
                spyOn(AccountResource, "all").and.returnValue(deferred.promise);
                var ctrl = $controller("AccountCtrl", {
                    $scope: scope,
                    "$location": $location,
                    "$log": $log,
                    "AccountResource": AccountResource,
                    "$modalError": $modalError
                });
                $rootScope.$apply();

                scope.home();

                expect($location.path()).toBe("/");
            }));

            // watch and loading on control
            // rest of class
        });
    });
});