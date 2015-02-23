'use strict';

describe("Budgeto infiniteLoader module", function() {
    var $document;
    var body;

    beforeEach(function(){
        module('budgeto.infiniteLoader');

        $document = angular.element(document);
        module(function($provide) {
            $provide.value('$document', $document);
        });
        body = $document.find('body').eq(0);

        inject(function(){
        });
    });

    afterEach(function() {
        $document.find('body').html('');
    });

  it('provider $infiniteLoader initialised', inject(function($infiniteLoader) {
        expect($infiniteLoader).not.toBeNull();
      }));

  it('provider infiniteloader add loader to document', inject(function($infiniteLoader) {
        expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p>Wait</p></div>');
      }));

  it('directive present and call to hide ok do nothing', inject(function($infiniteLoader) {
        $infiniteLoader.hide();
        expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p>Wait</p></div>');
      }));

  it('directive present and call to show ok', inject(function($infiniteLoader) {
        $infiniteLoader.show();
        expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default"><p>Wait</p></div>');
      }));

  it('directive present and call to show and hide ok', inject(function($infiniteLoader) {
        $infiniteLoader.show();
        expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default"><p>Wait</p></div>');
        $infiniteLoader.hide();
        expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p>Wait</p></div>');
      }));

  it('directive present and call to show 2 times and hide ok', inject(function($infiniteLoader) {
        $infiniteLoader.show();
        $infiniteLoader.show();
        expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default"><p>Wait</p></div>');
        $infiniteLoader.hide();
        expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default"><p>Wait</p></div>');
      }));
});

describe("Budgeto infiniteLoader module configuration", function() {
    var $infiniteLoaderProviderMock;
    var $log;
    var $document;
    var body;

    beforeEach(function() {
        module('budgeto.infiniteLoader', function($infiniteLoaderProvider) {
            $infiniteLoaderProviderMock = $infiniteLoaderProvider;
        });

        $document = angular.element(document);
        module(function($provide) {
            $provide.value('$document', $document);
        });
        body = $document.find('body').eq(0);

        inject(function(_$log_){
            $log = _$log_;
        });
    });

    afterEach(function() {
        $document.find('body').html('');
    });

    it('have a valid provider', inject(function() {
        expect($infiniteLoaderProviderMock).not.toBeNull();
    }));

    it('take a config message', inject(function() {
        $infiniteLoaderProviderMock.setMessage('the new wait message');

        var $infiniteLoader = $infiniteLoaderProviderMock.$get[2]($document, $log).show();
        expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default"><p>the new wait message</p></div>');
    }));
});