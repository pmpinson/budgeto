#! /usr/bin/env node
// -*- js -*-

"use strict";

var fs = require("fs");
var mkDirP = require('mkdirp');
var glob = require("glob");
var path = require('path');

module.exports.processDirectories = function (directories, excludedPath, callback) {
    directories.forEach(function(directory) {
        var idx = directory.indexOf(":");
        if (idx === -1) {
            console.log(directory, " bad syntax use sourcePath:destPath");
            process.exit(6);
            return;
        }
        var source = directory.substr(0, idx);
        var destination = directory.substr(idx + 1);

        if (!module.exports.controlOutputDirectory(destination)) {
            console.log(directory, "destination '", destination, "' not exist, or not directory or not have write access'");
            process.exit(6);
            return;
        }

        if (excludedPath !== undefined && excludedPath !== "") {
            glob(excludedPath, {}, function (err, excludedFiles) {
                module.exports.processDirectory(source, destination, excludedFiles, callback);
            });
        } else {
            module.exports.processDirectory(source, destination, [], callback);
        }
    });
};

module.exports.processDirectory = function (source, destination, excludedFiles, callback) {
    glob(source, {}, function (err, files) {
        files.forEach(function(file) {
            module.exports.processFile(file, destination, excludedFiles, callback);
        });
    });
}

module.exports.controlOutputDirectory = function(path) {
    try {
        return fs.lstatSync(path).isDirectory();
    } catch(err) {
        return false;
    }
}

module.exports.processFile = function(file, output, excludedFiles, callback) {
    if (module.exports.includeFile(file, excludedFiles)) {
        mkDirP(path.dirname(path.join(output, file)), function (err) {
            if (err) {
                throw err;
             } else {
                fs.writeFile(output + "/" + file, callback(file), function (err) {
                    if (err) {
                        throw err;
                    }
                });
             }
        });
    }
}

module.exports.includeFile = function(file, excludedFiles) {
    for (var key in excludedFiles) {
        var excludedFile = excludedFiles[key];
        if (file === excludedFile) {
            return false;
        }
    };
    return true;
}