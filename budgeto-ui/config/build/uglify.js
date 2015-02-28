var fs = require("fs");
var UglifyJS = require("uglify-js");
var mkdirp = require('mkdirp');

var path = "app";
var pathLibJs = "app/scripts/build.js";
var output = "target/dist/app";

uglifyPath(path, output);

function uglifyPath(path, output) {
    fs.readdir(path, function(arg1, files){
        files.forEach(function(file) {
            if (fs.lstatSync(path + "/" + file).isDirectory()) {
                uglifyPath(path + "/" + file, output + "/" + file);
            } else {
                uglifyFile(path, output, file);
            }
        });
    });
}

function uglifyFile(path, output, file) {
    if (includeFile(path, file)) {
        console.info("process file ", path + "/" + file, " to ", output + "/" + file);
        mkdirp(output, function (err) {if (err) {console.error(err);process.exit(5);}});
        fs.writeFile(output + "/" + file, UglifyJS.minify(path + "/" + file).code, function (err) {if (err) {console.error(err);process.exit(5);}});
    }
}

function includeFile(path, file) {
    if ((path + "/" + file) === pathLibJs) {
        return false;
    }
    var suffix = ".js";
    if (file.indexOf(suffix, file.length - suffix.length) === -1) {
        return false;
    }
    return true;
}