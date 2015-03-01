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
                $controller("AccountCtrl", {
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
                $controller("AccountCtrl", {
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

            it("account home change location to home", inject(function (AccountResource, $modalError) {
                var deferred = $q.defer();
                deferred.resolve([]);
                spyOn(AccountResource, "all").and.returnValue(deferred.promise);
                $controller("AccountCtrl", {
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

            it("scope account is watch for modification to get operations", inject(function (AccountResource, $modalError) {
                var deferred = $q.defer();
                deferred.resolve([]);
                spyOn(AccountResource, "all").and.returnValue(deferred.promise);
                $controller("AccountCtrl", {
                    $scope: scope,
                    "$location": $location,
                    "$log": $log,
                    "AccountResource": AccountResource,
                    "$modalError": $modalError
                });
                $rootScope.$apply();

                var operations = [{label:"ope1"}];
                deferred = $q.defer();
                deferred.resolve(operations);
                spyOn(AccountResource, "operations").and.returnValue(deferred.promise);
                scope.account = {name:"myaccount", links:[{rel:"operations", href:"/myaccount/operations"}]};
                $rootScope.$apply();

                expect(AccountResource.operations).toHaveBeenCalledWith(scope.account);
                expect(scope.operations).toBe(operations);
            }));

            it("scope account is watch for modification to get operations but http call fail", inject(function (AccountResource, $modalError) {
                var deferred = $q.defer();
                deferred.resolve([]);
                spyOn(AccountResource, "all").and.returnValue(deferred.promise);
                $controller("AccountCtrl", {
                    $scope: scope,
                    "$location": $location,
                    "$log": $log,
                    "AccountResource": AccountResource,
                    "$modalError": $modalError
                });
                $rootScope.$apply();

                deferred = $q.defer();
                deferred.reject("network fail");
                spyOn(AccountResource, "operations").and.returnValue(deferred.promise);
                spyOn($log, "error").and.callThrough();
                spyOn($modalError, "open").and.callThrough();
                scope.account = {name:"myaccount", links:[{rel:"operations", href:"/myaccount/operations"}]};
                $rootScope.$apply();

                expect($modalError.open).toHaveBeenCalledWith();
                expect($log.error).toHaveBeenCalledWith('error getting operations for', scope.account, ':', 'network fail');
            }));

            // loading on control
            // rest of class
        });
    });
});