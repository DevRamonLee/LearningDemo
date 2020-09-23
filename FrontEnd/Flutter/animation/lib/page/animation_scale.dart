
import 'package:flutter/cupertino.dart';

class AnimationScale extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _AnimationScale();
}

// 需要继承 TickerProvider， 如果有多个 AnimationController，则应该使用 TickerProviderStateMixin
class _AnimationScale extends State<AnimationScale> with SingleTickerProviderStateMixin {
  Animation<double> animation;
  AnimationController controller;

  @override
  void initState() {
    super.initState();
    controller = new AnimationController(duration: const Duration(seconds: 3), vsync: this);
    // 使用一个弹性曲线
    animation = CurvedAnimation(parent: controller, curve: Curves.bounceIn);
    // 图片宽度从 0 到 300
    animation = new Tween(begin: 0.0, end: 300.0).animate(animation)
      ..addListener(() {
        setState(() {});  // 值改变时调用 setState 标记当前 widget 为 dirty，下次刷新就会重新调用 build，图片宽高使用了动画的值，会逐渐放大
      });
    // 启动动画
    controller.forward();
  }

  @override
  Widget build(BuildContext context) {
    return new Center(
      child: Image.asset("images/ocean.jpg",
        width: animation.value,
        height: animation.value),
    );
  }

  @override
  void dispose() {
    // 路由销毁时需要释放动画资源，防止内存泄漏
    controller.dispose();
    super.dispose();
  }
}