'use strict';

/**
* configuration of requirejs
*/
require.config({
  paths:{
    'underscore': 'lib/underscore/underscore-min',
    'angular': 'lib/angular/angular.min',
    'angular-resource': 'lib/angular-resource/angular-resource.min',
    'angular-ui-router': 'lib/angular-ui-router/release/angular-ui-router.min',
    'angular-bootstrap': 'lib/angular-bootstrap/ui-bootstrap-tpls.min',
    'moment': 'lib/moment/min/moment-with-locales.min',
    'moment-timezone': 'lib/moment-timezone/builds/moment-timezone-with-data.min',
    'angular-moment': 'lib/angular-moment/angular-moment.min'
  },
  shim:{
  'angular': {
      exports: 'angular'
  },
    'angular-resource': {
      deps: ['angular']
    },
    'angular-ui-router': {
      deps: ['angular']
    },
    'angular-bootstrap': {
      deps: ['angular']
    },
     'moment-timezone': {
       deps: ['moment']
     },
      'angular-moment': {
        deps: ['angular', 'moment']
      }
  }
});