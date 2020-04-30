import 'package:basic_widget/integrated_case3.dart';
import 'package:basic_widget/pages/page_basic.dart';
import 'package:flutter/material.dart';
import 'package:basic_widget/integrated_case1.dart';
import 'package:basic_widget/integrated_case2.dart';

void main() {
  runApp(new MaterialApp(
    home: new HomePage(),
    routes: {
      '/i': (BuildContext context) => new PageBasic(Key('basic_widget')),
      '/g': (BuildContext context) => new MyAppOne(),
      '/h': (BuildContext context) => new MyAppTwo(),
      '/m': (BuildContext context) => new MyAppThree()
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
            getGestureDetector('/i', '基本组件'),
            getGestureDetector('/g', '综合案例 1 复杂布局'),
            getGestureDetector('/h', '综合案例 2 ListView 与点击事件'),
            getGestureDetector('/m', '综合案例 3 ListView 加载更多')
          ],
        ),
      ),
    );
  }
}
