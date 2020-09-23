import 'package:animation/config/router_config.dart';
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
                getGestureDetector(RouteName.imgScale, '基本示例：图片放大'),
                getGestureDetector(RouteName.imgScale2, '基本示例：图片放大(使用 AnimatedWidget 简化)'),
                getGestureDetector(RouteName.imgScale2, '基本示例：图片放大(使用 AnimatedBuilder 封装)'),
                getGestureDetector(RouteName.innerAnim, '基本示例：内置动画组件'),
                getGestureDetector(RouteName.rollingBall, '自定义动画：运动的小球'),
                getGestureDetector(RouteName.heroAnimationA, 'Hero 动画'),
                getGestureDetector(RouteName.staggerAnimation, '交织动画'),
                getGestureDetector(RouteName.switcherAnimation, '组件切换动画：计时器'),
                getGestureDetector(RouteName.switcherAnimation2, '组件切换动画(高级用法)：计时器'),
                getGestureDetector(RouteName.animatedDecorationBox, '动画过渡组件'),
                getGestureDetector(RouteName.animatedDecorationBox, '动画过渡组件(优化封装)'),
                getGestureDetector(RouteName.animatedPresetWidget, '动画过渡组件(Flutter内置)'),
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