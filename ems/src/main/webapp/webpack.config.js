/**
 *  webpack默认webpack.config.js
 *
var path = require('path');
const config = {};
//不同页面需要有不同的入口
config.entry = {
    index: path.resolve(__dirname, 'static/src/index.jsx'),
    main: path.resolve(__dirname, 'static/src/main.jsx')
};
//使用页面名称命名输出文件
config.output = {
    filename: '[name].js',
    path: path.resolve(__dirname, 'static/dist'),
};
config.module = {
    loaders: [
        { test: /\.css$/, loader: 'style-loader!css-loader' },
        { test: /\.less$/,loader: 'style-loader!css-loader!less-loader' },
        { test: /\.(js|jsx)$/, loader: 'babel-loader', exclude: /(node_modules)/,
            query: {
                presets: ['react', 'es2015'],
                plugins: [
                    ['import', [{ libraryName: "antd", style: 'css' }]],
                ],
            }
        }
    ]
};
config.resolve = {
    alias: {
        '@src': path.resolve(__dirname, 'static/src')
    }
}
module.exports = config;
 */



/*
 */
//使用了atool-build,原entry字段放到package.json中去了
const webpack = require('atool-build/lib/webpack');
const path = require('path');
module.exports = function(webpackConfig) {
    //转换es6的新API
    webpackConfig.babel.plugins.push('transform-runtime');

    //按需加载antd
    webpackConfig.babel.plugins.push(['import', {
        libraryName: 'antd',
        // style: 'css'
        style: true//引入antd的less文件
    }]);

    //修改默认输出目录
    webpackConfig.output = {
        filename: '[name].js',
        path: path.resolve(__dirname, 'static/dist'),
    };

    //定义根目录 引用示例:
    //                  import { ConditionContainer } from 'core/component/ConditionContainer.jsx';
    webpackConfig.resolve.root = [
        path.resolve(__dirname, 'static/src')
    ];

    //dev
    webpackConfig.devtool = 'cheap-module-eval-source-map';

    return webpackConfig;
};