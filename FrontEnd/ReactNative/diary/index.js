/** @format */
import React ,{Component} from 'react';
import {AppRegistry} from 'react-native';
import {name as appName} from './app.json';
import DiaryList from './DiaryList';
import DiaryReader from './DiaryReader';
import DiaryWriter from './DiaryWriter';
import DataHandler from './DataHandler';

export default class Diary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            uiCode: 1,
            diaryList: [],
            diaryMood: null,
            diaryTime: '读取中...',
            diaryTitle:'读取中...',
            diaryBody:'读取中'
        };
        //执行回调函数绑定
        this.bindAllMyFunction();
        //获取所有的日记数据，数据保存在 DataHandler 中
        DataHandler.getAllTheDiary().then(
            (result)=>{
                this.setState({diaryList: result});
            }
        ).catch (
            (error)=>{
                console.log(error);
            }
        )
    }
    //将所有的绑定回调函数放这里，让构造方法易阅读
    bindAllMyFunction() {
        this.selectListisItem = this.selectListisItem.bind(this);
        this.writeDiary = this.writeDiary.bind(this);
        this.returnPressed = this.returnPressed.bind(this);
        this.saveDiaryAndReturn = this.saveDiaryAndReturn.bind(this);
        this.readingPreviousPressed = this.readingPreviousPressed.bind(this);
        this.readingNextPressed = this.readingNextPressed.bind(this);
    }
    //阅读日记界面请求读上一篇日记的处理函数
    readingPreviousPressed() {
        let previousDiary = DataHandler.getPreviousDiary();
        if(previousDiary === null) return; //已经是第一篇日记
        this.setState(previousDiary);//显示上一篇日记
    }
    //阅读日记界面请求读下一篇日记的处理函数
    readingNextPressed() { 
        let nextDiary = DataHandler.getNextDiary();
        if(nextDiary === null) return; //已经到最后一篇日记
        this.setState(nextDiary);//显示下一篇日记
    }
    //阅读日记界面、写日记界面返回日记列表的处理函数
    returnPressed() {
        this.setState({uiCode: 1});
    }
    //写日记界面保存日记并返回日记列表界面的处理函数
    saveDiaryAndReturn(newDiaryMood, newDiaryBody, newDiaryTitle) {
        DataHandler.saveDiary(newDiaryMood, newDiaryBody, newDiaryTitle).then (
            (result)=>{
                this.setState(result);
            }
        ).catch(
            (error)=>{
                console.log(error);
            }
        );
    }
    //写日记按钮被按下时的处理函数
    writeDiary() {
        this.setState({uiCode: 3});
    }
    //搜索框中有输入时的处理函数
    searchKeyword(keyword) {
        console.log('search keyword is: ' + keyword);
    }
    //日记列表中某条记录被选中时的处理函数
    selectListisItem(aIndex) {
        //现在selectListItem 可以知道按下了日记的第几行
        let rValue = DataHandler.getDiaryAtIndex(aIndex);
        this.setState(rValue);
    }
    
    showDiaryList() {
        return (
            <DiaryList selectListisItem={this.selectListisItem}
                searchKeyword={this.searchKeyword}
                diaryList={this.state.diaryList}
                writeDiary={this.writeDiary}/>
        );
    }
    showDiaryReader() {
        return (
            <DiaryReader returnToDiaryList={this.returnPressed}
                diaryTitle={this.state.diaryTitle}
                diaryMood={this.state.diaryMood}
                diaryTime={this.state.diaryTime}
                readingPreviousPressed={this.readingPreviousPressed}
                returnPressed={this.returnPressed}
                readingNextPressed={this.readingNextPressed}
                diaryBody={this.state.diaryBody}/>
        );
    }
    showDiaryWriter() {
        //注意，如何将上层组件的某些函数作为回调函数利用属性向下层传递
        return (
            <DiaryWriter returnPressed={this.returnPressed}
                saveDiary={this.saveDiaryAndReturn}/>
        );
    }
    render() {
       
        if(this.state.uiCode === 1) return this.showDiaryList();
        if(this.state.uiCode === 2) return this.showDiaryReader();
        if(this.state.uiCode === 3) return this.showDiaryWriter();
    }
}
AppRegistry.registerComponent(appName, () => Diary);
