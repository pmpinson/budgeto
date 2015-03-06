"use strict";

describe("Budgeto home module", function () {
    var $rootScope;
    var scope;
    var $log;
    var $location;
    var $controller;

    beforeEach(function () {
        module("budgeto.home");

        inject(function (_$rootScope_, _$log_, _$location_, _$controller_) {
            $rootScope = _$rootScope_;
            scope = _$rootScope_.$new();
            $log = _$log_;
            $location = _$location_;
            $controller = _$controller_;
        });
    });

    describe("HomeCtrl", function () {

        it(" load apis in scope from ApiService", inject(function (ApiService) {
            var apis = [{url:"http://"}];
            spyOn(ApiService, "findAll").and.returnValue(apis);

            $controller("HomeCtrl", {
                $scope: scope,
                "$location": $location,
                "$log": $log,
                "ApiService": ApiService
            });
            scope.$apply();

            expect(scope.apis).not.toBeNull();
            expect(scope.apis).toBe(apis);
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