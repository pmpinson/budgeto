import path from 'path';
import webpack from 'webpack';
import HtmlWebpackPlugin from 'html-webpack-plugin';
import angularApp from './client/main-webpack';

export default {
    devtool: 'eval-source-map',
    entry: [
        'client/main-webpack.js'
    ],
    output: {
        path: path.join(__dirname, '/dist/'),
        filename: '[name].js',
        publicPath: '/'
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: path.join(__dirname, 'client/index.html'),
            inject: 'body',
            filename: 'index.html'
        }),
        //new webpack.optimize.OccurenceOrderPlugin(),
        new webpack.HotModuleReplacementPlugin(),
        //new webpack.NoErrorsPlugin(),
        new webpack.DefinePlugin({
            'process.env.NODE_ENV': JSON.stringify('development')
        })
    ],
    module: {
        loaders: [{
            test: /\.js?$/,
            exclude: /node_modules/,
            loader: 'babel'
        }
            //    , {
            //    test: /\.json?$/,
            //    loader: 'json'
            //}, {
            //    test: /\.css$/,
            //    loader: 'style!css?modules&localIdentName=[name]---[local]---[hash:base64:5]'
            //}
        ]
    }
};