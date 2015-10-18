'use strict';

/**
* bootstrap angular app
*/
require(['angular', './javascripts/budgeto'], function(angular) {
    angular.bootstrap(window.document, ['budgeto']);
});