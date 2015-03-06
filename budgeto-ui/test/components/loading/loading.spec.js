"use strict";

describe("Budgeto loading module", function () {
    describe("", function () {
        var $rootScope;
        var scope;
        var $log;
        var $location;
        var $controller;
        var $q;

        beforeEach(function () {
            module("budgeto.loading");

            inject(function (_$rootScope_, _$log_, _$location_, _$controller_, _$q_) {
                $rootScope = _$rootScope_;
                scope = _$rootScope_.$new();
                $log = _$log_;
                $location = _$location_;
                $controller = _$controller_;
                $q = _$q_;
            });
        });

        describe("LoadingCtrl", function () {

            it("clean $location.search 'sourcePage' and call the infinite loader to be showed", inject(function ($infiniteLoader, LoadingService) {
                spyOn($location, "search").and.callThrough();
                spyOn($infiniteLoader, "show").and.callThrough();

                $controller("LoadingCtrl", {
                    $scope: scope,
                    "$location": $location,
                    "$log": $log,
                    "LoadingService": LoadingService,
                    "$infiniteLoader": $infiniteLoader
                });
                $rootScope.$apply();

                expect($location.search).toHaveBeenCalledWith();
                expect($location.search).toHaveBeenCalledWith("sourcePage", null);
                expect($infiniteLoader.show).toHaveBeenCalledWith();
                expect(scope.loadFail).toBe(false);
                expect($location.path()).toBe("/");
            }));

            it(" clean $location.search 'sourcePage' with empty value", inject(function ($infiniteLoader, LoadingService) {
                spyOn($location, "search").and.returnValue({});
                spyOn($infiniteLoader, "show").and.callThrough();

                $controller("LoadingCtrl", {
                    $scope: scope,
                    "$location": $location,
                    "$log": $log,
                    "LoadingService": LoadingService,
                    "$infiniteLoader": $infiniteLoader
                });
                $rootScope.$apply();

                expect($location.search).toHaveBeenCalledWith();
                expect($location.search).toHaveBeenCalledWith("sourcePage", null);
                expect(scope.loadFail).toBe(false);
                expect($location.path()).toBe("/");
            }));

            it(" clean $location.search 'sourcePage' with a value", inject(function ($infiniteLoader, LoadingService) {
                spyOn($location, "search").and.returnValue({sourcePage:"mypage"});
                spyOn($infiniteLoader, "show").and.callThrough();

                $controller("LoadingCtrl", {
                    $scope: scope,
                    "$location": $location,
                    "$log": $log,
                    "LoadingService": LoadingService,
                    "$infiniteLoader": $infiniteLoader
                });
                $rootScope.$apply();

                expect($location.search).toHaveBeenCalledWith();
                expect($location.search).toHaveBeenCalledWith("sourcePage", null);
                expect(scope.loadFail).toBe(false);
                expect($location.path()).toBe("/mypage");
            }));

            it("with LoadingService that load ok change path to /", inject(function ($infiniteLoader, LoadingService) {
                var customDefer = $q.defer();
                customDefer.resolve(true);

                spyOn($location, "path").and.callThrough();
                spyOn($infiniteLoader, "hide").and.callThrough();
                spyOn(LoadingService, "loaded").and.returnValue(customDefer.promise);

                $controller("LoadingCtrl", {
                    $scope: scope,
                    "$location": $location,
                    "$log": $log,
                    "LoadingService": LoadingService,
                    "$infiniteLoader": $infiniteLoader
                });
                $rootScope.$apply();

                expect(LoadingService.loaded).toHaveBeenCalledWith();
                expect($infiniteLoader.hide).toHaveBeenCalledWith();
                expect($location.path).toHaveBeenCalledWith("/");
                expect(scope.loadFail).toBe(false);
                expect($location.path()).toBe("/");
            }));

            it("with LoadingService that load fail no change to path (empty path)", inject(function ($infiniteLoader, LoadingService) {
                var customDefer = $q.defer();
                customDefer.reject("an error occured");

                spyOn($log, "error").and.callThrough();
                spyOn($location, "path").and.callThrough();
                spyOn($infiniteLoader, "hide").and.callThrough();
                spyOn(LoadingService, "loaded").and.returnValue(customDefer.promise);

                $controller("LoadingCtrl", {
                    $scope: scope,
                    "$location": $location,
                    "$log": $log,
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
        });

        describe("provider LoadingService", function () {
            it("initialised", inject(function (LoadingService) {
                expect(LoadingService).not.toBeNull();
            }));
        });
    });

    describe("configuration of provider LoadingService", function () {
        var LoadingServiceProviderMock;
        var $log;
        var $q;
        var $injector;
        var $rootScope;

        beforeEach(function () {
            module("budgeto.loading", function (LoadingServiceProvider, $provide) {
                LoadingServiceProviderMock = LoadingServiceProvider;


                $provide.factory("Service1", function () {
                    return {
                        loaded: function () {
                            var customDefer = $q.defer();
                            customDefer.resolve(true);
                            return customDefer.promise;
                        }
                    };
                });
                $provide.factory("Service2", function () {
                    return {
                        loaded: function () {
                            var customDefer = $q.defer();
                            customDefer.resolve(true);
                            return customDefer.promise;
                        }
                    };
                });
                $provide.factory("Service3", function () {
                    return {
                        loaded: function () {
                            var customDefer = $q.defer();
                            customDefer.resolve(true);
                            return customDefer.promise;
                        }
                    };
                });
                $provide.factory("ServiceFail1", function () {
                    return {
                        loaded: function () {
                            var customDefer = $q.defer();
                            customDefer.reject("not available");
                            return customDefer.promise;
                        }
                    };
                });
            });

            inject(function (_$log_, _$q_, _$injector_, _$rootScope_) {
                $log = _$log_;
                $q = _$q_;
                $injector = _$injector_;
                $rootScope = _$rootScope_;
            });
        });

        it("have a valid provider", inject(function () {
            expect(LoadingServiceProviderMock).not.toBeNull();
        }));

        it("take 0 services names", inject(function () {
            var LoadingService = LoadingServiceProviderMock.$get[3]($log, $q, $injector);

            LoadingService.loaded().then(function () {
                expect(true).toBe(true);
            }).catch(function () {
                expect(true).toBe(false);
            });
            $rootScope.$apply();

        }));

        it("take 3 services that are correct", inject(function () {
            LoadingServiceProviderMock.add("Service1");
            LoadingServiceProviderMock.add("Service2");
            LoadingServiceProviderMock.add("Service3");

            var LoadingService = LoadingServiceProviderMock.$get[3]($log, $q, $injector);

            LoadingService.loaded().then(function () {
                expect(true).toBe(true);
            }).catch(function () {
                expect(true).toBe(false);
            });
            $rootScope.$apply();
        }));

        it("take 2 services and one fail", inject(function () {
            LoadingServiceProviderMock.add("Service2");
            LoadingServiceProviderMock.add("ServiceFail1");

            var LoadingService = LoadingServiceProviderMock.$get[3]($log, $q, $injector);

            LoadingService.loaded().then(function () {
                expect(true).toBe(false);
            }).catch(function () {
                expect(true).toBe(true);
            });
            $rootScope.$apply();
        }));
    });
});