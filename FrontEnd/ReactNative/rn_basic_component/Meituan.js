/**
 * 仿照美团首页顶部效果，未找到图片，图片是随便找到的。
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View,Image} from 'react-native';

type Props = {};

export default class Meituan extends Component<Props>{
    render() {
        return (
            <View style={styles.container}>
                <View style={styles.first_row}>
                    <View style={styles.item_container}>
                        <Image source={require('./img/me_ico_share.png')} style={styles.icon_image}/>
                        <Text style={styles.icon_text}>分享</Text>
                    </View>
                    <View style={styles.item_container}>
                        <Image source={require('./img/me_ico_account.png')} style={styles.icon_image}/>
                        <Text style={styles.icon_text}>账户</Text>
                    </View>
                    <View style={styles.item_container}>
                        <Image source={require('./img/me_ico_card_center.png')} style={styles.icon_image}/>
                        <Text style={styles.icon_text}>卡券中心</Text>
                    </View>
                    <View style={styles.item_container}>
                        <Image source={require('./img/me_ico_card_pack.png')} style={styles.icon_image}/>
                        <Text style={styles.icon_text}>卡包</Text>
                    </View>
                    <View style={styles.item_container}>
                        <Image source={require('./img/me_ico_feedback.png')} style={styles.icon_image}/>
                        <Text style={styles.icon_text}>我的反馈</Text>
                    </View>
                </View>
                <View style={styles.first_row}>
                    <View style={styles.item_container}>
                        <Image source={require('./img/me_ico_sign_activity.png')} style={styles.icon_image}/>
                        <Text style={styles.icon_text}>发布</Text>
                    </View>
                    <View style={styles.item_container}>
                        <Image source={require('./img/me_ico_my_blueprint.png')} style={styles.icon_image}/>
                        <Text style={styles.icon_text}>图片</Text>
                    </View>
                    <View style={styles.item_container}>
                        <Image source={require('./img/me_ico_partner.png')} style={styles.icon_image}/>
                        <Text style={styles.icon_text}>伙伴</Text>
                    </View>
                    <View style={styles.item_container}>
                        <Image source={require('./img/me_ico_rights.png')} style={styles.icon_image}/>
                        <Text style={styles.icon_text}>特权</Text>
                    </View>
                    <View style={styles.item_container}>
                        <Image source={require('./img/me_ico_service.png')} style={styles.icon_image}/>
                        <Text style={styles.icon_text}>服务</Text>
                    </View>
                </View>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container:{
        marginLeft:5,
        marginTop:10,
        marginRight:5
    },
    first_row:{
        flexDirection:'row'
    },
    item_container:{
        width:70
    },
    icon_image:{
        alignSelf:'center',
        width:45,
        height:45
    },
    icon_text:{
        marginTop:5,
        textAlign:'center',
        fontSize:11,
        color:'#555555'
    }
})