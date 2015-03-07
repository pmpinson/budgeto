// Karma configuration
// Generated on Sat Feb 21 2015 12:39:02 GMT-0500 (Est)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '../',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    // 'bower_components/angular-mocks/angular-mocks.js',
    files: [
      'app/scripts/build.js',
      'app/app.js',
      'app/components/**/*.js',
      'test/app.spec.js',
      'test/components/**/*.spec.js'
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
          'app/app.js': ['coverage'],
          'app/components/**/*.js': ['coverage']
    },


    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress', 'html', 'junit', 'coverage'],

    junitReporter: {
      outputFile: 'target/test-results.xml'
    },

    htmlReporter: {
      outputDir: 'target/test-results',
      focusOnFailures: true
    },

    coverageReporter: {
      type : 'html',
      dir : 'target/coverage/'
    },


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome', 'Firefox', 'PhantomJS'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false
  });
};
