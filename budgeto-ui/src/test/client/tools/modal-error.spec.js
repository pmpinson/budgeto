'use strict';

define(['javascripts/tools/modal-error', 'angular-mocks'], function() {

    describe('Budgeto modalError module', function () {

        describe('controller ModalErrorInstanceCtrl', function () {
            var $rootScope;
            var scope;
            var $log;
            var $state;
            var $controller;

            beforeEach(function () {
                module('budgeto.modalError');

                inject(function (_$rootScope_, _$log_, _$state_, _$controller_) {
                    $rootScope = _$rootScope_;
                    scope = _$rootScope_.$new();
                    $log = _$log_;
                    $state = _$state_;
                    $controller = _$controller_;
                });
            });

            it('have a good initialisation', inject(function ($modalError) {
                spyOn($modalError, 'close').and.callThrough();

                var modalOptions = {};
                var modalErrorInstanceCtrl = $controller('ModalErrorInstanceCtrl', {
                    '$scope': scope,
                    '$log': $log,
                    '$modalError': $modalError,
                    'modalOptions': modalOptions
                });

                expect(modalErrorInstanceCtrl.utils).not.toBeUndefined();
                expect(modalErrorInstanceCtrl.modalOptions).toBe(modalOptions);
            }));

            it('on close method, call close on $modalError provider to close the modal', inject(function ($modalError) {
                spyOn($modalError, 'close').and.callThrough();

                var modalOptions = {};
                var modalErrorInstanceCtrl = $controller('ModalErrorInstanceCtrl', {
                    '$scope': scope,
                    '$log': $log,
                    '$modalError': $modalError,
                    'modalOptions': modalOptions
                });

                modalErrorInstanceCtrl.close();

                expect($modalError.close).toHaveBeenCalledWith();
            }));
        });

        describe('provider $modalError', function () {
            var $rootScope;
            var scope;
            var $log;
            var $state;
            var $controller;
            var $modal;
            var $httpBackend;

            beforeEach(function () {
                module('budgeto.modalError', function($stateProvider){
                    $stateProvider
                        .state('home', {});
                });

                inject(function (_$rootScope_, _$log_, _$state_, _$controller_, _$modal_, _$httpBackend_) {
                    $rootScope = _$rootScope_;
                    scope = _$rootScope_.$new();
                    $log = _$log_;
                    $state = _$state_;
                    $controller = _$controller_;
                    $modal = _$modal_;
                    $httpBackend = _$httpBackend_;

                    spyOn($modal, 'open').and.callThrough();
                    spyOn($state, 'go').and.callThrough();
                });
            });

            afterEach(function() {
                $httpBackend.verifyNoOutstandingExpectation();
                $httpBackend.verifyNoOutstandingRequest();
            });

            it('initialised', inject(function ($modalError) {
                expect($modalError).not.toBeNull();
            }));

            it('open method open a modal', inject(function ($modalError) {
                $httpBackend.whenGET('template/tools/modal-error.html').respond('<div></div>');

                var options = {val:'myval'};
                spyOn($modalError, 'prepareOptions').and.callThrough();
                var modalInstance = $modalError.open(options);

                expect(modalInstance).not.toBeNull();
                expect($modal.open).toHaveBeenCalledWith({
                    controller: 'ModalErrorInstanceCtrl as modalErrorInstanceCtrl',
                    templateUrl: 'template/tools/modal-error.html',
                    resolve: {
                        modalOptions: jasmine.any(Function)
                    }
                });
                $httpBackend.flush();
                expect($modalError.prepareOptions).toHaveBeenCalledWith(options);
            }));

            it('prepareOptions method extends default options', inject(function ($modalError) {
                var options = $modalError.prepareOptions({});

                expect(options.title).toBe('Error');
                expect(options.detail).toBe('detail');
                expect(options.close).toBe('OK');
                expect(options.logMessages).toBeUndefined();
                expect(options.reason).toBeUndefined();
            }));

            it('prepareOptions method with value extends default options', inject(function ($modalError) {
                var options = $modalError.prepareOptions({
                    logMessages: 'a message',
                    reason: 'cause by',
                    title: 'Error modal',
                    detail: 'detail of error',
                    close: 'Close'
                });

                expect(options.title).toBe('Error modal');
                expect(options.detail).toBe('detail of error');
                expect(options.close).toBe('Close');
                expect(options.logMessages).toBe('a message');
                expect(options.reason).toBe('cause by');
            }));

            it('closed method redirect to home', inject(function ($modalError) {
                $httpBackend.whenGET('template/tools/modal-error.html').respond('<div></div>');
                var modalInstance = $modalError.open();
                $httpBackend.flush();
                spyOn(modalInstance, 'dismiss').and.callThrough();

                $modalError.close();
                $rootScope.$apply();

                expect($state.go).toHaveBeenCalledWith('home');
                expect(modalInstance.dismiss).toHaveBeenCalledWith('close');
            }));

            it('closed by escape or backdrop redirect to home', inject(function ($modalError) {
                $httpBackend.whenGET('template/tools/modal-error.html').respond('<div></div>');
                var modalInstance = $modalError.open();
                $httpBackend.flush();
                spyOn(modalInstance, 'close').and.callThrough();

                modalInstance.close();
                $rootScope.$apply();

                expect($state.go).toHaveBeenCalledWith('home');
                expect(modalInstance.close).toHaveBeenCalledWith();
            }));

            it('closed do nothing if open not called before', inject(function ($modalError) {
                $modalError.close();
                $rootScope.$apply();

                expect($state.go).not.toHaveBeenCalledWith('home');
            }));

            it('manageError function help to get good information on modal', inject(function ($modalError) {
                $httpBackend.whenGET('template/tools/modal-error.html').respond('<div></div>');
                spyOn($log, 'error').and.callThrough();
                spyOn($modalError, 'open').and.callThrough();

                $modalError.manageError()();
                $httpBackend.flush();

                expect($log.error).toHaveBeenCalledWith(jasmine.any(Array), undefined);
                expect($modalError.open).toHaveBeenCalledWith({logMessages: jasmine.any(Array), reason:undefined});
            }));

            it('manageError function with params help to get good information on modal', inject(function ($modalError) {
                $httpBackend.whenGET('template/tools/modal-error.html').respond('<div></div>');
                spyOn($log, 'error').and.callThrough();
                spyOn($modalError, 'open').and.callThrough();

                var message = 'a message';
                var object = {param:'an object'};
                var reason = 'an error';
                $modalError.manageError(message, object)(reason);
                $httpBackend.flush();

                expect($log.error).toHaveBeenCalledWith([message, object], reason);
                expect($modalError.open).toHaveBeenCalledWith({logMessages: [message, object], reason:reason});
            }));
        });
    });
});