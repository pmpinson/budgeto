module.exports = function(grunt) {
  require('jit-grunt')(grunt);

  grunt.initConfig({
    less: {
      development: {
        options: {
          compress: true,
          yuicompress: true,
          optimization: 2
        },
        files: {
          "app/styles/styles.css": "app/less/styles.less" // destination file and source file
        }
      }
    },
      bower: {
        install: {
            options: {
                bowerOptions: {
                    production: true
                }
            }
        }
      },
      bower_concat:{
          all: {
              dest: 'lib/libs.js',
              cssDest: 'lib/libs.css',
              bowerOptions: {
                relative: true
              }
            }
      },
      uglify: {
          js: {
            files: {
              'app/js/libs.js': ['lib/libs.js']
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
    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-bower-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');

  grunt.registerTask('compile', ['less', 'bower', 'bower_concat', 'uglify']);
  grunt.registerTask('server', ['compile','bgShell:server']);

//  grunt.registerTask('server', 'install the backend and frontend dependencies', startServer);
};