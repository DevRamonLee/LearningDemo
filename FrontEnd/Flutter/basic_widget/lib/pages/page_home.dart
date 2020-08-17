import 'package:basic_widget/config/router_config.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _HomePageState();
}

class _HomePageState extends State<HomePage> {
  DateTime _lastPressedAt;  // 上次点击时间
  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        if (_lastPressedAt == null ||
            DateTime.now().difference(_lastPressedAt) > Duration(seconds: 1)) {
          // 两次点击时间超过 1 秒则重新计时
          _lastPressedAt = DateTime.now();
          return false;
        }
        return true;
      },
      child: new Scaffold(
        appBar: new AppBar(title: new Text('Home'),),
        body: SingleChildScrollView(
          child: Column(
            children: <Widget>[
              getGestureDetector(RouteName.basicWidgets, '基本组件'),
              getGestureDetector(RouteName.case1, '案例 1 复杂布局'),
              getGestureDetector(RouteName.case2, '案例 2 ListView 与点击事件'),
              getGestureDetector(RouteName.case3, '案例 3 ListView 加载更多'),
              getGestureDetector(RouteName.case4, '案例 4 Provider 简单实现'),
              getGestureDetector(RouteName.case5, '案例 5 路由换肤'),
              getGestureDetector(RouteName.case6, '案例 6 FutureBuilder'),
              getGestureDetector(RouteName.case7, '案例 7 StreamBuilder'),
              getGestureDetector(RouteName.case8, '案例 8 对话框')
            ],
          ),
        ),
      )
    );
  }

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
}