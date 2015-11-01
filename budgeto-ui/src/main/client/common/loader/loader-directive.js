/**
 * Directive to integrate a small loader during call of service or api
 * @returns {{restrict: string, transclude: boolean, template: Function}}
 * @constructor
 */
function LoaderDirective(loaderService) {
    return {
        restrict: 'E',
        transclude: true,
        scope: {
            name: '@'
        },
        controller: ['$scope', function($scope) {
            $scope.isVisible = function() {
                return loaderService.get($scope.name).visible();
            }
        }],
        template: function(element, attrs) {
            return '<div ng-show="isVisible()">' +
                '<img src="' + require('./loader.gif') + '" class="loader" />' +
                '</div>' +
                '<div ng-hide="isVisible()" ng-transclude></div>';
        }
    };
}

export default LoaderDirective;