"use strict";

describe("Budgeto module", function () {

    describe("constants configuration", function () {

        beforeEach(function () {
            module("budgeto");
        });

        it("BudgetoRestApiURL to be correct", inject(function (BudgetoRestApiURL) {
            expect(BudgetoRestApiURL).toEqual("http://localhost:9001/budgeto-api");
        }));

        it("moment tz to be correct", inject(function (angularMomentConfig) {
            expect(angularMomentConfig).toEqual({timezone: "UTC"});
        }));

        it("messages defined correctly", inject(function (MessageService) {
            expect(Object.keys(MessageService).length).toBe(8);
            expect(Object.keys(MessageService.apisLinks).length).toBe(2);
            expect(Object.keys(MessageService.apisTitles).length).toBe(2);
            expect(Object.keys(MessageService.modalError).length).toBe(3);
        }));
    });

    describe("application run", function () {
        var $log;
        var $location;
        var $rootScope;

        beforeEach(function () {
            module("budgeto");

            inject(function (_$log_, _$location_, _$rootScope_) {
                $log = _$log_;
                $location = _$location_;
                $rootScope = _$rootScope_;
            });
        });

        it("redirecting to /loading and keep source path", inject(function (MessageService) {
            spyOn($location, "path").and.callThrough();
            spyOn($location, "search").and.callThrough();

            // call run
            var myModule = angular.module("budgeto");
            myModule._runBlocks[0][4]($location, $rootScope, $log, MessageService);
            $rootScope.$apply();

            expect($location.path.calls.count()).toBe(2);
            expect($location.path).toHaveBeenCalledWith();
            expect($location.path).toHaveBeenCalledWith("/loading");
            expect($location.search).toHaveBeenCalledWith("sourcePage", $location.path());
            expect($location.path()).toBe("/loading");
        }));

        it("set MessageService in rootScope", inject(function (MessageService) {

            // call run
            var myModule = angular.module("budgeto");
            myModule._runBlocks[0][4]($location, $rootScope, $log, MessageService);
            $rootScope.$apply();

            expect($rootScope.MessageService).toBe(MessageService);
        }));
    });

    describe("providers configuration", function () {

        beforeEach(function () {
            module("budgeto");
        });

        it("$infiniteLoader take wait message", inject(function ($infiniteLoader) {
            expect($infiniteLoader.config().getMessage()).toBe("Work in progress. Pleas wait...");
        }));

        it("ApiServiceProvider take url", inject(function (ApiService) {
            expect(ApiService.config().getUrl()).toBe("http://localhost:9001/budgeto-api");
        }));

        it("LoadingService take services", inject(function (LoadingService) {
            expect(LoadingService.config().getServicesNames().length).toBe(1);
            expect(LoadingService.config().getServicesNames()).toContain("ApiService");
        }));

        it("$modalError take modal error messages", inject(function ($modalError) {
            expect($modalError.config().getMessage().title).toBe("Error");
            expect($modalError.config().getMessage().message).toBe("An error occured, please advice us.");
            expect($modalError.config().getMessage().close).toBe("Close");
        }));
    });
});