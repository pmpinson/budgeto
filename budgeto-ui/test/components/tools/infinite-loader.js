'use strict';

describe("Budgeto infiniteLoader module", function() {
    beforeEach(module('budgeto.infiniteLoader'));

    var $compile;
    var $rootScope;
    var $scope;
    var $controller;
    var element;

    beforeEach(inject(function(_$compile_, _$rootScope_, _$controller_){
        $compile = _$compile_;
        $rootScope = _$rootScope_;
        $controller = _$controller_;
        $scope = _$rootScope_.$new();
      }));

  it('directive infiniteloader was compiled without message attribute', inject(function() {
        element = $compile('<div data-infiniteloader></div>')($scope);
        $rootScope.$digest();
        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p class="ng-binding"></p></div>');
      }));

  it('directive infiniteloader was compiled with message attribute non existing', inject(function() {
        element = $compile('<div data-infiniteloader data-msg="varmsg"></div>')($scope);
        $rootScope.$digest();
        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p class="ng-binding"></p></div>');
      }));

  it('directive infiniteloader was compiled with message attribute empty', inject(function() {
        element = $compile('<div data-infiniteloader data-msg="varmsg"></div>')($scope);
        $scope.varmsg =  '';
        $rootScope.$digest();
        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p class="ng-binding"></p></div>');
      }));

  it('directive infiniteloader was compiled with a message', inject(function() {
        element = $compile('<div data-infiniteloader data-msg="varmsg"></div>')($scope);
        $scope.varmsg =  'themessage';
        $rootScope.$digest();
        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p class="ng-binding">themessage</p></div>');
      }));

  it('directive infiniteloader was compiled as element with a message', inject(function() {
        element = $compile('<infiniteloader data-msg="varmsg"></infiniteloader>')($scope);
        $scope.varmsg =  'themessage';
        $rootScope.$digest();
        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p class="ng-binding">themessage</p></div>');
      }));

      function getGoodDirective(body) {
        element = $compile('<div data-infiniteloader data-msg="varmsg"></div>')($scope);
        $scope.varmsg =  'themessage';
        $rootScope.$digest();
        body.appendChild(element[0]);
      }

      function removeDirective(body) {
        body.removeChild(element[0]);
      }

  it('directive present and call to hide ok do nothing', inject(function($infiniteLoader) {
        getGoodDirective(document.body);

        $infiniteLoader.hide();
        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p class="ng-binding">themessage</p></div>');

        removeDirective(document.body);
      }));

  it('directive present and call to show ok', inject(function($infiniteLoader) {
        getGoodDirective(document.body);

        $infiniteLoader.show();
        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default"><p class="ng-binding">themessage</p></div>');

        removeDirective(document.body);
      }));

  it('directive present and call to show and hide ok', inject(function($infiniteLoader) {
        getGoodDirective(document.body);

        $infiniteLoader.show();
        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default"><p class="ng-binding">themessage</p></div>');
        $infiniteLoader.hide();
        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p class="ng-binding">themessage</p></div>');

        removeDirective(document.body);
      }));

  it('directive present and call to show 2 times and hide ok', inject(function($infiniteLoader) {
        getGoodDirective(document.body);

        $infiniteLoader.show();
        $infiniteLoader.show();
        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default"><p class="ng-binding">themessage</p></div>');
        $infiniteLoader.hide();
        expect(element.html()).toContain('<div class="infinite-loader infinite-loader-default"><p class="ng-binding">themessage</p></div>');

        removeDirective(document.body);
      }));

  it('directive no tpresent and call to show', inject(function($infiniteLoader) {
        spyOn(console, 'error');

        $infiniteLoader.show();
        expect(console.error).toHaveBeenCalledWith('have you setup the infiniteLoader directive');
      }));

  it('directive no tpresent and call to hide', inject(function($infiniteLoader) {
        spyOn(console, 'error');

        $infiniteLoader.hide();
        expect(console.error).toHaveBeenCalledWith('have you setup the infiniteLoader directive');
      }));
});