"use strict";

define(['components/account/account', 'angular-mocks'], function() {

    describe("Budgeto account module", function () {

        describe("", function () {
            var ApiService;
            var api = {label:"myapi", href:"urlofMyApi"};

            beforeEach(function () {
                module("budgeto.account");

                inject(function (_ApiService_) {
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
                var $log;
                var $httpBackend;

                beforeEach(function () {
                    inject(function (_$log_, _$httpBackend_) {
                        $log = _$log_;
                        $httpBackend = _$httpBackend_;
                    });
                });

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
                    spyOn($modalError, "manageError").and.returnValue(function(reason) {
                        expect(reason.data).toBe("network fail");
                        expect(reason.status).toBe(500);
                    });
                    AccountResource.all();
                    $httpBackend.flush();

                    expect($modalError.manageError).toHaveBeenCalledWith("error getting accounts");
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
                        expect(false).toBe(true);
                    });
                }));

                it("operations show modal error if http call fail", inject(function (AccountResource, $modalError) {
                    var account = {name:"myaccount", links:[{rel:"operations", href:"/myaccount/operations"}]};
                    $httpBackend.whenGET("/myaccount/operations").respond(500, "network fail");

                    spyOn($log, "error").and.callThrough();
                    spyOn($modalError, "manageError").and.returnValue(function(reason) {
                        expect(reason.data).toBe("network fail");
                        expect(reason.status).toBe(500);
                    });
                    AccountResource.operations(account);
                    $httpBackend.flush();

                    expect($modalError.manageError).toHaveBeenCalledWith("error getting operations for", account);
                }));
            });
        });

        describe("", function () {
            var $rootScope;
            var scope;
            var $log;
            var $state;
            var $controller;
            var $q;

            beforeEach(function () {
                module("budgeto.account", function($stateProvider){
                    $stateProvider
                        .state("home", {})
                        .state("account", {})
                        .state("account.list", {})
                        .state("account.list.detail", {});
                });

                inject(function (_$rootScope_, _$log_, _$state_, _$controller_, _$q_) {
                    $rootScope = _$rootScope_;
                    scope = _$rootScope_.$new();
                    $log = _$log_;
                    $state = _$state_;
                    $controller = _$controller_;
                    $q = _$q_;
                });
            });

            describe("AccountCtrl", function () {

                it("change location to account.list", inject(function () {
                    spyOn($state, "go").and.callThrough();
                    $controller("AccountCtrl", {
                        "$scope": scope,
                        "$state": $state,
                        "$log": $log
                    });
                    $rootScope.$apply();

                    expect($state.go.calls.count()).toBe(1);
                    expect($state.go).toHaveBeenCalledWith("account.list");
                }));

                it("function home change location to home", inject(function () {
                    spyOn($state, "go").and.callThrough();
                    var accountCtrl = $controller("AccountCtrl", {
                        "$scope": scope,
                        "$state": $state,
                        "$log": $log
                    });
                    accountCtrl.home();
                    $rootScope.$apply();

                    expect($state.go.calls.count()).toBe(2);
                    expect($state.go).toHaveBeenCalledWith("account.list");
                    expect($state.go).toHaveBeenCalledWith("home");
                }));
            });

            describe("AccountListCtrl", function () {

                it("call to get all account, some results are store in scope", inject(function (AccountResource) {
                    var accounts = [{name:"myaccount", links:[{rel:"operations", href:"/myaccount/operations"}]}, {name:"myaccount2", links:[{rel:"operations", href:"/myaccount2/operations"}]}];
                    var deferred = $q.defer();
                    deferred.resolve(accounts);
                    spyOn(AccountResource, "all").and.returnValue(deferred.promise);
                    var accountListCtrl = $controller("AccountListCtrl", {
                        "$scope": scope,
                        "$state": $state,
                        "$log": $log,
                        "AccountResource": AccountResource
                    });
                    $rootScope.$apply();

                    expect(accountListCtrl.accounts).toBe(accounts);
                    expect(accountListCtrl.account).toBe(accounts[0]);
                }));

                it("call to get all account, no results are store in scope", inject(function (AccountResource) {
                    var accounts = [];
                    var deferred = $q.defer();
                    deferred.resolve(accounts);
                    spyOn(AccountResource, "all").and.returnValue(deferred.promise);
                    var accountListCtrl = $controller("AccountListCtrl", {
                        "$scope": scope,
                        "$state": $state,
                        "$log": $log,
                        "AccountResource": AccountResource
                    });
                    $rootScope.$apply();

                    expect(accountListCtrl.accounts).toBe(accounts);
                    expect(accountListCtrl.account).toBeUndefined();
                }));

                it("watch account for modification to show detail state", inject(function (AccountResource) {
                    var deferred = $q.defer();
                    deferred.resolve([]);
                    spyOn(AccountResource, "all").and.returnValue(deferred.promise);
                    var accountListCtrl = $controller("AccountListCtrl", {
                        "$scope": scope,
                        "$state": $state,
                        "$log": $log,
                        "AccountResource": AccountResource
                    });
                    $rootScope.$apply();

                    spyOn($state, "go").and.callThrough();
                    accountListCtrl.account = {name:"myaccount", links:[{rel:"operations", href:"/myaccount/operations"}]};
                    $rootScope.$apply();

                    expect($state.go).toHaveBeenCalledWith("account.list.detail", { name: "myaccount" });
                }));
            });

            describe("AccountDetailCtrl", function () {

                it("scope account is watch for modification to show detail state", inject(function (AccountResource) {
                    scope.accountListCtrl = {account: {name:"myaccount", links:[{rel:"operations", href:"/myaccount/operations"}]}};
                    var operations = [{label:"ope1"}];
                    var deferred = $q.defer();
                    deferred.resolve(operations);
                    spyOn(AccountResource, "operations").and.returnValue(deferred.promise);
                    var accountDetailCtrl = $controller("AccountDetailCtrl", {
                        "$scope": scope,
                        "$log": $log,
                        "AccountResource": AccountResource
                    });
                    $rootScope.$apply();

                    expect(AccountResource.operations).toHaveBeenCalledWith(scope.accountListCtrl.account);
                    expect(accountDetailCtrl.operations).toBe(operations);
                }));
            });
        });
    });
});