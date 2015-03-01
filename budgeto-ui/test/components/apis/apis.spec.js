"use strict";

describe("Budgeto apis module", function () {
    var apis = {
        "links": [
            {
                "rel": "self",
                "href": "test/components/apis/apis.json"
            },
            {
                "rel": "api1",
                "href": "http://api1.com"
            },
            {
                "rel": "api2",
                "href": "http://api2.com"
            }
        ]
    };

    describe("", function () {
        var $rootScope;
        var scope;
        var $log;

        beforeEach(function () {
            module("budgeto.apis");

            inject(function (_$rootScope_, _$log_) {
                $rootScope = _$rootScope_;
                scope = _$rootScope_.$new();
                $log = _$log_;
            });
        });

        describe("factory ApisResource", function (ApisResource) {
            it("initialised", inject(function (ApisResource) {
                $rootScope.$apply();

                expect(ApisResource).not.toBeNull();
            }));
        });

        describe("provider ApiService", function (ApiService) {
            it("initialised", inject(function (ApiService) {
                $rootScope.$apply();

                expect(ApiService).not.toBeNull();
            }));

            it("get result by findAll", inject(function (ApiService) {
                ApiService.loadApis(apis);
                expect(ApiService.findAll()).not.toBeNull();
                expect(ApiService.findAll().length).toBe(2);
            }));

            it("get result by find api exist", inject(function (ApiService) {
                ApiService.loadApis(apis);
                expect(ApiService.find("api1")).not.toBeNull();
                expect(ApiService.find("api1").rel).toBe("api1");
                expect(ApiService.find("api1").href).toBe("http://api1.com");
            }));

            it("get result by find api not exist", inject(function (ApiService) {
                ApiService.loadApis(apis);
                expect(ApiService.find("api3")).toBeNull();
            }));

            it("getLink find the good link", inject(function (ApiService) {
                expect(ApiService.getLink("api2", apis.links)).not.toBeNull();
                expect(ApiService.getLink("api3", apis.links)).toBeNull();
            }));
        });
    });

    describe("configuration of provider ApiService", function () {
        var ApiServiceProviderMock;
        var $rootScope;
        var $log;
        var $q;
        var $httpBackend;
        var ApisResource;

        beforeEach(function () {
            module("budgeto.apis", function (ApiServiceProvider) {
                ApiServiceProviderMock = ApiServiceProvider;
            });

            inject(function (_$rootScope_, _$log_, _$q_, _$httpBackend_, _ApisResource_) {
                $rootScope = _$rootScope_;
                $log = _$log_;
                $q = _$q_;
                $httpBackend = _$httpBackend_;
                ApisResource = _ApisResource_;
            });
        });

        it("have a valid provider", inject(function () {
            $rootScope.$apply();

            expect(ApiServiceProviderMock).not.toBeNull();
        }));

        it("not set url so failed", inject(function () {

            $httpBackend.whenGET("noUrlSet").respond(500, "");
            var ApiService = ApiServiceProviderMock.$get[2]($log, ApisResource);

            spyOn(ApisResource, "all").and.callThrough();
            ApiService.loaded().then(function (data) {
                expect(true).toBe(false);
            }).catch(function (reason) {
                expect(true).toBe(true);
            });

            $rootScope.$apply();
            expect(ApisResource.all).toHaveBeenCalledWith("noUrlSet");
        }));

        it("get result ok", inject(function () {

            $httpBackend.whenGET("test/components/apis/apis.json").respond(function (method, url, data) {
                return apis;
            });

            spyOn(ApisResource, "all").and.callThrough();
            ApiServiceProviderMock.setUrl("test/components/apis/apis.json");
            var ApiService = ApiServiceProviderMock.$get[2]($log, ApisResource);
            $rootScope.$apply();
            spyOn(ApiService, "loadApis").and.callThrough();
            ApiService.loaded().then(function (data) {
                expect(data).not.toBeNull(true);
                expect(data.links.length).toBe(3);
                expect(ApiService.loadApis).toHaveBeenCalledWith(apis);
            }).catch(function (reason) {
                expect(true).toBe(false);
            });

            $rootScope.$apply();
            expect(ApisResource.all).toHaveBeenCalledWith("test/components/apis/apis.json");
        }));
    });

    describe("configuration of provider ApiService with good url configuration", function () {
//        var ApiServiceProviderMock;
//        var $rootScope;
//        var $log;
//        var $q;
//        var $httpBackend;
//        var ApisResource;
    });
});