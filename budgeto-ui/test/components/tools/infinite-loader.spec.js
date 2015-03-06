"use strict";

describe("Budgeto infiniteLoader module", function () {

    describe("provider $infiniteLoader", function () {
        var $document;
        var body;

        beforeEach(function () {
            module("budgeto.infiniteLoader");

            $document = angular.element(document);
            module(function ($provide) {
                $provide.value("$document", $document);
            });
            body = $document.find("body").eq(0);
        });

        afterEach(function () {
            $document.find("body").html("");
        });

        it("initialised", inject(function ($infiniteLoader) {
            expect($infiniteLoader).not.toBeNull();
        }));

        it("add html to document", inject(function ($infiniteLoader) {
            expect($infiniteLoader).not.toBeNull();
            expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p>Wait</p></div>');
        }));

        it("call to hide do nothing", inject(function ($infiniteLoader) {
            $infiniteLoader.hide();

            expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p>Wait</p></div>');
        }));

        it("call to show remove the hidden class", inject(function ($infiniteLoader) {
            $infiniteLoader.show();

            expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default"><p>Wait</p></div>');
        }));

        it("call show and after hide keep hidden class", inject(function ($infiniteLoader) {
            $infiniteLoader.show();

            expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default"><p>Wait</p></div>');

            $infiniteLoader.hide();

            expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default hidden"><p>Wait</p></div>');
        }));

        it("call to show 2 times and after hide 1 keep the hidden class removed", inject(function ($infiniteLoader) {
            $infiniteLoader.show();
            $infiniteLoader.show();

            expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default"><p>Wait</p></div>');

            $infiniteLoader.hide();

            expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default"><p>Wait</p></div>');
        }));
    });

    describe("configuration of provider $infiniteLoader", function () {
        var $infiniteLoaderProviderMock;
        var $log;
        var $document;
        var body;

        beforeEach(function () {
            module("budgeto.infiniteLoader", function ($infiniteLoaderProvider) {
                $infiniteLoaderProviderMock = $infiniteLoaderProvider;
            });

            $document = angular.element(document);
            module(function ($provide) {
                $provide.value("$document", $document);
            });
            body = $document.find("body").eq(0);

            inject(function (_$log_) {
                $log = _$log_;
            });
        });

        afterEach(function () {
            $document.find("body").html("");
        });

        it("have a valid provider", inject(function () {

            expect($infiniteLoaderProviderMock).not.toBeNull();
        }));

        it("take a config message", inject(function () {
            $infiniteLoaderProviderMock.setMessage("the new wait message");

            var $infiniteLoader = $infiniteLoaderProviderMock.$get[2]($document, $log);
            $infiniteLoader.show();

            expect(body.html()).toContain('<div class="infinite-loader infinite-loader-default"><p>the new wait message</p></div>');
        }));
    });
});