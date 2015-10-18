'use strict';

var TEST_REGEXP = /spec\.js$/;
var allTestFiles = [];

Object.keys(window.__karma__.files).forEach(function (file) {
    if (TEST_REGEXP.test(file)) {
        // Normalize paths to RequireJS module names.
        var n = file.replace(/^\/base\/test\//, '../src/test/client/').replace(/\.js$/, '');
        allTestFiles.push(n);
    }
});

var tests = [];
for (var file in window.__karma__.files) {
    if (window.__karma__.files.hasOwnProperty(file)) {
        if (/\.spec\.js$/.test(file)) {
            tests.push(file);
        }
    }
}
allTestFiles = tests;

require.config({
    paths: {
        'angular-mocks': 'lib/angular-mocks/angular-mocks'
    },
    shim: {
        'angular-mocks': {
            deps: ['angular']
        }
    },
    deps: allTestFiles,
    callback: window.__karma__.start,
    baseUrl: '/base/src/main/client'
});