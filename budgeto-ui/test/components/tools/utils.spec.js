"use strict";

describe("Budgeto utils module", function () {

    beforeEach(function () {
        module("budgeto.utils");

        inject(function () {
        });
    });

    describe("provider $utils", function () {

        it("initialised", inject(function ($utils) {
            expect($utils).not.toBeNull();
        }));

        it("isObject method return true if it's an object", inject(function ($utils) {

            expect($utils.isObject({})).toBe(true);
            expect($utils.isObject({name:"value", val:12, sub:{label:"!!"}})).toBe(true);
        }));

        it("isObject method return false if it's not an object", inject(function ($utils) {

            expect($utils.isObject("4546546")).toBe(false);
            expect($utils.isObject(12)).toBe(false);
        }));

        it("formatObject method return object formatted", inject(function ($utils) {

            expect($utils.formatObject({})).toBe("{}");
            expect($utils.formatObject({name:"value", val:12, sub:{label:"!!"}})).toBe("{\n    \"name\": \"value\",\n    \"val\": 12,\n    \"sub\": {\n        \"label\": \"!!\"\n    }\n}");
            expect($utils.formatObject("4546546")).toBe("\"4546546\"");
            expect($utils.formatObject(12)).toBe("12");
        }));

//        it("closed method redirect to /", inject(function ($modalError) {
//            var modalInstance = $modalError.open();
//            spyOn(modalInstance, "dismiss").and.callThrough();
//
//            $modalError.close();
//
//            expect($location.path).toHaveBeenCalledWith("/");
//            expect($location.path()).toBe("/");
//            expect(modalInstance.dismiss).toHaveBeenCalledWith("close");
//        }));
//
//        it("but no call open before closed method no redirect to /", inject(function ($modalError) {
//            $modalError.close();
//
//            expect($location.path).not.toHaveBeenCalledWith("/");
//            expect($location.path()).toBe("");
//        }));
    });
});