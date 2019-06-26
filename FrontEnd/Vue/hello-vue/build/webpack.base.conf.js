'use strict'
const path = require('path')// node 自带文件路径工具
const utils = require('./utils')//工具类
const config = require('../config')
const vueLoaderConfig = require('./vue-loader.conf')//vue-loader.conf配置文件是用来解决各种css文件的，定义了诸如css,less,sass之类的和样式有关的loader

//这个函数用来返回当前目录的平行目录的路径
function resolve (dir) {
  return path.join(__dirname, '..', dir)
}

const createLintingRule = () => ({
  test: /\.(js|vue)$/,
  loader: 'eslint-loader',
  enforce: 'pre',
  include: [resolve('src'), resolve('test')],
  options: {
    formatter: require('eslint-friendly-formatter'),
    emitWarning: !config.dev.showEslintErrorsInOverlay
  }
})

module.exports = {
  context: path.resolve(__dirname, '../'),
  entry: {
    app: './src/main.js'//入口文件
  },
  output: {
    // 路径是config目录下的index.js中的build配置中的assetsRoot，也就是dist目录
    path: config.build.assetsRoot,
    filename: '[name].js',
    // 上线地址，也就是真正的文件引用路径
    publicPath: process.env.NODE_ENV === 'production'
      ? config.build.assetsPublicPath
      : config.dev.assetsPublicPath
  },

  // resolve是webpack的内置选项，顾名思义，决定要做的事情，
  // 也就是说当使用 import "jquery"，该如何去执行这件事情，
  // 就是resolve配置项要做的，import jQuery from "./additional/dist/js/jquery" 这样会很麻烦，可以起个别名简化操作
  resolve: {
    // 省略扩展名，比方说import index form '../js/index', 会默认去找index文件，然后找index.js,.vue,.josn.
    extensions: ['.js', '.vue', '.json'],
    alias: {
      'vue$': 'vue/dist/vue.esm.js',
      // 使用上面的resolve函数，意思是用@代替src的绝对路径
      '@': resolve('src'),
    }
  },
  //不同模块使用不同的 loader
  module: {
    rules: [
      ...(config.dev.useEslint ? [/*createLintingRule()*/] : []),
      {
        // vue 文件，使用 vue-loader 解析
        test: /\.vue$/,
        loader: 'vue-loader',
        options: vueLoaderConfig
      },
      {
        // base-loader 把 es6 解析成 es5
        test: /\.js$/,
        loader: 'babel-loader',
        include: [resolve('src'), resolve('test'), resolve('node_modules/webpack-dev-server/client')]
      },
      {
        // url-loader将文件大小低于下面option中limit的图片，转化为一个64位的DataURL，
        // 这样会省去很多请求，大于limit的，按[name].[hash:7].[ext]的命名方式放到了static/img下面，方便做cache
        test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('img/[name].[hash:7].[ext]')
        }
      },
      {
        // 音频和视频文件处理，同图片
        test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('media/[name].[hash:7].[ext]')
        }
      },
      {
        // 字体处理，同上
        test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('fonts/[name].[hash:7].[ext]')
        }
      }
    ]
  },
  node: {
    // prevent webpack from injecting useless setImmediate polyfill because Vue
    // source contains it (although only uses it if it's native).
    setImmediate: false,
    // prevent webpack from injecting mocks to Node native modules
    // that does not make sense for the client
    dgram: 'empty',
    fs: 'empty',
    net: 'empty',
    tls: 'empty',
    child_process: 'empty'
  }
}
