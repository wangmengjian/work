const path = require('path');
const config = {
    mode: 'development',
    entry: path.resolve(__dirname, 'src/console.js'),
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'main.js'
    },
    module: {
        rules: [{
            test: /\.js?$/,
            exclude: /node_module/,
            use: {
                loader: 'babel-loader',
                options: {
                    presets: ['env', 'react', 'stage-0'],
                    plugins: ['transform-decorators-legacy', 'transform-class-properties']
                }
            }
        }, {
            test: /\.css$/,
            use: 'style-loader!css-loader'
        }]
    },
    devServer:  {
        contentBase:  './',
        historyApiFallback:  true
    },
    devtool: 'inline-source-map'
};
module.exports = config;