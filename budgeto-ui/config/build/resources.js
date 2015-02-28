var fs = require("fs");
var htmlmin = require('htmlmin');
var mkdirp = require('mkdirp');

var path = "app";
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
        fs.readFile(path + "/" + file, function (err, data) {
          if (err) {throw err;}
          fs.writeFile(output + "/" + file, data, function (err) {if (err) {throw err;}});
        });
    }
}

function includeFile(path, file) {
    var suffix = ".gif";
    if (file.indexOf(suffix, file.length - suffix.length) === -1) {
        return false;
    }
    return true;
}