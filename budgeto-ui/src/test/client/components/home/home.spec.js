'use strict';

define(['components/home/home', 'angular-mocks'], function() {

    describe('Budgeto home module', function () {
        var $rootScope;
        var scope;
        var $log;
        var $state;
        var $controller;
        var $q;

        beforeEach(function () {
            module('budgeto.home', function($stateProvider){
                $stateProvider
                    .state('home', {
                        url: '/home'
                    })
                    .state('mySelectedPath', {
                        url: '/mySelectedPath'
                    });
            });

            inject(function (_$rootScope_, _$log_, _$state_, _$controller_, _$q_) {
                $rootScope = _$rootScope_;
                scope = _$rootScope_.$new();
                $log = _$log_;
                $state = _$state_;
                $controller = _$controller_;
                $q = _$q_;
            });
        });


        describe('HomeCtrl', function () {

            it('loadFail at false and call the infinite loader to be showed', inject(function (ApiService, $infiniteLoader, LoadingService) {
                spyOn($infiniteLoader, 'show').and.callThrough();

                var homeCtrl = $controller('HomeCtrl', {
                    '$scope': scope,
                    '$state': $state,
                    '$log': $log,
                    'ApiService': ApiService,
                    '$infiniteLoader': $infiniteLoader,
                    'LoadingService': LoadingService
                });
                $rootScope.$apply();

                expect($infiniteLoader.show).toHaveBeenCalledWith();
                expect(homeCtrl.loadFail).toBe(false);
            }));

            it('with LoadingService that load ok apis is get and set in scope, and infiniteLoader hide', inject(function (ApiService, $infiniteLoader, LoadingService) {
                var customDefer = $q.defer();
                customDefer.resolve(true);

                var apis = [{url:'http://'}];
                spyOn(ApiService, 'findAll').and.returnValue(apis);
                spyOn(LoadingService, 'loaded').and.returnValue(customDefer.promise);
                spyOn($infiniteLoader, 'hide').and.callThrough();

                var homeCtrl = $controller('HomeCtrl', {
                    '$scope': scope,
                    '$state': $state,
                    '$log': $log,
                    'ApiService': ApiService,
                    '$infiniteLoader': $infiniteLoader,
                    'LoadingService': LoadingService
                });
                $rootScope.$apply();

                expect(homeCtrl.apis).not.toBeNull();
                expect(homeCtrl.apis).toBe(apis);
                expect($infiniteLoader.hide).toHaveBeenCalledWith();
            }));

            it('with LoadingService that load fail, error logged, loadFail to false and infiniteLoader hide', inject(function (ApiService, $infiniteLoader, LoadingService) {
                var customDefer = $q.defer();
                customDefer.reject('an error occured');

                spyOn($log, 'error').and.callThrough();
                spyOn($infiniteLoader, 'hide').and.callThrough();
                spyOn(LoadingService, 'loaded').and.returnValue(customDefer.promise);

                var homeCtrl = $controller('HomeCtrl', {
                    '$scope': scope,
                    '$state': $state,
                    '$log': $log,
                    'ApiService': ApiService,
                    '$infiniteLoader': $infiniteLoader,
                    'LoadingService': LoadingService
                });
                $rootScope.$apply();

                expect(LoadingService.loaded).toHaveBeenCalledWith();
                expect($infiniteLoader.hide).toHaveBeenCalledWith();
                expect($log.error).toHaveBeenCalledWith('error getting apis /', 'an error occured');
                expect(homeCtrl.loadFail).toBe(true);
            }));

            it(' function changePath with a path change the path to selected', inject(function (ApiService, $infiniteLoader, LoadingService) {
                spyOn($state, 'go').and.callThrough({});

                var homeCtrl = $controller('HomeCtrl', {
                    '$scope': scope,
                    '$state': $state,
                    '$log': $log,
                    'ApiService': ApiService,
                    '$infiniteLoader': $infiniteLoader,
                    'LoadingService': LoadingService
                });
                homeCtrl.changePath('mySelectedPath');
                scope.$apply();

                expect($state.current).not.toBeNull();
                expect($state.current.name).toBe('mySelectedPath');
                expect($state.current.url).toBe('/mySelectedPath');
            }));

            it('function changePath with a path null change the path to /home', inject(function (ApiService, $infiniteLoader, LoadingService) {
                spyOn($state, 'go').and.callThrough({});

                var homeCtrl = $controller('HomeCtrl', {
                    '$scope': scope,
                    '$state': $state,
                    '$log': $log,
                    'ApiService': ApiService,
                    '$infiniteLoader': $infiniteLoader,
                    'LoadingService': LoadingService
                });
                homeCtrl.changePath();
                scope.$apply();

                expect($state.current).not.toBeNull();
                expect($state.current.name).toBe('home');
                expect($state.current.url).toBe('/home');
            }));

            it('function changePath with a path defined but not exist change the path to /home', inject(function (ApiService, $infiniteLoader, LoadingService) {
                spyOn($state, 'go').and.callThrough({});
                spyOn($log, 'error').and.callThrough({});

                var homeCtrl = $controller('HomeCtrl', {
                    '$scope': scope,
                    '$state': $state,
                    '$log': $log,
                    'ApiService': ApiService,
                    '$infiniteLoader': $infiniteLoader,
                    'LoadingService': LoadingService
                });
                homeCtrl.changePath('unexisting');
                scope.$apply();

                expect($state.current).not.toBeNull();
                expect($state.current.name).toBe('home');
                expect($log.error).toHaveBeenCalledWith('unknown path', 'unexisting', ':', jasmine.any(Error));
            }));
        });
    });
});