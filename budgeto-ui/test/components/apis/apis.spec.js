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

    describe("ApisResource", function () {

        var $httpBackend;

        beforeEach(function () {
            module("budgeto.apis");

            inject(function (_$httpBackend_) {
                $httpBackend = _$httpBackend_;
            });
        });

        afterEach(function() {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });

        it("all get the url path in parameter", inject(function (ApisResource) {
            $httpBackend.whenGET("urlofMyApi").respond({label:"2"});

            var prom = ApisResource.all("urlofMyApi");
            $httpBackend.flush();

            prom.then(function(data){
                expect(JSON.stringify(data)).toBe(JSON.stringify({label:"2"}));
            }).catch(function(){
                expect(false).toBe(true);
            });
        }));
    });

    describe("provider ApiService", function () {

        beforeEach(function () {
            module("budgeto.apis");
        });

        it("initialised", inject(function (ApiService) {
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

    describe("configuration of provider ApiService", function () {
        var ApiServiceProviderMock;
        var $rootScope;
        var $log;
        var $q;

        beforeEach(function () {
            module("budgeto.apis", function (ApiServiceProvider) {
                ApiServiceProviderMock = ApiServiceProvider;
            });

            inject(function (_$rootScope_, _$log_, _$q_) {
                $rootScope = _$rootScope_;
                $log = _$log_;
                $q = _$q_;
            });
        });

        it("have a valid provider", inject(function () {
            expect(ApiServiceProviderMock).not.toBeNull();
        }));

        it("not set url so failed", inject(function (ApisResource) {
            var deferred = $q.defer();
            deferred.reject("no url set");
            spyOn(ApisResource, "all").and.returnValue(deferred.promise);
            var ApiService = ApiServiceProviderMock.$get[2]($log, ApisResource);

            ApiService.loaded().then(function () {
                 expect(true).toBe(false);
             }).catch(function () {
                 expect(true).toBe(true);
             });
            $rootScope.$apply();

            expect(ApisResource.all).toHaveBeenCalledWith("noUrlSet");
        }));

        it("get result ok", inject(function (ApisResource) {
            var deferred = $q.defer();
            deferred.resolve(apis);
            spyOn(ApisResource, "all").and.returnValue(deferred.promise);
            ApiServiceProviderMock.setUrl("test/components/apis/apis.json");
            var ApiService = ApiServiceProviderMock.$get[2]($log, ApisResource);
            spyOn(ApiService, "loadApis").and.callThrough();

            ApiService.loaded().then(function () {
                 expect(true).toBe(true);
             }).catch(function () {
                 expect(true).toBe(false);
             });
            $rootScope.$apply();

            expect(ApisResource.all).toHaveBeenCalledWith("test/components/apis/apis.json");
            expect(ApiService.loadApis).toHaveBeenCalledWith(apis);
        }));
    });
});