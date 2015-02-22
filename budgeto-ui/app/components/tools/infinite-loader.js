'use strict';

// Declare progress module
var budgetoInfiniteLoader= angular.module('budgeto.infiniteLoader', []);

budgetoInfiniteLoader.provider('$infiniteLoader', InfiniteLoaderProvider);

budgetoInfiniteLoader.directive('infiniteloader', [function() {
    return {
        restrict: 'EA',
        scope: {
            message: '=msg'
        },
        template: '<div class="infinite-loader infinite-loader-default hidden"><p>{{message}}</p></div>'
    }

}]);

function InfiniteLoaderProvider() {
    console.info('budgeto.infiniteLoader : load $infiniteLoaderProvider');

    var $infiniteLoaderProvider = {
      $get: [function () {
          console.info('budgeto.infiniteLoader : load $infiniteLoader');

          var cpt = 0;
          var $infiniteLoader = {};

          function getLoader() {
//            $document to use
              var loader = angular.element(document.getElementsByClassName('infinite-loader'));
              if (loader.length === 0) {
                    console.error('have you setup the infiniteLoader directive');
              }
              return loader;
          }

          $infiniteLoader.show = function () {
              cpt++;
              getLoader().removeClass('hidden');
          };

          $infiniteLoader.hide = function () {
              cpt--;
              if (cpt < 0) {
                  cpt = 0;
              }
              if (cpt == 0) {
                  getLoader().addClass('hidden');
              }
          };

          return $infiniteLoader;
        }]
    };

    return $infiniteLoaderProvider;
}