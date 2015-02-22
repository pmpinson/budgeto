'use strict';

describe("Budgeto infiniteLoader module", function() {
    beforeEach(module('budgeto.modalError'));

    var $location;
    var $rootScope;
    var $controller;
    var scope;
    var element;

    beforeEach(inject(function(_$location_, _$rootScope_, _$controller_){
        $location = _$location_;
        $rootScope = _$rootScope_;
        $controller = _$controller_;

        scope = _$rootScope_.$new();
      }));

//      function getGoodDirective(body, asElement=false) {
//        if (asElement) {
//             element = $compile('<div data-modalerror></div>')($scope);
//        } else {
//            element = $compile('<modalerror></modalerror>')($scope);
//        }
//        body.appendChild(element[0]);
//      }
//
//      function removeDirective(body) {
//        body.removeChild(element[0]);
//      }

  it('directive present and call to hide ok do nothing', inject(function($modalError, $modal) {
        spyOn($modal, 'open');

        $modalError.open();

        expect($modal.open).toHaveBeenCalledWith({controller: 'ModalErrorInstanceCtrl', template: '<div><div class="modal-header"><h3 class="modal-title">{{MessageService.errorTitle}}</h3></div><div class="modal-body"><p>{{MessageService.error}}</p></div><div class="modal-footer"><button class="btn btn-primary" ng-click="close()">{{MessageService.closeTitle}}</button></div></div>'});
      }));

  it('directive present and call to show ok', inject(function($modal) {
        var modalInstance = $modal.open({template: '<div></div>'});
        spyOn($location, 'path');
        spyOn(modalInstance, 'dismiss');

        var ctrl = $controller('ModalErrorInstanceCtrl', {$scope: scope, '$modalInstance':modalInstance, '$location':$location});

        scope.close();
        expect($location.path).toHaveBeenCalledWith('/');
        expect(modalInstance.dismiss).toHaveBeenCalledWith('close');
      }));
//
//  it('directive present and call to show and hide ok', inject(function($infiniteLoader) {
//        getGoodDirective(document.body);
//
//        $infiniteLoader.show();
//        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default"><p class="ng-binding">themessage</p></div>');
//        $infiniteLoader.hide();
//        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p class="ng-binding">themessage</p></div>');
//
//        removeDirective(document.body);
//      }));
//
//  it('directive present and call to show 2 times and hide ok', inject(function($infiniteLoader) {
//        getGoodDirective(document.body);
//
//        $infiniteLoader.show();
//        $infiniteLoader.show();
//        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default"><p class="ng-binding">themessage</p></div>');
//        $infiniteLoader.hide();
//        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default"><p class="ng-binding">themessage</p></div>');
//
//        removeDirective(document.body);
//      }));
//
//  it('directive no tpresent and call to show', inject(function($infiniteLoader) {
//        spyOn(console, 'error');
//
//        $infiniteLoader.show();
//        expect(console.error).toHaveBeenCalledWith('have you setup the infiniteLoader directive');
//      }));
//
//  it('directive no tpresent and call to hide', inject(function($infiniteLoader) {
//        spyOn(console, 'error');
//
//        $infiniteLoader.hide();
//        expect(console.error).toHaveBeenCalledWith('have you setup the infiniteLoader directive');
//      }));
});