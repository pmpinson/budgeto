#! /usr/bin/env node
// -*- js -*-

"use strict";

var tools = require("./tools");
var fs = require("fs");
var htmlmin = require('htmlmin');
var yArgs = require("yargs");

var ARGS = yArgs
    .usage("$0 sourcePath1:destPath1 [sourcePath2:destPath2] [options], sourcePath cqn a file or a glob expression, destPath the base of output path where output hierarchy is reproduce after processing")
    .describe("e", "exclude files, can use glob expression")
    .describe("v", "Print version number and exit.")
    .describe("h", "Print help message.")

    .alias("e", "exclude")
    .alias("v", "version")
    .alias("h", "help")

    .string("e")
    .boolean("v")
    .boolean("h")

    .wrap(80)

    .argv;

if (ARGS.noerr) {
    throw "args no valide";
}

if (ARGS.version || ARGS.V) {
//    var json = require("../package.json");
//    console.log(json.name + ' ' + json.version);
    console.log("htmlmin 0.0.1");
    process.exit(0);
}

if (ARGS.h || ARGS.help) {
    console.log(yArgs.help());
    process.exit(0);
}

var directories = ARGS._.slice();

if (directories === undefined || directories.length === 0) {
    console.log("no directories defined");
    process.exit(6);
}

tools.processDirectories(directories, ARGS.exclude, function(file) {
    return htmlmin(fs.readFileSync(file, {encoding:"UTF-8"}));
});