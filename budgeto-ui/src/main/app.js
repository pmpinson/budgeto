/* eslint no-console: 0 */
import path from 'path';
import express from 'express';
import favicon from 'serve-favicon';
import logger from 'morgan';
import cookieParser from 'cookie-parser';
import bodyParser from 'body-parser';

import webpack from 'webpack';
import webpackMiddleware from 'webpack-dev-middleware';
import webpackHotMiddleware from 'webpack-hot-middleware';
import config from './webpack.build';

const isDeveloping = (process.env.NODE_ENV ? process.env.NODE_ENV : 'development') === 'development';
const app = express();

//app.use(express.static(path.join(__dirname, '../dist')));

if (isDeveloping) {
    const compiler = webpack(config);

    app.use(webpackMiddleware(compiler, {
        publicPath: config.output.publicPath,
        contentBase: 'client',
        stats: {
            colors: true,
            hash: false,
            timings: true,
            chunks: false,
            chunkModules: false,
            modules: false
        }
    }));

    app.use(webpackHotMiddleware(compiler));
}

//app.get('*', function response(req, res) {
//    res.sendFile(path.join(__dirname, '../dist/index.html'));
//});

var publicFiles = path.join(__dirname, 'public');

app.use(favicon(path.join(publicFiles, 'favicon.ico')));

// view engine setup
app.set('views', path.join(publicFiles, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

app.use(express.static(publicFiles));

var mockRoutes = require('./routes/mock/apis');
app.use('/mock/', mockRoutes);


/*
 * error handlers
 */
// catch 404 and forward to error handler
app.use(function(req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

if (isDeveloping) {
    // development error handler
    // will print stacktrace
    app.use(function(err, req, res) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
} else {

    // production error handler
    // no stacktraces leaked to user
    app.use(function(err, req, res) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: {}
        });
    });
}

module.exports = app;