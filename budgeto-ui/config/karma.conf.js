// Karma configuration
// Generated on Sat Feb 21 2015 12:39:02 GMT-0500 (Est)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '../',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine', 'requirejs'],


    // list of files / patterns to load in the browser
    files: [
        {pattern: 'src/main/client/lib/**/*.js', included: false},
        {pattern: 'src/main/client/javascripts/**/*.js', included: false},
        {pattern: 'src/test/client/**/*.spec.js', included: false},
        'src/main/client/javascripts/main.js',
        'src/test/client/main.js'
    ],


    // list of files to exclude
    exclude: [
        'src/main/client/javascripts/launch.js'
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
          'src/main/client/javascripts/**/*.js': ['coverage']
    },


    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress', 'html', 'junit', 'coverage'],

    junitReporter: {
      outputDir: 'target/test-results'
    },

    htmlReporter: {
      outputDir: 'target/test-results/html',
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
    browsers: ['Firefox'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false
  });
};
