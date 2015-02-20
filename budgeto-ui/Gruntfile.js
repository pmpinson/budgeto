module.exports = function(grunt) {
  require('jit-grunt')(grunt);

  grunt.initConfig({
    less: {
      development: {
        options: {
          compress: false,
          yuicompress: false,
          optimization: 2
        },
        files: {
          "app/styles/styles.css": "app/less/styles.less" // destination file and source file
        }
      }
    },
    watch: {
       less: {
         files: ['app/less/*.less'], // which files to watch
         tasks: ['less'],
         options: {
           nospawn: true
         }
       }
     },
    bgShell: {
        _defaults: {
        },
        server: {
            cmd: 'npm start'
        }
    }
  });


    grunt.loadNpmTasks('grunt-bg-shell');

  grunt.registerTask('compile', ['less']);
  grunt.registerTask('server', ['compile','bgShell:server']);

//  grunt.registerTask('server', 'install the backend and frontend dependencies', startServer);
};