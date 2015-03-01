"use strict";

describe("Budgeto modalError module", function () {
    var $rootScope;
    var scope;
    var $log;
    var $location;
    var $controller;
    var $modal;
    var $document;
    var body;

    beforeEach(function () {
        module("budgeto.modalError");

        $document = angular.element(document);
        module(function ($provide) {
            $provide.value("$document", $document);
        });
        body = $document.find("body").eq(0);

        inject(function (_$rootScope_, _$log_, _$location_, _$controller_, _$modal_) {
            $rootScope = _$rootScope_;
            scope = _$rootScope_.$new();
            $log = _$log_;
            $location = _$location_;
            $controller = _$controller_;
            $modal = _$modal_;
        });
    });

    describe("controller ModalErrorInstanceCtrl", function () {
        it("on close method, call close on $modalError provider to close the modal", inject(function ($modalError) {
            spyOn($modalError, "close").and.callThrough();

            $controller("ModalErrorInstanceCtrl", {$scope: scope, "$log": $log, "$modalError": $modalError});

            scope.close();
            $rootScope.$apply();

            expect($modalError.close).toHaveBeenCalledWith();
        }));
    });

    describe("provider $modalError", function () {
        beforeEach(function () {
            spyOn($modal, "open").and.callThrough();
            spyOn($location, "path").and.callThrough();
        });

        it("initialised", inject(function ($modalError) {
            $rootScope.$apply();

            expect($modalError).not.toBeNull();
        }));

        it("open method open a modal", inject(function ($modalError) {

            var modalInstance = $modalError.open();
            $rootScope.$apply();

            expect(modalInstance).not.toBeNull();
            expect($modal.open).toHaveBeenCalledWith({
                controller: "ModalErrorInstanceCtrl",
                template: jasmine.any(String)
            });
        }));

        it("closed method redirect to /", inject(function ($modalError) {
            var modalInstance = $modalError.open();
            spyOn(modalInstance, "dismiss").and.callThrough();

            $modalError.close();
            $rootScope.$apply();

            expect($location.path).toHaveBeenCalledWith("/");
            expect($location.path()).toBe("/");
            expect(modalInstance.dismiss).toHaveBeenCalledWith("close");
        }));

        it("but no call open before closed method no redirect to /", inject(function ($modalError) {
            $modalError.close();
            $rootScope.$apply();

            expect($location.path).not.toHaveBeenCalledWith("/");
            expect($location.path()).toBe("");
        }));
    });
});