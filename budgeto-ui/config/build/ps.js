#! /usr/bin/env node
// -*- js -*-

"use strict";

var ps = require('ps-node');
var ChildProcess = require('child_process');

// A simple pid lookup
ps.lookup({
//    pid: '8396'
//    command: 'node',
    arguments: 'build:serve'
    }, function(err, resultList ) {
    if (err) {
        throw new Error( err );
    }

    resultList.forEach(function( process ){
        if( process ){
            console.log( 'PID: %s, COMMAND: %s, ARGUMENTS: %s', process.pid, process.command, process.arguments );

            var killCommand = 'taskkill ';
            var command = killCommand + '/F /T /PID ' + process.pid;
            ChildProcess.exec( command, function( err, stdout, stderr) {
                if (err || stderr) {
                    throw new Error( err );
                }
                else {
                    stdout = stdout.toString();

                    // 在windows下，kill完马上查询会出现还能找到刚刚被kill的进程的情况，因此等待200ms，然后再认为kill结束

                        setTimeout(function(){
                    console.log( 'Process %s has been killed!', process.pid );
                        }, 200 );

                }
            });

//            // A simple pid lookup
//            ps.kill( process.pid, function( err ) {
//                if (err) {
//                    throw new Error( err );
//                }
//                else {
//                    console.log( 'Process %s has been killed!', process.pid );
//                }
//            });
        }
    });
});