'use strict';

describe("Budgeto apis module", function () {
    describe("", function () {
        var $rootScope;
        var scope;
        var $log;

        beforeEach(function () {
            module('budgeto.apis');

            inject(function (_$rootScope_, _$log_) {
                $rootScope = _$rootScope_;
                scope = _$rootScope_.$new();
                $log = _$log_;
            });
        });

        //describe("LoadingCtrl", function () {
        //
        //    it(' clean $location.search "sourcePage" and call the infinite loader to be showed', inject(function ($infiniteLoader, LoadingService) {
        //        spyOn($location, 'search').and.callThrough();
        //        spyOn($infiniteLoader, 'show').and.callThrough();
        //
        //        var ctrl = $controller('LoadingCtrl', {
        //            $scope: scope,
        //            '$location': $location,
        //            '$log': $log,
        //            'LoadingService': LoadingService,
        //            '$infiniteLoader': $infiniteLoader
        //        });
        //        $rootScope.$apply();
        //
        //        expect($location.search).toHaveBeenCalledWith();
        //        expect($location.search).toHaveBeenCalledWith('sourcePage', null);
        //        expect($infiniteLoader.show).toHaveBeenCalledWith();
        //        expect(scope.loadFail).toBe(false);
        //        expect($location.path()).toBe('/');
        //    }));
        //
        //    it('with LoadingService that load ok change path to /', inject(function ($infiniteLoader, LoadingService) {
        //        var customDefer = $q.defer();
        //        customDefer.resolve(true);
        //
        //        spyOn($location, 'path').and.callThrough();
        //        spyOn($infiniteLoader, 'hide').and.callThrough();
        //        spyOn(LoadingService, 'loaded').and.returnValue(customDefer.promise);
        //
        //        var ctrl = $controller('LoadingCtrl', {
        //            $scope: scope,
        //            '$location': $location,
        //            '$log': $log,
        //            'LoadingService': LoadingService,
        //            '$infiniteLoader': $infiniteLoader
        //        });
        //        $rootScope.$apply();
        //
        //        expect(LoadingService.loaded).toHaveBeenCalledWith();
        //        expect($infiniteLoader.hide).toHaveBeenCalledWith();
        //        expect($location.path).toHaveBeenCalledWith('/');
        //        expect(scope.loadFail).toBe(false);
        //        expect($location.path()).toBe('/');
        //    }));
        //
        //    it('with LoadingService that load fail no change to path (empty path)', inject(function ($infiniteLoader, LoadingService) {
        //        var customDefer = $q.defer();
        //        customDefer.reject('an error occured');
        //
        //        spyOn($log, 'error').and.callThrough();
        //        spyOn($location, 'path').and.callThrough();
        //        spyOn($infiniteLoader, 'hide').and.callThrough();
        //        spyOn(LoadingService, 'loaded').and.returnValue(customDefer.promise);
        //
        //        var ctrl = $controller('LoadingCtrl', {
        //            $scope: scope,
        //            '$location': $location,
        //            '$log': $log,
        //            'LoadingService': LoadingService,
        //            '$infiniteLoader': $infiniteLoader
        //        });
        //        $rootScope.$apply();
        //
        //        expect(LoadingService.loaded).toHaveBeenCalledWith();
        //        expect($infiniteLoader.hide).toHaveBeenCalledWith();
        //        expect($location.path).not.toHaveBeenCalledWith(jasmine.any(String));
        //        expect($log.error).toHaveBeenCalledWith('error getting apis / ', 'an error occured');
        //        expect(scope.loadFail).toBe(true);
        //        expect($location.path()).toBe('');
        //    }));
        //});

        describe("factory ApisResource", function (ApisResource) {
            it('initialised', inject(function (ApisResource) {
                $rootScope.$apply();

                expect(ApisResource).not.toBeNull();
            }));
        });

        describe("provider ApiService", function (ApiService) {
            it('initialised', inject(function (ApiService) {
                $rootScope.$apply();

                expect(ApiService).not.toBeNull();
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
            module('budgeto.apis', function (ApiServiceProvider) {
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

        it('have a valid provider', inject(function () {
            $rootScope.$apply();

            expect(ApiServiceProviderMock).not.toBeNull();
        }));

        it('not set url so failed', inject(function () {

            $httpBackend.whenGET('noUrlSet').respond(500, '');
            var ApiService = ApiServiceProviderMock.$get[2]($log, ApisResource);

            spyOn(ApisResource, 'all').and.callThrough();
            ApiService.loaded().then(function (data) {
                expect(true).toBe(false);
            }).catch(function (reason) {
                expect(true).toBe(true);
            });

            $rootScope.$apply();
            expect(ApisResource.all).toHaveBeenCalledWith('noUrlSet');
        }));

        describe("good url configuration", function () {
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
            var ApiService;

            beforeEach(function () {
                inject(function () {
                    $httpBackend.whenGET('test/components/apis/apis.json').respond(function (method, url, data) {
                        apis;
                    });

                    spyOn(ApisResource, 'all').and.callThrough();
                    ApiServiceProviderMock.setUrl('test/components/apis/apis.json');
                    ApiService = ApiServiceProviderMock.$get[2]($log, ApisResource);
                    $rootScope.$apply();
                });
            });

            it('get result ok', inject(function () {
                ApiService.loaded().then(function (data) {
                    expect(data).not.toBeNull(true);
                    expect(data.links.length).toBe(3);
                }).catch(function (reason) {
                    expect(true).toBe(false);
                });

                $rootScope.$apply();
                expect(ApisResource.all).toHaveBeenCalledWith('test/components/apis/apis.json');
            }));

            it('get result by findAll', inject(function () {
                ApiService.loaded().then(function (data) {
                    expect(ApiService.findAll()).not.toBeNull();
                    expect(ApiService.findAll().links.length).toBe(3);
                }).catch(function (reason) {
                    expect(true).toBe(false);
                });

                $rootScope.$apply();
            }));

            it('get result by find api exist', inject(function () {
                ApiService.loaded().then(function (data) {
                    expect(ApiService.find('api1')).not.toBeNull();
                    expect(ApiService.find('api1').rel).toBe('api1');
                    expect(ApiService.find('api1').href).toBe('http://api1.com');
                }).catch(function (reason) {
                    expect(true).toBe(false);
                });

                $rootScope.$apply();
            }));

            it('get result by find api not exist', inject(function () {
                ApiService.loaded().then(function (data) {
                    expect(ApiService.find('api3')).toBeNull();
                }).catch(function (reason) {
                    expect(true).toBe(false);
                });

                $rootScope.$apply();
            }));

            it('getLink find the good link', inject(function () {
                $rootScope.$apply();
                expect(ApiService.getLink('api2', apis.links)).not.toBeNull();
                expect(ApiService.getLink('api3', apis.links)).toBeNull();
            }));

        });
    });
});