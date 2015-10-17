'use strict';

/**
* bootstrap angular app
*/
require(['../../target/dist/app/lib/angular/angular.min', 'budgeto'], function(angular) {
    angular.bootstrap(window.document, ['budgeto']);
});