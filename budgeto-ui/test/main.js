"use strict";

var TEST_REGEXP = /spec\.js$/;
var allTestFiles = [];

Object.keys(window.__karma__.files).forEach(function(file) {
    if (TEST_REGEXP.test(file)) {
        // Normalize paths to RequireJS module names.
        var n = file.replace(/^\/base\/test\//, '../test/').replace(/\.js$/, '');
        allTestFiles.push(n);
    }
});

require.config({
  paths:{
	'angular-mocks': 'lib/angular-mocks/angular-mocks'
  },
  shim:{
    'angular-mocks': {
      deps: ['angular']
    }
  },
  deps: allTestFiles,
    callback: window.__karma__.start,
    baseUrl: '/base/app',
});