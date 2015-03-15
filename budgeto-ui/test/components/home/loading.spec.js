"use strict";

define(['components/home/loading', 'angular-mocks'], function() {

    describe("Budgeto loading module", function () {

        describe("provider LoadingService", function () {

            beforeEach(function () {
                module("budgeto.loading");

                inject(function () {
                });
            });

            it("initialised", inject(function (LoadingService) {
                expect(LoadingService).not.toBeNull();
            }));
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
});