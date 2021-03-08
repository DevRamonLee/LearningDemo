import 'package:flutter/material.dart';
import 'package:io_db_net/io_screen.dart';
import 'package:io_db_net/sqlite_screen.dart';
import 'package:io_db_net/echo/client/message_list.dart';
import 'package:io_db_net/net/list_page.dart';

const String DEMO_ONE = '/a';
const String DEMO_TWO = '/b';
const String DEMO_THREE = '/c';
const String DEMO_FOUR = '/d';
void main() {
  runApp(new MaterialApp(
      home: new HomePage(),
      routes: {
        DEMO_ONE: (BuildContext context) => new IoScreen(),
        DEMO_TWO: (BuildContext context) => new SqliteScreen(),
        DEMO_THREE: (BuildContext context) => new MessageList(),
        DEMO_FOUR: (BuildContext context) => new ListPage()
      }
  ));
}

class HomePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    getGestureDetector(String routeName, String content) {
      return new GestureDetector(
        onTap: (){
          Navigator.of(context).pushNamed(routeName);
        },
        child: new Container(
          decoration: new BoxDecoration(
            border: new Border.all(
              color: Colors.blue,
            ),
            color: Colors.greenAccent,
            // 设置所有的圆角
            borderRadius: const BorderRadius.all(const Radius.circular(6.0)),
          ),
          padding: EdgeInsets.all(8.0),
          margin: EdgeInsets.all(10.0),
          child: new Center(child: new Text(content)),
        ),
      );
    }
    return new Scaffold(
      appBar: new AppBar(title: new Text('Home'),),
      body: SingleChildScrollView(
        child: Column(
          children: <Widget>[
            getGestureDetector(DEMO_ONE, '文件 IO'),
            getGestureDetector(DEMO_TWO, '数据库操作'),
            getGestureDetector(DEMO_FOUR, '网络操作'),
            getGestureDetector(DEMO_THREE, 'echo 客户端')
          ],
        ),
      ),
    );
  }
}