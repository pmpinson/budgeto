/**
 * Directive to integrate a small loader during call of service or api
 * @returns {{restrict: string, transclude: boolean, template: Function}}
 * @constructor
 */
function LoaderDirective() {
    return {
        restrict: 'A',
        transclude: true,
        template: function(element, attrs) {
            return '<div ng-show="getLoader(\'' + attrs.loader + '\').visible()">' +
                '<img src="/images/loader.gif" class="center-block" width="40px" height="40px" />' +
                '</div>' +
                '<div ng-hide="getLoader(\'' + attrs.loader + '\').visible()" ng-transclude></div>';
        }
    };
}

export default LoaderDirective;