var webpack = require('webpack');
var path = require('path')
var CopyWebpackPlugin = require('copy-webpack-plugin');
;

module.exports = {
    clearBeforeBuild: true,
    entry: './src/js/index.js',
    module: {
        loaders: [
            {
                exclude: /(node_modules)/,
                loader: 'babel-loader',
                query: {
                    presets: ['react', 'es2015', 'stage-0'],
                    plugins: ['react-html-attrs', 'transform-decorators-legacy', 'transform-class-properties'],
                }
            }, {
                test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,
                loader: 'url?limit=10000&mimetype=image/svg+xml'
            }
        ]
    },
    output: {
        path: __dirname + '/dist',
        filename: 'index.min.js'
    },
    plugins: [
        new webpack.optimize.DedupePlugin(),
        new webpack.optimize.OccurenceOrderPlugin(),
        new webpack.optimize.UglifyJsPlugin({sourcemap: false}),
        new CopyWebpackPlugin([
            {from: './src/index.html'},
            {from: './src/bootstrap.min.css'},
            {from: './src/favicon.ico'}
        ])
    ],
};
