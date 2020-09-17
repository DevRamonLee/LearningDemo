import 'package:event_notification/config/router_config.dart';
import 'package:flutter/material.dart';

class Home extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _HomeState();
}

class _HomeState extends State<Home> {
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
                getGestureDetector(RouteName.touchEvent, '原始指针事件'),
                getGestureDetector(RouteName.gestureDetector, '手势识别'),
                getGestureDetector(RouteName.busBPage, 'EventBus'),
                getGestureDetector(RouteName.scrollableNotification, '滚动通知'),
                getGestureDetector(RouteName.customNotification, '自定义通知')
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