import 'package:flutter/material.dart';
import 'package:gesture_animation/first_screen.dart';
import 'package:gesture_animation/message_list.dart';
import 'package:gesture_animation/animation_screen.dart';
import 'package:gesture_animation/animation_dot_screen.dart';
import 'package:gesture_animation/paint_screen.dart';

const String DEMO_ONE = '/a';
const String DEMO_TWO = '/b';
const String DEMO_THREE = '/c';
const String DEMO_FOUR = '/d';
const String DEMO_FIVE = '/e';
void main() {
  runApp(new MaterialApp(
      home: new HomePage(),
      routes: {
        DEMO_ONE: (BuildContext context) => new FirstScreen(),
        DEMO_TWO: (BuildContext context) => new MessageList(),
        DEMO_THREE: (BuildContext context) => new AnimationScreen(),
        DEMO_FOUR: (BuildContext context) => new AnimationDotScreen(),
        DEMO_FIVE: (BuildContext context) => new PaintScreen()
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
            getGestureDetector(DEMO_ONE, '页面跳转'),
            getGestureDetector(DEMO_TWO, '综合案例：echo 客户端'),
            getGestureDetector(DEMO_THREE, '动画展示'),
            getGestureDetector(DEMO_FOUR, '运动的小点'),
            getGestureDetector(DEMO_FIVE, 'flutter 绘制')
          ],
        ),
      ),
    );
  }
}