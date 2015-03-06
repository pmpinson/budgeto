"use strict";

describe("Budgeto account module", function () {
    var $rootScope;
    var scope;
    var $log;
    var $location;
    var $controller;
    var $httpBackend;
    var $q;
    var ApiService;
    var api = {label:"myapi", href:"urlofMyApi"};

    beforeEach(function () {
        module("budgeto.account");

        inject(function (_$rootScope_, _$log_, _$location_, _$controller_, _$httpBackend_, _$q_, _ApiService_) {
            $rootScope = _$rootScope_;
            scope = _$rootScope_.$new();
            $log = _$log_;
            $location = _$location_;
            $controller = _$controller_;
            $httpBackend = _$httpBackend_;
            $q = _$q_;
            ApiService = _ApiService_;

            spyOn(ApiService, "find").and.returnValue(api);
        });
    });

    describe("AccountApi", function () {
        it("find the good api because it's defined", inject(function (AccountApi) {
            expect(ApiService.find).toHaveBeenCalledWith("account");
            expect(AccountApi).toBe(api);
        }));
    });

    describe("AccountResource", function () {

        afterEach(function() {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });

        it("all get the href of the current AccountApi", inject(function (AccountResource) {
            var accounts = [{name:"myaccount", links:[{rel:"operations", href:"/myaccount/operations"}]}, {name:"myaccount2", links:[{rel:"operations", href:"/myaccount2/operations"}]}];
            $httpBackend.whenGET("urlofMyApi").respond(accounts);

            var prom = AccountResource.all();
            $httpBackend.flush();

            prom.then(function(data){
                expect(JSON.stringify(data)).toBe(JSON.stringify(accounts));
            }).catch(function(){
                expect(false).toBe(true);
            });
        }));

        it("all show modal error if http call fail", inject(function (AccountResource, $modalError) {
            $httpBackend.whenGET("urlofMyApi").respond(500, "network fail");

            spyOn($log, "error").and.callThrough();
            spyOn($modalError, "open").and.callThrough();
            AccountResource.all();
            $httpBackend.flush();

            expect($log.error).toHaveBeenCalledWith("error getting accounts :", jasmine.any(Object));
            expect($modalError.open).toHaveBeenCalledWith();
        }));

        it("operations get the operations from operations link in the account", inject(function (AccountResource) {
            var account = {name:"myaccount", links:[{rel:"operations", href:"/myaccount/operations"}]};
            var operations = [{label:"ope1"}];
            $httpBackend.whenGET("/myaccount/operations").respond(operations);

            var prom = AccountResource.operations(account);
            $httpBackend.flush();
            prom.then(function(data){
                expect(JSON.stringify(data)).toBe(JSON.stringify(operations));
            }).catch(function(){
                console.log('err in promise');
                expect(false).toBe(true);
            });
        }));

        it("operations show modal error if http call fail", inject(function (AccountResource, $modalError) {
            var account = {name:"myaccount", links:[{rel:"operations", href:"/myaccount/operations"}]};
            $httpBackend.whenGET("/myaccount/operations").respond(500, "network fail");

            spyOn($log, "error").and.callThrough();
            spyOn($modalError, "open").and.callThrough();
            AccountResource.operations(account);
            $httpBackend.flush();

            expect($log.error).toHaveBeenCalledWith("error getting operations for", account, ":", jasmine.any(Object));
            expect($modalError.open).toHaveBeenCalledWith();
        }));
    });

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

            expect(scope.accounts).not.toBeNull();
            expect(scope.operations).not.toBeNull();
            expect(scope.account).toBeUndefined();
        }));

        it("call to get all account, some results are store in scope", inject(function (AccountResource, $modalError) {
            var accounts = [{name:"myaccount", links:[{rel:"operations", href:"/myaccount/operations"}]}, {name:"myaccount2", links:[{rel:"operations", href:"/myaccount2/operations"}]}];
            var deferred = $q.defer();
            deferred.resolve(accounts);
            spyOn(AccountResource, "all").and.returnValue(deferred.promise);
            $controller("AccountCtrl", {
                $scope: scope,
                "$location": $location,
                "$log": $log,
                "AccountResource": AccountResource,
                "$modalError": $modalError
            });
            deferred = $q.defer();
            deferred.resolve([]);
            spyOn(AccountResource, "operations").and.returnValue(deferred.promise);
            $rootScope.$apply();

            expect(scope.accounts).toBe(accounts);
            expect(scope.account).toBe(accounts[0]);
        }));

        it("call to get all account, no results are store in scope", inject(function (AccountResource, $modalError) {
            var accounts = [];
            var deferred = $q.defer();
            deferred.resolve(accounts);
            spyOn(AccountResource, "all").and.returnValue(deferred.promise);
            $controller("AccountCtrl", {
                $scope: scope,
                "$location": $location,
                "$log": $log,
                "AccountResource": AccountResource,
                "$modalError": $modalError
            });
            deferred = $q.defer();
            deferred.resolve([]);
            spyOn(AccountResource, "operations").and.returnValue(deferred.promise);
            $rootScope.$apply();

            expect(scope.accounts).toBe(accounts);
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
    });
});