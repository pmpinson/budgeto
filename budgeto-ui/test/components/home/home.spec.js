"use strict";

describe("Budgeto home module", function () {
    describe("", function () {
        var $rootScope;
        var scope;
        var $log;
        var $route;
        var $location;
        var $controller;
        var $httpBackend;

        beforeEach(function () {
            module("budgeto.home");

            inject(function (_$rootScope_, _$log_, _$route_, _$location_, _$controller_, _$httpBackend_) {
                $rootScope = _$rootScope_;
                scope = _$rootScope_.$new();
                $log = _$log_;
                $route = _$route_;
                $location = _$location_;
                $controller = _$controller_;
                $httpBackend = _$httpBackend_;

                $httpBackend.whenGET("components/home/home.html").respond(function (method, url, data) {
                    return '<div><div>';
                });
            });
        });

        it("$routeProvider add route", inject(function () {
            $rootScope.$apply();

            expect($route.routes["/home"]).not.toBeNull();
            expect($route.routes["/home"].templateUrl).toBe("components/home/home.html");
            expect($route.routes["/home"].controller).toBe("HomeCtrl");
            expect($route.routes[null]).not.toBeNull();
            expect($route.routes[null].redirectTo).toBe("/home");
        }));

        describe("HomeCtrl", function () {

            it(" load apis in scope from ApiService", inject(function (ApiService) {
                var apis = [{url:"http://"}];
                spyOn(ApiService, "findAll").and.returnValue(apis);

                var ctrl = $controller("HomeCtrl", {
                    $scope: scope,
                    "$location": $location,
                    "$log": $log,
                    "ApiService": ApiService
                });
                $rootScope.$apply();

                expect(scope.apis).not.toBeNull();
                expect(scope.apis).toBe(apis);
            }));

            it(" scope function changePath with a path change the path to selected", inject(function (ApiService) {
                spyOn($location, "path").and.callThrough({});

                var ctrl = $controller("HomeCtrl", {
                    $scope: scope,
                    "$location": $location,
                    "$log": $log,
                    "ApiService": ApiService
                });
                $rootScope.$apply();
                scope.changePath("mySelectedPath");

                expect($location.path()).toBe("/mySelectedPath");
            }));

            it(" scope function changePath with a path null change the path to /home", inject(function (ApiService) {
                spyOn($location, "path").and.callThrough({});

                var ctrl = $controller("HomeCtrl", {
                    $scope: scope,
                    "$location": $location,
                    "$log": $log,
                    "ApiService": ApiService
                });
                $rootScope.$apply();
                scope.changePath();

                expect($location.path()).toBe("/home");
            }));
        });
    });
});