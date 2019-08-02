import {AsyncStorage} from 'react-native';
let cryMood = require('./image/cry.png');
let laughMood = require('./image/laugh.png');
let proundMood = require('./image/proud.png');
let sadMood = require('./image/sad.png');
let shyMood = require('./image/shy.png');
export default class DataHandler {
    static realDiaryList = [];
    static listIndex = 0;
    static getAllTheDiary() {
        return new Promise(function(resolve, reject) {
                AsyncStorage.getAllKeys().then(
                    (Keys)=>{
                        if(Keys.length === 0) {
                            resolve(DataHandler.realDiaryList);
                            console.log('注意，resolve 后的语句还会被执行，因此 resolve 后如果有代码，结束处理必须要跟return语句');
                            return;
                        }
                        AsyncStorage.multiGet(Keys).then(
                            (results)=>{
                                let resultsLength = results.length;
                                for(let counter = 0; counter < resultsLength; counter++) {
                                    //取得数据并利用JSON类的parse方法生成对象，插入日记列表
                                    DataHandler.realDiaryList[counter] = JSON.parse(results[counter][1]);
                                    switch(DataHandler.realDiaryList[counter].mood) {
                                        case 2:
                                            DataHandler.realDiaryList[counter].mood = cryMood;
                                            break;
                                        case 3:
                                            DataHandler.realDiaryList[counter].mood = laughMood;
                                            break;
                                        case 4:
                                            DataHandler.realDiaryList[counter].mood = sadMood;
                                            break;
                                        case 5:
                                            DataHandler.realDiaryList[counter].mood = proundMood;
                                            break;
                                        default:
                                            DataHandler.realDiaryList[counter].mood = shyMood;
                                    }
                                    let aTime = new Date(DataHandler.realDiaryList[resultsLength].time);
                                    DataHandler.realDiaryList[counter].time = '' + aTime.getFullYear() + '年' + 
                                    (aTime.getMonth() + 1) + '月' + aTime.getDate() + '日 星期' + (aTime.getDay() + 1) +
                                    '  ' + aTime.getHours() + ':' + aTime.getMinutes();
                                }
                                DataHandler.bubleSortDiaryList();
                                console.log('load completely');
                                resolve(DataHandler.realDiaryList);
                                
                            }
                        ).catch(
                            (error)=>{
                                console.log('error' + error);
                            }
                        )
                    }
                ).catch(
                    (error)=>{
                        console.log('A error happens while read all the diary.');
                        console.log(error);
                        AsyncStorage.clear();
                        resolve(DataHandler.realDiaryList);
                    }
                )
            }
        );
    }
    //因为 AsyncStorage API 不能保证读取的顺序，使用冒泡排序对日记进行排序
    static bubleSortDiaryList() {
        let tempObj;
        for( let i = 0; i < DataHandler.realDiaryList.length; i++) {
            for(let j = 0; j <DataHandler.realDiaryList.length - i -1; j++) {
                if(DataHandler.realDiaryList[j].index > DataHandler.realDiaryList[j+1].index) {
                    tempObj = DataHandler.realDiaryList[j];
                    DataHandler.realDiaryList[j] = DataHandler.realDiaryList[j+1];
                    DataHandler.realDiaryList[j +1] = tempObj;
                }
            }
        }
    }
    //请求上一篇日记的处理
    static getPreviousDiary() {
        if(DataHandler.listIndex === 0) return null;//已经是第一篇日记
        DataHandler.listIndex--;
        return {
            uiCode:2,
            diaryTime: DataHandler.realDiaryList[DataHandler.listIndex].time,
            diaryTitle: DataHandler.realDiaryList[DataHandler.listIndex].title,
            diaryMood: DataHandler.realDiaryList[DataHandler.listIndex].mood,
            diaryBody: DataHandler.realDiaryList[DataHandler.listIndex].body,
        }
    }
    //获取第 index 篇日记
    static getDiaryAtIndex(aIndex) {
        DataHandler.listIndex = aIndex;
        return {
            uiCode:2,
            diaryTime: DataHandler.realDiaryList[DataHandler.listIndex].time,
            diaryTitle: DataHandler.realDiaryList[DataHandler.listIndex].title,
            diaryMood: DataHandler.realDiaryList[DataHandler.listIndex].mood,
            diaryBody: DataHandler.realDiaryList[DataHandler.listIndex].body,
        }
    }

    //获取下一篇日记的数据
    static getNextDiary() {
        if(DataHandler.listIndex === (DataHandler.realDiaryList.length - 1)) return null;//已经是最后一篇
        DataHandler.listIndex++;
        return {
            uiCode:2,
            diaryTime: DataHandler.realDiaryList[DataHandler.listIndex].time,
            diaryTitle: DataHandler.realDiaryList[DataHandler.listIndex].title,
            diaryMood: DataHandler.realDiaryList[DataHandler.listIndex].mood,
            diaryBody: DataHandler.realDiaryList[DataHandler.listIndex].body,
        }
    }
    //保存日记数据
    static saveDiary(newDiaryMood, newDiaryBody, newDiaryTitle) {
        return new Promise(function(resolve, reject) {
                let currentTime = new Date();//获取当前时间
                let timeString = '' + currentTime.getFullYear() + '年' + 
        (currentTime.getMonth() + 1) + '月' + currentTime.getDate() + '日 星期' + (currentTime.getDay() + 1) +
        '  ' + currentTime.getHours() + ':' + currentTime.getMinutes();
                let aDiary = Object();
                aDiary.title = newDiaryTitle;
                aDiary.body = newDiaryBody;
                aDiary.mood = newDiaryMood;
                aDiary.time = currentTime;
                aDiary.sectionID = '' + currentTime.getFullYear() + ' 年 ' + 
                    (currentTime.getMonth() + 1) + '月';
                //sectionID 用来对日记进行分段显示，后面用到
                aDiary.index = Date.parse(currentTime);//从当前时间生成唯一值，用来索引日记列表，精确到毫秒，可以认为是唯一的
                AsyncStorage.setItem(''+ aDiary.index, JSON.stringify(aDiary)).then(
                    ()=> {
                        let totalLength = DataHandler.realDiaryList.length;
                        aDiary.time = timeString;
                        DataHandler.realDiaryList[totalLength] = aDiary;
                        let newMoodIcon;
                        //准备心情图片
                        switch (newDiaryMood) {
                            case 2:
                                newMoodIcon = cryMood;
                                break;
                            case 3:
                                newMoodIcon = laughMood;
                                break;
                            case 4:
                                newMoodIcon = sadMood;
                                break;
                            case 5:
                                newMoodIcon = proundMood;
                                break;
                            default:
                                newMoodIcon = shyMood;
                        }
                        DataHandler.realDiaryList[totalLength].mood = newMoodIcon;
                        let rValue = {
                            diaryList: DataHandler.realDiaryList,
                            uiCode:1
                        }
                        resolve(rValue); // 返回最新写日记数据
                    }
                ).catch(
                    (error) => {
                        console.log('Saving failed, error ' + error.message);
                    }
                );
            });
    }
}