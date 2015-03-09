exports.config = {
  allScriptsTimeout: 11000,

  specs: [
    '../e2e-tests/**/*.js'
  ],

  capabilities: {
    'browserName': 'chrome',
    'browserName': 'firefox'
  },

  baseUrl: 'http://localhost:9000/app/',

  framework: 'jasmine',

  jasmineNodeOpts: {
    defaultTimeoutInterval: 30000
  },

  onPrepare: function() {
    var jasmineReporters = require('jasmine-reporters');
    var junitReporter = new jasmineReporters.JUnitXmlReporter({savePath: 'build/test/reports/xml/classes/', consolidateAll: false, filePrefix: 'Test-js-smoke-'});
    jasmine.getEnv().addReporter(junitReporter);

    var karmaCoverage  = require('karma-coverage');
    console.log(karmaCoverage['preprocessor:coverage'][1]({create:function(){}}, "src/main/webapp", ['coverage'], {
      dir: 'target/coverage/',
      reporters: [
        // reporters not supporting the `file` property
        { type: 'html', subdir: 'report-html' },
        { type: 'cobertura', subdir: '.', file: 'cobertura.xml' }
      ]
    }));
  }
};
