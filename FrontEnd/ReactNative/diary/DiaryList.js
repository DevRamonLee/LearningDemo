import React ,{Component} from 'react';
import {
    View, Text, TextInput, TouchableOpacity, Image, StatusBar, ListView
} from 'react-native';
let laughMood = require('./image/laugh.png');
import MCV from './MCV';
export default class DiaryList extends Component {
    constructor(props) {
        super(props);
        this.updateSearchKeyword = this.updateSearchKeyword.bind(this);
        this.state={
            //ListView 数据源
            diaryListDataSource: new ListView.DataSource({
                rowHasChanged: (oldRow, newRow)=> oldRow !== newRow
            })
        }
        //渲染ListView的每一行
        this.renderListItem = this.renderListItem.bind(this);
    }
    //将数据源的数据复制到数据列表中
    componentWillMount() {
        if(this.props.diaryList === null) return;
        this.setState({
            diaryListDataSource: this.state.diaryListDataSource.cloneWithRows(this.props.diaryList)
        });
    }
    //这个函数很重要，当有数据更新时，这里会更新列表
    componentWillReceiveProps(nextProps) {
        this.setState({
            //注意这里使用的参数nextProps.diaryList 而不是 this.props.diaryList
            diaryListDataSource: this.state.diaryListDataSource.cloneWithRows(nextProps.diaryList)
        });
    }
    //renderListItem 函数定义了如何渲染表中的每一行，三个参数分别代表 数据、分段号、行号
    renderListItem(log, sectionID, rowID) {
        return (
            <TouchableOpacity onPress={()=>this.props.selectListisItem(rowID)}>
                {/*使用 TouchableOpacity 将列表中的每一行声明为可按的控件
                并且指定按下事件的处理函数，按下事件上报时会带上本行的行号 */}
                <View style={MCV.secondRow}>
                    <Image style={MCV.moodStyle}
                        source={log.mood}/>
                    <View style={MCV.subViewInReader}>
                        <Text style={MCV.textInReader}>
                            {log.title}
                        </Text>
                        <Text style={MCV.textInReader}>
                            {log.time}
                        </Text>
                    </View>
                </View>
            </TouchableOpacity>
        );

    }
    updateSearchKeyword(newText) {
        //根据搜索关键词更新列表
        this.props.searchKeyword(newText);
        //将用户输入的搜索关键字交给上层组件，由上层组件对日记列表进行处理，只显示标题中包含关键字的日记
    }
    render() {
        return (
            <View style={MCV.container}>
                <StatusBar hidden={true}/>
                <View style={MCV.firstRow}>
                    <View style={{borderWidth:1}}>
                        <TextInput autoCapitalize="none"
                        placeholder='请输入搜索关键字'
                        clearButtonMode="while-editing"
                        onChangeText={this.updateSearchKeyword}
                        style={MCV.searchBarTextInput}/>
                    </View>
                    <TouchableOpacity onPress={this.props.writeDiary}>
                        <Text style={MCV.middleButton}>
                            写日记
                        </Text>
                    </TouchableOpacity>
                </View>
                {/*下面采用 ListView组件来显示数据， dataSource 描述列表的数据，renderRow描述如何渲染每一行数据 */}
                {
                    (
                        (this.props.diaryList.length !== 0) ?
                        (
                            <ListView dataSource={this.state.diaryListDataSource}
                                renderRow={this.renderListItem}>
                            </ListView>
                        ):
                        (
                            <View style={{flex:1, justifyContent:'center'}}>
                                <Text style={{fontSize: 18}}>你还没有写日记</Text>
                            </View>
                        )
                    )
                }
            </View>
        );
    }
}