var fs = require("fs");
var UglifyJS = require("uglify-js");
var mkdirp = require('mkdirp');

var path = "app";
var pathLibJs = "app/scripts/build.js";
var output = "target/dist/app";

processDir(path, output);

function processDir(path, output) {
    fs.readdir(path, function(arg1, files){
        files.forEach(function(file) {
            if (fs.lstatSync(path + "/" + file).isDirectory()) {
                processDir(path + "/" + file, output + "/" + file);
            } else {
                processFile(path, output, file);
            }
        });
    });
}

function processFile(path, output, file) {
    if (includeFile(path, file)) {
        mkdirp(output, function (err) {if (err) {throw err;}});
        fs.writeFile(output + "/" + file, UglifyJS.minify(path + "/" + file).code, function (err) {if (err) {throw err;}});
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