var path = require('path');
var webpack = require('webpack');
var CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
    devtool: '#eval-cheap-module-source-map',
    entry: {
        bundle: [
            './src/js/index',
            './src/html/index'
        ],
        vendor: [
            'babel-core/polyfill',
            'font-awesome/css/font-awesome'
        ]
    },
    output: {
        path: path.join(__dirname, '../src/main/resources/assets'),
        filename: '[name].js',
        publicPath: '/',
        pathinfo: true
    },
    plugins: [
        new CopyWebpackPlugin([
            {from: './src/bootstrap.min.css'},
            {from: './src/favicon.ico'}
        ]),
        new webpack.optimize.CommonsChunkPlugin({
            name: 'vendor',
            filename: 'vendor.js'
        })
    ],
    resolve: {
        alias: {
            moment: 'moment/min/moment.min.js',
            'babel-core/polyfill': 'babel-core/browser-polyfill.min.js',
            handlebars: 'handlebars/dist/handlebars.min.js',
            'js-logging': 'js-logging/js-logging.browser.min.js',
            'vis.css': 'vis/dist/vis.min.css',
            'vis.js': 'vis/dist/vis.min.js',
        },
        extensions: ['', '.js', '.scss', '.css', '.html', '.json']
    },
    module: {
        noParse: [
            path.resolve('node_modules', 'moment/min/moment.min.js'),
            path.resolve('node_modules', 'babel-core/browser-polyfill.min.js'),
            path.resolve('node_modules', 'handlebars/dist/handlebars.min.js'),
            path.resolve('node_modules', 'js-logging/js-logging.browser.min.js'),
            path.resolve('node_modules', 'vis/dist/vis.min.js'),
            path.resolve('node_modules', 'vis/dist/vis.min.css'),
        ],
        loaders: [{
            test: /\.js$/,
            loaders: ['react-hot', 'babel'],
            exclude: /node_modules/,
            include: path.join(__dirname, 'src', 'js')
        }, {
            test: /\.json$/,
            loader: 'json'
        }, {
            test: /\.css$/,
            loader: 'style!css?sourceMaps',
            include: __dirname
        }, {
            test: /\.woff(\?v=\d+\.\d+\.\d+)?$/,
            loader: 'url?limit=10000&mimetype=application/font-woff'
        }, {
            test: /\.woff2(\?v=\d+\.\d+\.\d+)?$/,
            loader: 'url?limit=10000&mimetype=application/font-woff'
        }, {
            test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/,
            loader: 'url?limit=10000&mimetype=application/octet-stream'
        }, {
            test: /\.eot(\?v=\d+\.\d+\.\d+)?$/,
            loader: 'file'
        }, {
            test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,
            loader: 'url?limit=10000&mimetype=image/svg+xml'
        }, {
            test: /\.scss$/,
            loader: 'style!css!sass'
        }, {
            test: /\.html$/,
            loader: 'file?name=[name].[ext]'
        }, {
            test: /\.(png|jpg|jpeg|gif)$/,
            loader: 'url-loader?limit=8192'
        }, {
            test: /\.js$/,
            loader: 'eslint-loader',
            exclude: /node_modules|xtext|polyfills/
        }, {
            test: /\.js$/,
            loader: 'jscs-loader',
            exclude: /node_modules|xtext|polyfills/
        }]
    },
    node: {
        fs: "empty"
    }
};
