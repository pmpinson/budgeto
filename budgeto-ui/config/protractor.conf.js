exports.config = {
  allScriptsTimeout: 11000,

  specs: [
    '../e2e-tests/**/*.js'
  ],

  capabilities: {
    'browserName': 'firefox'
  },

  baseUrl: 'http://localhost:9000/app/',

  framework: 'jasmine2',

  jasmineNodeOpts: {
    defaultTimeoutInterval: 30000
  },

  onPrepare: function() {
    var jasmineReporters = require('jasmine-reporters');
    var junitReporter = new jasmineReporters.JUnitXmlReporter({savePath: 'target/e2e-test-results/', consolidateAll: false});
    jasmine.getEnv().addReporter(junitReporter);

//    var karmaCoverage  = require('karma-coverage');
//    console.log(karmaCoverage['preprocessor:coverage'][1]({create:function(){}}, "src/main/webapp", ['coverage'], {
//      dir: 'target/coverage/',
//      reporters: [
//        // reporters not supporting the `file` property
//        { type: 'html', subdir: 'report-html' },
//        { type: 'cobertura', subdir: '.', file: 'cobertura.xml' }
//      ]
//    }));
  }
};
