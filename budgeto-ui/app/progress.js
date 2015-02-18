'use strict';

// Declare progress module
var budgetoProgress = angular.module('budgeto.progress', []);

budgetoProgress.factory('ProgressLoader', [ProgressLoader]);

/**
 * progress factory
 */
function ProgressLoader() {
    console.info('budgeto.progress : load ProgressLoader');

    var loader = angular.element(document.getElementsByTagName("body"));

    var cpt = 0;

    return {
        show: function () {
            cpt++;
            loader.addClass("progressdialog-loader");
        },

        hide: function () {
            cpt--;
            if (cpt < 0) {
                cpt = 0;
            }
            if (cpt == 0) {
                loader.removeClass("progressdialog-loader");
            }
        }
    };
}