'use strict';

define(['budgeto', '../../../target/dist/app/lib/angular/angular.min', 'angular-mocks'], function(test, angular) {

    describe('Budgeto module', function () {

        describe('constants configuration', function () {

            beforeEach(function () {
                module('budgeto');
            });

            it('BudgetoRestApiURL to be correct', inject(function (BudgetoRestApiURL) {
                expect(BudgetoRestApiURL).toEqual('http://localhost:9001/budgeto-api');
            }));

            it('moment tz to be correct', inject(function (angularMomentConfig) {
                expect(angularMomentConfig).toEqual({timezone: 'UTC'});
            }));

            it('messages defined correctly', inject(function (MessageService) {
                expect(Object.keys(MessageService).length).toBe(7);
                expect(Object.keys(MessageService.apisLinks).length).toBe(2);
                expect(Object.keys(MessageService.apisTitles).length).toBe(2);
                expect(Object.keys(MessageService.modalError).length).toBe(3);
            }));
        });

        describe('application run', function () {
            var $log;
            var $state;
            var $rootScope;

            beforeEach(function () {
                module('budgeto');

                inject(function (_$log_, _$state_, _$rootScope_) {
                    $log = _$log_;
                    $state = _$state_;
                    $rootScope = _$rootScope_;
                });
            });

            it('redirecting to /loading and keep source path', inject(function (MessageService) {
                spyOn($state, 'transitionTo').and.callThrough();

                // call run
                var myModule = angular.module('budgeto');
                myModule._runBlocks[0][4]($state, $rootScope, $log, MessageService);

                expect($state.transitionTo.calls.count()).toBe(1);
                expect($state.transitionTo).toHaveBeenCalledWith('home');
            }));

            it('set MessageService in rootScope', inject(function (MessageService) {

                // call run
                var myModule = angular.module('budgeto');
                myModule._runBlocks[0][4]($state, $rootScope, $log, MessageService);

                expect($rootScope.MessageService).toBe(MessageService);
            }));
        });

        describe('providers configuration', function () {

            beforeEach(function () {
                module('budgeto');
            });

            it('$infiniteLoader take wait message', inject(function ($infiniteLoader) {
                expect($infiniteLoader.config().getMessage()).toBe('Work in progress. Pleas wait...');
            }));

            it('ApiServiceProvider take url', inject(function (ApiService) {
                expect(ApiService.config().getUrl()).toBe('http://localhost:9001/budgeto-api');
            }));

            it('LoadingService take services', inject(function (LoadingService) {
                expect(LoadingService.config().getServicesNames().length).toBe(1);
                expect(LoadingService.config().getServicesNames()).toContain('ApiService');
            }));

            it('$modalError take default options', inject(function ($modalError) {
                expect($modalError.config().getDefaultOptions().title).toBe('An error occured, please advice us.');
                expect($modalError.config().getDefaultOptions().close).toBe('Close');
                expect($modalError.config().getDefaultOptions().detail).toBe('Error detail');
                expect($modalError.config().getDefaultOptions().logMessages).toBeUndefined();
                expect($modalError.config().getDefaultOptions().reason).toBeUndefined();
            }));
        });
    });
});