'use strict';

describe("Budgeto modalError module", function() {
    var $rootScope;
    var scope;
    var $log;
    var $location;
    var $controller;
    var $modal;
    var $document;
    var body;

    beforeEach(function() {
        module('budgeto.modalError');

        $document = angular.element(document);
        module(function($provide) {
            $provide.value('$document', $document);
        });
        body = $document.find('body').eq(0);

        inject(function(_$rootScope_, _$log_, _$location_, _$controller_, _$modal_){
                $rootScope = _$rootScope_;
                scope = _$rootScope_.$new();
                $log = _$log_;
                $location = _$location_;
                $controller= _$controller_;
                $modal = _$modal_;
              });
    });

  it('controller ModalErrorInstanceCtrl on close, call close on $modalError provider to close the modal', inject(function($modalError) {
        spyOn($modalError, 'close');

        var ctrl = $controller('ModalErrorInstanceCtrl', {$scope: scope, '$log':$log, '$modalError':$modalError});

        scope.close();
        expect($modalError.close).toHaveBeenCalledWith();
      }));

  it('provider $modalError initialised', inject(function($modalError) {
        expect($modalError).not.toBeNull();
      }));

  it('provider $modalError open method open a modal', inject(function($modalError) {
        spyOn($location, 'path');
        spyOn($modal, 'open');

        var modalInstance = $modalError.open();
        expect(modalInstance).not.toBeNull();
        expect($modal.open).toHaveBeenCalledWith({ controller: 'ModalErrorInstanceCtrl', template: jasmine.any(String)});
      }));

  it('provider $modalError closed method redirect to /', inject(function($modalError) {
        spyOn($location, 'path');

        var modalInstance = $modalError.open();
        spyOn(modalInstance, 'dismiss');

        $modalError.close();
        expect($location.path).toHaveBeenCalledWith('/');
        expect(modalInstance.dismiss).toHaveBeenCalledWith('close');
      }));

  it('provider $modalError but no call open before closed method no redirect to /', inject(function($modalError) {
        spyOn($location, 'path');

        $modalError.close();
        expect($location.path).not.toHaveBeenCalledWith('/');
      }));
});