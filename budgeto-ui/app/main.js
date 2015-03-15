"use strict";

require.config({
  paths:{
    'underscore': 'bower_components/underscore/underscore-min',
    'angular': 'bower_components/angular/angular.min',
    'angular-resource': 'bower_components/angular-resource/angular-resource.min',
    'angular-ui-router': 'bower_components/angular-ui-router/release/angular-ui-router.min',
    'angular-bootstrap': 'bower_components/angular-bootstrap/ui-bootstrap-tpls.min',
    'moment': 'bower_components/moment/min/moment-with-locales.min',
    'moment-timezone': 'bower_components/moment-timezone/builds/moment-timezone-with-data.min',
    'angular-moment': 'bower_components/angular-moment/angular-moment.min'
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