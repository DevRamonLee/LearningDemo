import React ,{Component} from 'react';
import {
    View, Text, TextInput, TouchableOpacity, Alert, StatusBar
} from 'react-native';
import MCV from './MCV';
export default class DiaryWriter extends Component {
    constructor(props) {
        super(props);
        this.diaryTitle = null;
        this.diaryBody = null;
        this.moodCode = 0;
        this.state = {
            moodText: '请选择心情'
        };
    }
    
    returnPressed(){
        Alert.alert(
            '请确认',
            '你确认要返回日记列表吗?',
            [
                {text: '确定', onPress: this.props.returnPressed},
                {text: '取消'}
            ]
        );
    }
    selectMood() {
        let tempString;
        if(this.moodCode === 5) this.moodCode = 1;
        else this.moodCode = this.moodCode + 1;
        switch (this.moodCode) {
            case 1:
                tempString = '平静';
                break;
            case 2:
                tempString = '愤怒';
                break;
            case 3:
                tempString = '悲伤';
                break;
            case 4:
                tempString = '高兴';
                break;
            case 5:
                tempString = '痛苦';
                break;
        }
        this.setState(
            ()=>{
                return {
                    moodText: tempString
                };
            });
    }
    render() {
        return (
            <View style={MCV.container}>
                <StatusBar hidden={true}/>
                <View style={MCV.firstRow}>
                    <TouchableOpacity onPress={this.returnPressed.bind(this)}>
                        <Text style={MCV.smallButton}>
                            返回
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity onPress={this.selectMood.bind(this)}>
                        <Text style={MCV.longButton}>
                            {this.state.moodText}
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity onPress={()=>this.props.saveDiary(this.moodCode, this.diaryBody, this.diaryTitle)}>
                        <Text style={MCV.smallButton}>
                            保存
                        </Text>
                    </TouchableOpacity>
                </View>
                <TextInput style={MCV.titleInputStyle}
                    placeholder={'写个日记标题吧'}
                    onChangeText={(text)=>{this.diaryTitle=text}}/>
                <TextInput style={MCV.diaryBodyStyle}
                    multiline={true}
                    placeholder={'日记正文请在此输入'}
                    onChangeText={(text)=>this.diaryBody=text}/>
            </View>
        );
    }
}