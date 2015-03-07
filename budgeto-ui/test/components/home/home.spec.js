"use strict";

describe("Budgeto home module", function () {
    var $rootScope;
    var scope;
    var $log;
    var $location;
    var $controller;
    var $q;

    beforeEach(function () {
        module("budgeto.home");

        inject(function (_$rootScope_, _$log_, _$location_, _$controller_, _$q_) {
            $rootScope = _$rootScope_;
            scope = _$rootScope_.$new();
            $log = _$log_;
            $location = _$location_;
            $controller = _$controller_;
            $q = _$q_;
        });
    });


    describe("HomeCtrl", function () {

        it("clean $location.search 'sourcePage' and call the infinite loader to be showed", inject(function (ApiService, $infiniteLoader, LoadingService) {
            spyOn($location, "search").and.callThrough();
            spyOn($infiniteLoader, "show").and.callThrough();

            $controller("HomeCtrl", {
                $scope: scope,
                "$location": $location,
                "$log": $log,
                "ApiService": ApiService,
                "LoadingService": LoadingService,
                "$infiniteLoader": $infiniteLoader
            });
            $rootScope.$apply();

            expect($location.search).toHaveBeenCalledWith();
            expect($location.search).toHaveBeenCalledWith("sourcePage", null);
            expect($infiniteLoader.show).toHaveBeenCalledWith();
            expect(scope.loadFail).toBe(false);
            expect($location.path()).toBe("");
        }));

        it(" clean $location.search 'sourcePage' with empty value", inject(function (ApiService, $infiniteLoader, LoadingService) {
            spyOn($location, "search").and.returnValue({});
            spyOn($infiniteLoader, "show").and.callThrough();

            $controller("HomeCtrl", {
                $scope: scope,
                "$location": $location,
                "$log": $log,
                "ApiService": ApiService,
                "LoadingService": LoadingService,
                "$infiniteLoader": $infiniteLoader
            });
            $rootScope.$apply();

            expect($location.search).toHaveBeenCalledWith();
            expect($location.search).toHaveBeenCalledWith("sourcePage", null);
            expect(scope.loadFail).toBe(false);
            expect($location.path()).toBe("");
        }));

        it(" clean $location.search 'sourcePage' with a value", inject(function (ApiService, $infiniteLoader, LoadingService) {
            spyOn($location, "search").and.returnValue({sourcePage:"mypage"});
            spyOn($infiniteLoader, "show").and.callThrough();

            $controller("HomeCtrl", {
                $scope: scope,
                "$location": $location,
                "$log": $log,
                "ApiService": ApiService,
                "LoadingService": LoadingService,
                "$infiniteLoader": $infiniteLoader
            });
            $rootScope.$apply();

            expect($location.search).toHaveBeenCalledWith();
            expect($location.search).toHaveBeenCalledWith("sourcePage", null);
            expect(scope.loadFail).toBe(false);
            expect($location.path()).toBe("/mypage");
        }));

        it("with LoadingService that load ok apis is get and set in scope", inject(function (ApiService, $infiniteLoader, LoadingService) {
            var customDefer = $q.defer();
            customDefer.resolve(true);

            var apis = [{url:"http://"}];
            spyOn(ApiService, "findAll").and.returnValue(apis);
            spyOn(LoadingService, "loaded").and.returnValue(customDefer.promise);

            $controller("HomeCtrl", {
                $scope: scope,
                "$location": $location,
                "$log": $log,
                "ApiService": ApiService,
                "LoadingService": LoadingService,
                "$infiniteLoader": $infiniteLoader
            });
            $rootScope.$apply();

            expect(scope.apis).not.toBeNull();
            expect(scope.apis).toBe(apis);
        }));


        it("with LoadingService that load ok, sourcePage empty don't change path", inject(function (ApiService, $infiniteLoader, LoadingService) {
            var customDefer = $q.defer();
            customDefer.resolve(true);

            spyOn($location, "path").and.callThrough();
            spyOn($infiniteLoader, "hide").and.callThrough();
            spyOn(LoadingService, "loaded").and.returnValue(customDefer.promise);

            $controller("HomeCtrl", {
                $scope: scope,
                "$location": $location,
                "$log": $log,
                "ApiService": ApiService,
                "LoadingService": LoadingService,
                "$infiniteLoader": $infiniteLoader
            });
            $rootScope.$apply();

            expect(LoadingService.loaded).toHaveBeenCalledWith();
            expect($infiniteLoader.hide).toHaveBeenCalledWith();
            expect(scope.loadFail).toBe(false);
            expect($location.path()).toBe("");
        }));


        it("with LoadingService that load ok, sourcePage with value don't change path", inject(function (ApiService, $infiniteLoader, LoadingService) {
            var customDefer = $q.defer();
            customDefer.resolve(true);

            spyOn($location, "path").and.callThrough();
            spyOn($location, "search").and.returnValue({sourcePage:"mypage"});
            spyOn($infiniteLoader, "hide").and.callThrough();
            spyOn(LoadingService, "loaded").and.returnValue(customDefer.promise);

            $controller("HomeCtrl", {
                $scope: scope,
                "$location": $location,
                "$log": $log,
                "ApiService": ApiService,
                "LoadingService": LoadingService,
                "$infiniteLoader": $infiniteLoader
            });
            $rootScope.$apply();

            expect(LoadingService.loaded).toHaveBeenCalledWith();
            expect($infiniteLoader.hide).toHaveBeenCalledWith();
            expect($location.search).toHaveBeenCalledWith("sourcePage", null);
            expect(scope.loadFail).toBe(false);
            expect($location.path()).toBe("/mypage");
        }));

        it("with LoadingService that load fail no change to path (empty path)", inject(function (ApiService, $infiniteLoader, LoadingService) {
            var customDefer = $q.defer();
            customDefer.reject("an error occured");

            spyOn($log, "error").and.callThrough();
            spyOn($location, "path").and.callThrough();
            spyOn($infiniteLoader, "hide").and.callThrough();
            spyOn(LoadingService, "loaded").and.returnValue(customDefer.promise);

            $controller("HomeCtrl", {
                $scope: scope,
                "$location": $location,
                "$log": $log,
                "ApiService": ApiService,
                "LoadingService": LoadingService,
                "$infiniteLoader": $infiniteLoader
            });
            $rootScope.$apply();

            expect(LoadingService.loaded).toHaveBeenCalledWith();
            expect($infiniteLoader.hide).toHaveBeenCalledWith();
            expect($location.path).not.toHaveBeenCalledWith(jasmine.any(String));
            expect($log.error).toHaveBeenCalledWith("error getting apis /", "an error occured");
            expect(scope.loadFail).toBe(true);
            expect($location.path()).toBe("");
        }));

        it(" scope function changePath with a path change the path to selected", inject(function (ApiService) {
            spyOn($location, "path").and.callThrough({});

            $controller("HomeCtrl", {
                $scope: scope,
                "$location": $location,
                "$log": $log,
                "ApiService": ApiService
            });
            scope.$apply();
            scope.changePath("mySelectedPath");

            expect($location.path()).toBe("/mySelectedPath");
        }));

        it(" scope function changePath with a path null change the path to /home", inject(function (ApiService) {
            spyOn($location, "path").and.callThrough({});

            $controller("HomeCtrl", {
                $scope: scope,
                "$location": $location,
                "$log": $log,
                "ApiService": ApiService
            });
            scope.$apply();
            scope.changePath();

            expect($location.path()).toBe("");
        }));
    });
});