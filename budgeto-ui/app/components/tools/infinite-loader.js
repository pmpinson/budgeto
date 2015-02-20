'use strict';

// Declare progress module
var budgetoInfiniteLoader= angular.module('budgeto.infiniteLoader', []);

budgetoInfiniteLoader.factory('InfiniteLoader', [InfiniteLoader]);

//budgetoInfiniteLoader.directive('infinite-loader', [InfiniteLoaderDirective]);

budgetoInfiniteLoader.directive('infinite-loader', function(){
    return {
        restrict: 'AEC',
        scope: {
            msg: '='
            //'loader-class': '@'
        },
        template: '<div class="infinite-loader infinite-loader-default {{loaderClass}}"><p>{{msg}}</p></div>'
    }

});
/**
 * infinite loader factory
 * keep progress open if for exemple 2 call of show and 1 call hide seems there is another call to hide
 * @returns {{show: Function to show the infinite loader, hide: Function to hide the infinite loader}}
 * @constructor
 */
function InfiniteLoader() {
    console.info('budgeto.progress : load InfiniteLoader');

    var loader = angular.element(document.getElementsByClassName('infinite-loader'));
    loader.addClass('hidden');

    var cpt = 0;

    return {
        show: function () {
            cpt++;
            loader.removeClass('hidden');
        },

        hide: function () {
            cpt--;
            if (cpt < 0) {
                cpt = 0;
            }
            if (cpt == 0) {
                loader.addClass('hidden');
            }
        }
    };
}