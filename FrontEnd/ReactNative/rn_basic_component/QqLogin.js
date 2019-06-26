/**
 * 仿照 QQ 登录的效果
 * 使用组件：TextInput
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View,Image,ScrollView} from 'react-native';

type Props = {};

//导入 nativeImageSource函数
let nativeImageSource = require('nativeImageSource');

export default class QqLogin extends Component<Props> {
  render() {
      return(
        <View style={{backgroundColor:'#f4f4f4', flex:1}}>
            <Image style={styles.style_image} source={require('./img/qq.jpg')}/>
        </View>
      );
  }
}

const styles = StyleSheet.create({
    style_image:{
        borderRadius:35,
        height:70,
        width:70,
        marginTop:40,
        alignSelf:'center'
    }
}
)