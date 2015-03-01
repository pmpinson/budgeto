"use strict";

// Declare progress module
var budgetoInfiniteLoader = angular.module("budgeto.infiniteLoader", []);

budgetoInfiniteLoader.provider("$infiniteLoader", function () {
    var message = "Wait";

    var $infiniteLoaderProvider = {

        setMessage: function (value) {
            message = value;
        },

        $get: ["$document", "$log", function ($document, $log) {
            $log.debug("budgeto.infiniteLoader : load $infiniteLoader");

            var cpt = 0;
            var $infiniteLoader = {};
            var body = $document.find("body").eq(0);
            var element = angular.element('<div class="infinite-loader infinite-loader-default hidden"><p>' + message + '</p></div>');
            body.append(element);

            $infiniteLoader.config = function () {
                return {
                    getMessage: function () {
                        return message;
                    }
                };
            };

            $infiniteLoader.show = function () {
                cpt++;
                element.removeClass("hidden");
            };

            $infiniteLoader.hide = function () {
                cpt--;
                if (cpt < 0) {
                    cpt = 0;
                }
                if (cpt === 0) {
                    element.addClass("hidden");
                }
            };

            return $infiniteLoader;
        }]
    };

    return $infiniteLoaderProvider;
});