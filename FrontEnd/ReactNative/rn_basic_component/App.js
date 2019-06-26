/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View,Image,ScrollView} from 'react-native';

type Props = {};

//导入 nativeImageSource函数
let nativeImageSource = require('nativeImageSource');

export default class App extends Component<Props> {
  render() {
    // 加载 native 资源文件
    let ades = {
      android:'mipmap/ic_launcher',
      width:72,
      height:72
    };
    return (
      <ScrollView style={styles.scroll_view}>
        <View style={styles.container}>
          <View style={styles.first_view}>
            <View style={styles.second_view}></View>
            <View style={styles.third_view}></View>
          </View>
          <View>
            <Text>'测试本地图片'</Text>
            <Image  style={styles.image_view} source={require('./img/scene.jpg')}/>
          </View>
          <View>
            <Text>'测试加载 app drawable 中的图片'</Text>
            <Image style={styles.icon} source={{uri:'ic_launcher'}}/>
          </View>
          <View>
            <Text>'测试加载 app mipmap 中的图片'</Text>
            <Image style={styles.image_view} source={nativeImageSource(ades)}/>
          </View>
          <View>
            <Text>'测试加载网络图片'</Text>
            <Image style={styles.image_net} source={{uri:'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=998091442,994746716&fm=26&gp=0.jpg'}}/>
          </View>
        </View>
      </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container:{
    flexDirection:'column',
    flex:1
  },
  first_view:{
    flexDirection:'row',
    height:100,
    padding:20,
    backgroundColor:'gray'
  },
  second_view:{
    backgroundColor:'red',
    flex:2
  },
  third_view:{
    backgroundColor:'green',
    flex:1
  },
  image_view:{
    marginLeft:10,
    marginTop:10
  },
  icon:{
    width:120,
    height:120
  },
  image_net:{
    width:300,
    height:200
  },
  scroll_view:{
    paddingVertical: 20
  }
});
